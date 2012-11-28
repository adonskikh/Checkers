/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server;
//import com.sun.org.apache.bcel.internal.generic.BREAKPOINT;
import java.awt.*;
import server.checkers.IChecker;
import server.players.Player;

/**
 *
 * @author totzhe
 */
public class Game
{
    private IChecker[][] board;
    private int boardSize = 8;
    private int countCheckers = 12;
    private Player players[];
    private Player currentPlayer;
    
    public Game(Player player1, Player player2)
    {
        
        board = new IChecker[boardSize][boardSize];
        players = new Player[2];
        players[0] = player1; 
        players[1] = player2;
        
        board = new IChecker[boardSize][boardSize];
        player1.setBoard(board);
        player2.setBoard(board);
        player1.SetCheckersInStartPosition();
        player2.SetCheckersInStartPosition();
        
        //Первый ход в игре принадлежит белым
        currentPlayer = player1.getColor() == Color.WHITE ? player1 : player2;
    }
    
    public Player getCurrentPlayer()
    {
        return currentPlayer;
    }
    
    public void ActionOnRecieveMessage(Point targetPoint)
    {
        Point killedChecker;
        Point startPoint;
        Point finishPoint;
        boolean endTurn = false;
        boolean turnToQueen = false;
        startPoint = new Point(currentPlayer.getCurrentChecker().getX(), currentPlayer.getCurrentChecker().getY());
        finishPoint = new Point(currentPlayer.getCurrentChecker().getX(), currentPlayer.getCurrentChecker().getY());
        
        if(board[targetPoint.x][targetPoint.y] != null && Math.abs(targetPoint.x - startPoint.x) == Math.abs(targetPoint.y - startPoint.y))
        {
            currentPlayer.SendMoveCommand(startPoint, finishPoint, new Point(-1, -1), turnToQueen, endTurn);
            System.out.println("targetPoint is not null");
            return;
        }
        //Если может убить кого-нибудь данным ходом
        if(currentPlayer.getCurrentChecker().IfThisOneKillSmb(targetPoint))
        {
            killedChecker = currentPlayer.getCurrentChecker().KillEnemysCheckers(targetPoint);
            finishPoint = currentPlayer.getCurrentChecker().ChangeCoords(targetPoint);
            endTurn = !currentPlayer.getCurrentChecker().CanKillSmb() ? true : false;
            currentPlayer.IncScore();
            System.out.println("This one can kill smb");
        }
        //Если данным ходом, никого не бьет
        else
        {
            System.out.println("This one can't kill smb");
            boolean found = false;
            //Должна ли хотя бы какая-нибудь шашка бить
            for(int i = 0; i < boardSize; i++)
            {
                for(int j = 0; j < boardSize; j++)
                {
                    if(board[i][j] != null && board[i][j].getColor() == currentPlayer.getColor())
                    {
                        if(board[i][j].CanKillSmb())
                        {
                            killedChecker = new Point(-1, -1);
                            endTurn = false;
                            found = true;
                            break;
                        }
                    }   
                }
                if(found)
                {
                    System.out.println("Other checker can kill");
                    break;
                }
            }
            
            //Не может никого убить сама, другие тоже. Просто перемещаем, если можно
            System.out.println("targetPoint = (" + targetPoint.x + ", " + targetPoint.y + ")");
            if(currentPlayer.getCurrentChecker().CheckMotion(targetPoint) && !found)
            {
                System.out.println("Moving nnnnnnnnnnnnnnnnn");
                finishPoint = currentPlayer.getCurrentChecker().ChangeCoords(targetPoint);
                killedChecker = new Point(-1, -1);
                endTurn = true;
            }
            else
            {
                System.out.println("Cannot move");
                killedChecker = new Point(-1, -1);
                endTurn = false;
            }       
        }
        
        turnToQueen = false;
        //Становится ли дамкой
        if(currentPlayer.getCurrentChecker().IfCanTurnToQueen())
        {
            turnToQueen = true;
            Point tmp = new Point(currentPlayer.getCurrentChecker().getX(), currentPlayer.getCurrentChecker().getY());
            board[tmp.x][tmp.y] = currentPlayer.getCurrentChecker().Crown();
            currentPlayer.setCurrentChecker(board[tmp.x][tmp.y]);
        }
        
        System.out.println("Sending answer");
        for(int i = 0; i < players.length; i++)
            players[i].SendMoveCommand(startPoint, finishPoint, killedChecker, turnToQueen, endTurn);
        
        if(endTurn) currentPlayer = currentPlayer == players[0] ? players[1] : players[0];
    }
    
    //Если какой-либо игрок убил все шашки другого игрока - конец игры
    public int CheckEndGame()
    {
        return players[0].getScore() == countCheckers ? 0 : players[1].getScore() == countCheckers ? 1 : -1;
    }
    
    public void Start()
    {
        //TODO: Вероятно, здесь будет всякая подготовительная ерунда вроде выбора из БД и отправки имен игроков друг другу
        players[0].SendNewGameCommand(0, players[0].getName(), players[1].getName());
        players[1].SendNewGameCommand(1, players[1].getName(), players[0].getName());
        
        
        while (players[0].isConnected() && players[1].isConnected() && players[0].getScore() < 12 && players[1].getScore() < 12)
        {
            System.out.println("New turn");
            Point targetPoint = currentPlayer.ReadMoveRequest();
            System.out.println(currentPlayer.getColor().toString());
            if (targetPoint != null)
            {
                System.out.println("Moving");
                ActionOnRecieveMessage(targetPoint); //раскомментировать потом
            }
        }
        
        //TODO: Отправить победителю и проигравшему соответствующие сообщения
        if(players[0].getScore() > players[1].getScore())
        {
            players[0].SendGameOverCommand("You win! Congrats!");
            players[1].SendGameOverCommand("You lost.");
        }
        else if(players[0].getScore() < players[1].getScore())
        {
            players[1].SendGameOverCommand("You win! Congrats!");
            players[0].SendGameOverCommand("You lost.");
        }
        else
        {
            players[1].SendGameOverCommand("Draw!");
            players[0].SendGameOverCommand("Draw!");
        }
    }
}
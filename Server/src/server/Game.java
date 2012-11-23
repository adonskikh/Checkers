/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server;
//import com.sun.org.apache.bcel.internal.generic.BREAKPOINT;
import java.awt.*;
import java.io.IOException;
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
        
        if(player1.getColor() == Color.WHITE)
        {
            currentPlayer = player1;
        }
        else
        {
            currentPlayer = player2;
        }
    }
    
    public Player getCurrentPlayer()
    {
        return currentPlayer;
    }
    
    private Point killedChecker;
    private Point startPoint;
    private Point finishPoint;
    private boolean endTurn;
    private boolean turnToQueen;
    
    public void ActionOnRecieveMessage(Point targetPoint)
    {
        endTurn = false;
        startPoint = new Point(currentPlayer.getCurrentChecker().getX(), currentPlayer.getCurrentChecker().getY());
        finishPoint = new Point(currentPlayer.getCurrentChecker().getX(), currentPlayer.getCurrentChecker().getY());
        //Если может убить кого-нибудь данным ходом
        if(currentPlayer.getCurrentChecker().IfThisOneKillSmb(targetPoint))
        {
            killedChecker = currentPlayer.getCurrentChecker().KillEnemysCheckers(targetPoint);
            currentPlayer.getCurrentChecker().ChangeCoords(targetPoint);
            endTurn = !currentPlayer.getCurrentChecker().CanKillSmb() ? true : false;
        }
        //Если данным ходом, никого не бьет
        else
        {
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
                    break;
                }
            }
            
            //Не может никого убить сама, другие тоже. Просто перемещаем, если можно
            if(currentPlayer.getCurrentChecker().CheckMotion(targetPoint))
            {
                currentPlayer.getCurrentChecker().ChangeCoords(targetPoint);
                killedChecker = new Point(-1, -1);
                endTurn = true;
            }
            else
            {
                killedChecker = new Point(-1, -1);
                endTurn = false;
            }       
        }
        
        turnToQueen = false;
        //Становится ли дамкой
        if(currentPlayer.getCurrentChecker().IfCanTurnToQueen())
        {
            turnToQueen = true;
            board[currentPlayer.getCurrentChecker().getX()][currentPlayer.getCurrentChecker().getY()] = currentPlayer.getCurrentChecker().Crown();
        }
        for(int i = 0; i < players.length; i++)
            players[i].SendMoveCommand(startPoint, finishPoint, killedChecker, turnToQueen, endTurn);
        
        if(endTurn) currentPlayer = currentPlayer == players[0] ? players[1] : players[0];
    }
    
    public void MoveChecker(Point currentSquare, Point newSquare)
    {
        board[currentSquare.x][currentSquare.y].setX(newSquare.x);
        board[currentSquare.x][currentSquare.y].setY(newSquare.y);
        board[newSquare.x][newSquare.y] = board[currentSquare.x][currentSquare.y];
        board[currentSquare.x][currentSquare.y] = null;
    }
    
    public void Start()
    {
        //TODO: Вероятно, здесь будет всякая подготовительная ерунда вроде выбора из БД и отправки имен игроков друг другу
        players[0].SendNewGameCommand(0, "white", "black");
        players[1].SendNewGameCommand(1, "black", "white");
        
        
        while (players[0].isAlive() && players[1].isAlive())
        {
            System.out.println("New turn");
            Point targetPoint = currentPlayer.ReadMoveRequest();
            System.out.println(currentPlayer.getColor().toString());
            if (targetPoint != null)
            {
                System.out.println("Moving");
                //ActionOnRecieveMessage(targetPoint); //раскомментировать потом


                //заглушка  
                for(int i = 0; i < players.length; i++)
                    players[i].SendMoveCommand(new Point(currentPlayer.getCurrentChecker().getX(), currentPlayer.getCurrentChecker().getY()), targetPoint, new Point(-1, -1), false, true);
                MoveChecker(new Point(currentPlayer.getCurrentChecker().getX(), currentPlayer.getCurrentChecker().getY()), targetPoint);
                currentPlayer = currentPlayer == players[0] ? players[1] : players[0];
                //if(currentPlayer.getColor() == Color.WHITE && players[0].getColor() == Color.WHITE)
            }
        }

        //TODO: Отправить победителю и проигравшему соответствующие сообщения
    }
}

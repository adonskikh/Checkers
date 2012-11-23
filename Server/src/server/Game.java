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
    private Player players[];
    private Player currentPlayer;
    
    public Game(Player player1, Player player2)
    {
        board = new IChecker[boardSize][boardSize];
        players = new Player[2];
        players[0] = player1; 
        players[1] = player2;
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
        
        currentPlayer.SendMoveCommand(killedChecker, startPoint, finishPoint,endTurn, turnToQueen);
        
        if(endTurn) currentPlayer = currentPlayer == players[0] ? players[1] : players[0];
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server.players;

import java.awt.*;
import server.checkers.BlackChecker;
import server.checkers.IChecker;
import server.checkers.WhiteChecker;

/**
 *
 * @author alina
 */
public class Player
{
    private int id;
    //private ObjectReader reader; 
    private int score;
    private Color color;
    private IChecker current_checker;
    private Point startPoint;
    
    public Player(int id/*, ObjectReader rdr*/)
    {
        this.id = id;
        score = 0;
        current_checker = null;
        //reader = rdr;
    }
    
    private IChecker[][] board;

    public void setBoard(IChecker[][] gameBoard)
    {
        board = gameBoard;
    }
  
    public Color getColor()
    {
        return color;
    }
    
    public IChecker getCurrentChecker()
    {
        return current_checker;
    }
    
    //Расстановка фигур на доске в начале игры
    public void SetCheckersInStartPosition()
    {
        for(int i = 0; i < board.length; i++) 
        {
            for(int j = 0; j < board.length; j++) 
            {
                if((i + j) % 2 == 1)
                {
                    if(j < 3 && color == Color.WHITE)
                    {
                        board[i][j] = new WhiteChecker(color, i, j, board);
                    }
                    if(j > board.length - 3  && color == Color.BLACK)
                    {
                        board[i][j] = new BlackChecker(Color.BLACK, i, j, board);
                    }
                }
            }
        }    
    }
    
    //Проверка является ли шашка, шашкой игрока (по цвету)
    public boolean IsMineChecker(Point checker_coord)
    {
        if(board[checker_coord.x][checker_coord.y] != null && board[checker_coord.x][checker_coord.y].getColor() == color)
        {
            current_checker = board[checker_coord.x][checker_coord.y];
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * @return the score
     */
    public int getScore()
    {
        return score;
    }

    /**
     * @param score the score to set
     */
    public void setScore(int score)
    {
        this.score = score;
    }
    
    public void SendMoveCommand(Point killedCheckerPoint, Point startPoint, Point finishPoint, boolean endTurnB, boolean turnToQueen)// + currentPoint
    {
        
    }
    
}

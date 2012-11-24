/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server.checkers;

import java.awt.*;

/**
 *
 * @author alina
 */
public final class BlackChecker extends Checker
{
    public BlackChecker(Color color, int x, int y, IChecker[][] masChecker)
    {
        super(color, x, y, masChecker);
    }
    
    @Override
    //Может ли шашка сделать данных ход
    public boolean CheckMotion(Point targetPoint)
    {
        if (targetPoint.y >= y)
        {
            return false;
        }
        
        int dX = Math.abs(targetPoint.x - x), dY = y - targetPoint.y;

        if (dX == 2 && dY == 2
                && board[(targetPoint.x + x) / 2][(targetPoint.y + y) / 2] != null
                && board[(targetPoint.x + x) / 2][(targetPoint.y + y) / 2].getColor() == Color.WHITE)
        {
            return true;
        }
        if (dX == 1 && dY == 1)
        {
            return true;
        }
        return false;
    }
    
    @Override
    //Проверяет, может ли шашка убить вражескую
    public boolean CanKillSmb()
    {
        if(y - 2 > -1)
            {
                if(x - 1 > -1 && board[x - 1][y - 1] != null && board[x - 1][y - 1].getColor() == Color.WHITE)
                {
                    if(x - 2 > -1 && board[x - 2][y - 2] == null)
                    {
                        return true;
                    }  
                }
                if(x + 1 < board.length && board[x + 1][y - 1] != null && board[x + 1][y - 1].getColor() == Color.WHITE)
                {
                    if(x + 2 < board.length && board[x + 2][y - 2] == null)
                    {
                        return true;
                    }  
                }                
            }
            return false;    
    }
    
    @Override
    public boolean IfCanTurnToQueen()
    {
        return y == board.length;
    }
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server.checkers;

import java.awt.*;

/**
 *
 * @author Alina
 */
public abstract  class Checker implements IChecker 
{
    
    protected Color color;
    protected int x, y;
    
    protected IChecker[][] board;
    
    public Checker(Color color, int x, int y, IChecker[][] masChecker)
    {
        this.color = color;
        this.x = x;
        this.y = y;
        this.board = masChecker;
    }
    
    @Override
    public int getX()
    {
        return x;
    }
    
    @Override
    public void setX(int x)
    {
        this.x = x;
    }
    
    @Override
    public int getY()
    {
        return y;
    }
    
    @Override
    public void setY(int y)
    {
        this.y = y;
    }
    
    @Override
    public Color getColor()
    {
        return color;
    }
    
    @Override
    public IChecker Crown()
    {
        return new CrownedChecker(this, this.board);
    }
    
    @Override
    public boolean CheckMotion(Point targetPointPoint)
    {
        /*if (this.getColor() == Color.BLACK && y >= this.getY())
        {
            return false;
        }
        
        if (this.getColor() == Color.WHITE && y <= this.getY())
        {
            return false;
        }
        int dX = Math.abs(x - this.x), dY = Math.abs(y - this.y);
        
        if (board[x][y] == null)
        {
            if (dX == 2 && dY == 2 && 
                    board[(x + this.x)/2][(y + this.y)/2] != null && 
                    board[(x + this.x)/2][(y + this.y)/2].getColor() != this.color) 
            {
                return true;
            }
            if (dX == 1 && dY == 1) 
            {
                return true;
            }
        }
        return false;*/
        return false;
    }
    
    @Override
    public Point KillEnemysCheckers(Point point)
    {
        board[(point.x + this.x) / 2][(point.y + this.y) / 2] = null;
        return new Point((point.x + this.x) / 2, (point.y + this.y) / 2);
    }
    
    @Override
    public boolean CanKillSmb()
    {
        /*if (this.getColor() == Color.BLACK)
        {
            if(startY - 2 > -1)
            {
                if(startX - 1 > -1 && board[startX - 1][startY - 1] != null && board[startX - 1][startY - 1].getColor() != this.getColor())
                {
                    if(startX - 2 > -1 && board[startX - 2][startY - 2] == null)
                    {
                        return true;
                    }  
                }
                if(startX + 1 < board.length && board[startX + 1][startY - 1] != null && board[startX + 1][startY - 1].getColor() != this.getColor())
                {
                    if(startX + 2 < board.length && board[startX + 2][startY - 2] == null)
                    {
                        return true;
                    }  
                }                
            }
            return false;           
        }
        else 
        {
            if(startY + 2 < board.length)
            {
                if(startX - 1 > -1 && board[startX - 1][startY + 1] != null && board[startX - 1][startY + 1].getColor() != this.getColor())
                {
                    if(startX - 2 > -1 && board[startX - 2][startY + 2] == null)
                    {
                        return true;
                    }  
                }
                if(startX + 1 < board.length && board[startX + 1][startY + 1] != null && board[startX + 1][startY + 1].getColor() != this.getColor())
                {
                    if(startX + 2 < board.length && board[startX + 2][startY + 2] == null)
                    {
                        return true;
                    }  
                }                
            }
            return false; 
            }*/
        return false;
    }
  
    /**
     * Может ли текущая шашка данным ходом убить вражескую, если может, то убивает
     * @param startPoint
     * @param targetPoint
     * @return true/false
     */
    @Override
    public boolean IfThisOneKillSmb(Point targetPoint)
    {
        if(Math.abs(targetPoint.y - x) == 2)
        {
            Point tmp = new Point((targetPoint.x + x)/2, (targetPoint.y + y)/2);
            if (board[tmp.x][tmp.y] != null && board[tmp.x][tmp.y].getColor() != color)
            {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public void ChangeCoords(Point targetPoint)
    {
        x = targetPoint.x;
        y = targetPoint.y;
    }
    
    @Override
    public abstract boolean IfCanTurnToQueen();
}

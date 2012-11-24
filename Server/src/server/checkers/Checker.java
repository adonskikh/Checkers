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
    public abstract boolean CheckMotion(Point targetPointPoint);
    
    @Override
    public Point KillEnemysCheckers(Point point)
    {
        board[(point.x + this.x) / 2][(point.y + this.y) / 2] = null;
        return new Point((point.x + this.x) / 2, (point.y + this.y) / 2);
    }
    
    @Override
    public abstract boolean CanKillSmb();
  
    /**
     * Может ли текущая шашка данным ходом убить вражескую, если может, то убивает
     * @param startPoint
     * @param targetPoint
     * @return true/false
     */
    @Override
    public boolean IfThisOneKillSmb(Point targetPoint)
    {
        if(Math.abs(targetPoint.y - y) == 2)
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
    public Point ChangeCoords(Point targetPoint)
    {
        board[x][y] = null;
        x = targetPoint.x;
        y = targetPoint.y;
        board[x][y] = this;
        
        return new Point(x, y);
    }
    
    @Override
    public abstract boolean IfCanTurnToQueen();
}

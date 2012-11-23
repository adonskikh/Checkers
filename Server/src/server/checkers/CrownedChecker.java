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
public final class CrownedChecker implements IChecker
{   
    public CrownedChecker(IChecker checker, IChecker[][] masCheckers)
    {
        this.checker = checker;
        this.board = masCheckers;
    }
    
    private IChecker checker;
    private IChecker[][] board;
    
    @Override
    public Color getColor()
    {
        return checker.getColor();
    }
    
    @Override
    public int getX()
    {
        return checker.getX();
    }
    
    @Override
    public void setX(int x)
    {
        checker.setX(x);
    }
    
    @Override
    public int getY()
    {
        return checker.getY();
    }
    
    @Override
    public void setY(int y)
    {
        checker.setY(y);
    }
    
    @Override
    public IChecker Crown()
    {
        return  this;
    }
    
    private boolean Go(int stepX, int stepY, int x, int y)
    {
        int cur_x = getX() + stepX, cur_y = getY() + stepY;
        boolean flag = false;
        while (cur_x != x && cur_y != y)
        {
            if (board[cur_x][cur_y] != null)
            {
                if (board[cur_x][cur_y].getColor() == this.getColor())
                {
                    return false;
                }
                else 
                {
                    if(!flag)
                    {
                        flag = !flag;
                    }
                    else
                    {
                        return false;
                    }
                }   
            }
            cur_x += stepX;
            cur_y += stepY;
        }
        return true;
    }
    
    @Override
    public boolean CheckMotion(Point targetPoint)
    {
        int stepX = getX() < targetPoint.x ? 1 : -1;
        int stepY = getY() < targetPoint.y ? 1 : -1;
        
        if (board[targetPoint.x][targetPoint.y] == null)
        {
            if (Math.abs(targetPoint.x - this.getX()) == Math.abs(targetPoint.y - this.getY()))
            {
                return Go(stepX, stepY, targetPoint.x, targetPoint.y);
            }
        }
        return false;
    }
    
    @Override
    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!ДОПИСАТЬ!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    public Point KillEnemysCheckers(Point point)
    {
        int stepX = getX() < point.x ? 1 : -1;
        int stepY = getY() < point.y ? 1 : -1;
        
        int cur_x = getX() + stepX, cur_y = getY() + stepY;
        
        while (cur_x != point.x)
        {
            if (board[cur_x][cur_y] != null)
            {
                board[cur_x][cur_y] = null;
                break;
                //return 1;
            }
            cur_x += stepX;
            cur_y += stepY;   
        }
        
        return new Point(cur_x, cur_y);
        //return 0;
    }
    
    /**
     * Может ли дамка побить какую-нибудь шашку
     * @param startPoint
     * @return true/false
     */
    @Override
    public boolean CanKillSmb()
    {
        Point tmp;
        boolean founded = false;
        Point masSteps[] = {new Point(1, 1), new Point(-1, -1), new Point(1, -1), new Point(-1, 1)}; 
        for (int i = 0; i < 4; i++)
        {
            tmp = new Point(getX() + masSteps[i].x, getY() + masSteps[i].y);
            
            while (tmp.x < board.length && tmp.y < board.length && tmp.x > -1 && tmp.y > -1)
            {
                //Дамка не может ходить через свои шашки
                if (board[tmp.x][tmp.y] != null && board[tmp.x][tmp.y].getColor() == this.getColor())
                {
                    break;
                }
                
                if (founded)
                {
                    if(board[tmp.x][tmp.y] == null)
                    {
                        return true;
                    }
                    else
                    {
                        //Проверять в другом направлении
                        break;
                    }
                }
                
                if (board[tmp.x][tmp.y] != null && board[tmp.x][tmp.y].getColor() != this.getColor() && !founded)
                {
                    founded = true;
                }
                
                tmp.x += masSteps[i].x;
                tmp.y += masSteps[i].y;
            }
        }
        
        return false;
    }
    
    @Override
    public boolean IfThisOneKillSmb(Point targetPoint)
    {
        int stepX = getX() < targetPoint.x ? 1 : -1;
        int stepY = getY() < targetPoint.y ? 1 : -1;
        
        Point tmp = new Point(getX()+stepX, getY()+stepY);
        boolean founded = false;
        
        while(tmp.x != targetPoint.x && tmp.y != targetPoint.y)
        {
            if(board[tmp.x][tmp.y] != null)
            {
                if(board[tmp.x][tmp.y].getColor() == this.getColor())
                {
                    return false;
                }
                else
                {
                    if(!founded)
                    {
                        founded = true;
                    }
                    else
                    {
                        return false;
                    }
                }
            }
            tmp.x += stepX;
            tmp.y += stepY;
        }
        
        if(founded)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    @Override
    public void ChangeCoords(Point targetPoint)
    {
        setX(targetPoint.x);
        setY(targetPoint.y);
    }
    
    @Override
    public boolean IfCanTurnToQueen()
    {
        return false;
    }
}

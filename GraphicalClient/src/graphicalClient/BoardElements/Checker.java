/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphicalClient.BoardElements;

import java.awt.*;

/**
 *
 * @author totzhe
 */
public class Checker implements IChecker
{
    public Checker(IBoardSquare square, Color color)
    {
        this.color = color;
        width = square.getWidth();
        x = square.getX();
        y = square.getY();
    }
    
    private static int board = 3;
    
    private Color color;

    /**
     * @return the color
     */
    @Override
    public Color getColor()
    {
        return color;
    }
    
    private int x;
    
    private int y;

    /**
     * @return the x
     */
    @Override
    public int getX()
    {
        return x;
    }
    
    public void setX(int x)
    {
        this.x = x;
    }

    /**
     * @return the y
     */
    @Override
    public int getY()
    {
        return y;
    }
    
    public void setY(int y)
    {
        this.y = y;
    }
    
    private int width;

    /**
     * @return the width
     */
    @Override
    public int getWidth()
    {
        return width;
    }
    
    @Override
    public void Draw(Graphics g)
    {        
        g.setColor(color);
        g.fillOval(x * width + board, y * width + board, width - 2 * board, width - 2 * board);
    }
    
    @Override
    public IChecker Crown()
    {
        return new CrownedChecker(this);
    }
    
    /*@Override
    public void MoveTo(IBoardSquare square) throws SquareIsNotEmptyException
    {
        if(square.isEmpty())
        {
            this.x = square.getX();
            this.y = square.getY();
        }
        else
            throw new SquareIsNotEmptyException();
    }*/
}

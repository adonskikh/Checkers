/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphicalClient;

import java.awt.*;

/**
 *
 * @author totzhe
 */
public class CrownedChecker implements IChecker
{    
    public CrownedChecker(IChecker checker)
    {
        this.checker = checker;
    }
    
    private IChecker checker;
    
    private static int board = 10;
    
    @Override
    public Color getColor()
    {
        return checker.getColor();
    }

    /**
     * @return the x
     */
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

    /**
     * @return the y
     */
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

    /**
     * @return the width
     */
    @Override
    public int getWidth()
    {
        return checker.getWidth();
    }
    
    @Override
    public void Draw(Graphics g)
    {
        checker.Draw(g);
        g.setColor(Color.YELLOW);
        g.fillOval(getX() + board, getY() + board, getWidth() - 2 * board, getWidth() - 2 * board);
    }
    
    @Override
    public IChecker Crown()
    {
        return this;
    }
}

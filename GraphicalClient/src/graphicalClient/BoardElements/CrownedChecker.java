/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphicalClient.BoardElements;

import graphicalClient.Drawer;
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
    
    @Override
    public void Draw()
    {
        Drawer.DrawCrownedChecker(getX(), getY(), getColor());
    }
    
    @Override
    public IChecker Crown()
    {
        return this;
    }
}

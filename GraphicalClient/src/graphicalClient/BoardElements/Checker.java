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
public class Checker extends BoardElement implements IChecker
{
    public Checker(IBoardSquare square, Color color)
    {
        this.color = color;
        x = square.getX();
        y = square.getY();
    }
    
    public void setX(int x)
    {
        this.x = x;
    }
    
    public void setY(int y)
    {
        this.y = y;
    }
    
    @Override
    public void Draw()
    {
        Drawer.DrawChecker(x, y, color);
    }
    
    @Override
    public IChecker Crown()
    {
        return new CrownedChecker(this);
    }
}

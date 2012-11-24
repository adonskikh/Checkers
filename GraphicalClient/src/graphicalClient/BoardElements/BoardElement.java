/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphicalClient.BoardElements;

import java.awt.Color;

/**
 *
 * @author totzhe
 */
public class BoardElement
{  
    protected Color color;

    /**
     * @return the color
     */
    public Color getColor()
    {
        return color;
    }
    
    protected int x;
    
    protected int y;

    /**
     * @return the x
     */
    public int getX()
    {
        return x;
    }

    /**
     * @return the y
     */
    public int getY()
    {
        return y;
    }
}

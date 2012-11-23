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
public class Player
{
    private Color color;
    private boolean active;
    
    public Player(Color color, String name, String opponentName)
    {
        this.color = color;
        
        active = (getColor() == Color.WHITE);
    }

    /**
     * @return the color
     */
    public Color getColor()
    {
        return color;
    }

    /**
     * @return the active
     */
    public boolean isActive()
    {
        return active;
    }
    
    public void ChangeTurn()
    {
        active = !active;
    }
}

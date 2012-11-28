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
    private String name, opponentName;
    private boolean active;

    /**
     * @return the color
     */
    public Color getColor()
    {
        return color;
    }

    /**
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * @return the opponentName
     */
    public String getOpponentName()
    {
        return opponentName;
    }

    /**
     * @param opponentName the opponentName to set
     */
    public void setOpponentName(String opponentName)
    {
        this.opponentName = opponentName;
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
    
    public void Initialize(Color color, String name, String opponentName)
    {
        this.color = color;
        this.setName(name);
        this.setOpponentName(opponentName);
        active = (getColor() == Color.WHITE);        
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphicalClient.Server;

import java.awt.Color;

/**
 *
 * @author totzhe
 */
public class NewGameCommand
{
    private Color color;
    
    private String name;
    private String opponentName;

    public NewGameCommand(int color, String name, String opponentName)
    {
        this.color = color == 0 ? Color.WHITE : Color.RED;
        this.name = name;
        this.opponentName = opponentName;
    }

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
     * @return the opponentName
     */
    public String getOpponentName()
    {
        return opponentName;
    }
}

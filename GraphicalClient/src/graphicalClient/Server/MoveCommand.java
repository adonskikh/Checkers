/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphicalClient.Server;

import java.io.Serializable;

/**
 *
 * @author totzhe
 */
public class MoveCommand implements Serializable
{
   private int x, y, new_x, new_y, x_kill, y_kill;
    private boolean crowned;
    private boolean endOfTurn;
   
   public MoveCommand(int x, int y, int new_x, int new_y, int x_kill, int y_kill, boolean crowned, boolean endOfTurn)
   {
       this.x = x;
       this.y = y;
       this.new_x = new_x;
       this.new_y = new_y;
       this.x_kill = x_kill;
       this.y_kill = y_kill;
       this.crowned = crowned;
       this.endOfTurn = endOfTurn;
   }

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

    /**
     * @return the new_x
     */
    public int getNewX()
    {
        return new_x;
    }

    /**
     * @return the new_y
     */
    public int getNewY()
    {
        return new_y;
    }

    /**
     * @return the x_kill
     */
    public int getX_kill()
    {
        return x_kill;
    }

    /**
     * @return the y_kill
     */
    public int getY_kill()
    {
        return y_kill;
    }

    /**
     * @return the crowned
     */
    public boolean isCrowned()
    {
        return crowned;
    }

    /**
     * @return the endOfTurn
     */
    public boolean isEndOfTurn()
    {
        return endOfTurn;
    }
    
    public String toString()
    {
        return x + " " + y + " " + new_x + " " + new_y + " " + x_kill + " " + y_kill + " " + crowned + " " + endOfTurn;
    }
}

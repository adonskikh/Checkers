/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.Serializable;

/**
 *
 * @author totzhe
 */
public class MoveRequest implements Serializable
{
   private int x, y, new_x, new_y;
   
   public MoveRequest(int x, int y, int new_x, int new_y)
   {
       this.x = x;
       this.y = y;
       this.new_x = new_x;
       this.new_y = new_y;
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
}

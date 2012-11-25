/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphicalClient.Server;

import java.awt.Point;
import java.io.Serializable;

/**
 *
 * @author totzhe
 */
public class MoveCommand implements Serializable
{

    private Point startPoint, finishPoint, killedCheckerPoint;
    private boolean crowned;
    private boolean endOfTurn;

    public MoveCommand(Point startPoint, Point finishPoint, Point killedCheckerPoint, boolean crowned, boolean endOfTurn)
    {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
        this.killedCheckerPoint = killedCheckerPoint;
        this.crowned = crowned;
        this.endOfTurn = endOfTurn;
    }

    /**
     * @return the startPoint
     */
    public int getX()
    {
        return startPoint.x;
    }

    /**
     * @return the y
     */
    public int getY()
    {
        return startPoint.y;
    }

    /**
     * @return the finishPoint
     */
    public int getNewX()
    {
        return finishPoint.x;
    }

    /**
     * @return the new_y
     */
    public int getNewY()
    {
        return finishPoint.y;
    }

    /**
     * @return the killedCheckerPoint
     */
    public int getX_kill()
    {
        return killedCheckerPoint.x;
    }

    /**
     * @return the y_kill
     */
    public int getY_kill()
    {
        return killedCheckerPoint.y;
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
    
    @Override
    public String toString()
    {
        return startPoint.toString() + " " + finishPoint.toString() + " " + killedCheckerPoint.toString() + " " + crowned + " " + endOfTurn;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphicalClient.BoardElements;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author totzhe
 */
public interface IChecker
{

    void Draw(Graphics g);
    
    IChecker Crown();
    
    //void MoveTo(IBoardSquare square) throws SquareIsNotEmptyException;

    /**
     * @return the color
     */
    Color getColor();

    /**
     * @return the width
     */
    int getWidth();

    /**
     * @return the x
     */
    int getX();
    
    void setX(int x);

    /**
     * @return the y
     */
    int getY();
    
    void setY(int y);
    
}

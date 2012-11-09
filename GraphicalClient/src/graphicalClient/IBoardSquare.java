/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphicalClient;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author TotZhe
 */
public interface IBoardSquare
{

    void AddChecker(IChecker checker) throws SquareIsNotEmptyException;

    void Draw(Graphics g);

    void MoveCheckerTo(IBoardSquare square) throws SquareIsNotEmptyException;

    void RemoveChecker();

    /**
     * @return the checker
     */
    IChecker getChecker();

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

    /**
     * @return the y
     */
    int getY();

    boolean isEmpty();
    
    boolean Selected();
    
    IBoardSquare Select();
    
    IBoardSquare CancelSelection();    
}

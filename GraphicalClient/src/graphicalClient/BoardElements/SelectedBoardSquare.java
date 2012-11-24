/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphicalClient.BoardElements;

import graphicalClient.Drawer;
import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author totzhe
 */
public class SelectedBoardSquare implements IBoardSquare
{
    private IBoardSquare square;
    
    public SelectedBoardSquare(IBoardSquare square)
    {
        this.square = square;
    }

    @Override
    public void AddChecker(IChecker checker) throws SquareIsNotEmptyException
    {
        square.AddChecker(checker);
    }

    @Override
    public void Draw()
    {
        square.Draw();
        Drawer.DrawSelection(getX(), getY());
    }

    @Override
    public void RemoveChecker()
    {
        square.RemoveChecker();
    }

    @Override
    public IChecker getChecker()
    {
        return square.getChecker();
    }

    @Override
    public Color getColor()
    {
        return square.getColor();
    }

    @Override
    public int getX()
    {
        return square.getX();
    }

    @Override
    public int getY()
    {
        return square.getY();
    }

    @Override
    public boolean isEmpty()
    {
        return square.isEmpty();
    }
    
    @Override
    public boolean Selected()
    {
        return true;
    }
    
    @Override
    public IBoardSquare Select()
    {
        return this;
    }
    
    /**
     *
     * @return
     */
    @Override
    public IBoardSquare CancelSelection()
    {
        return square;
    }    
}

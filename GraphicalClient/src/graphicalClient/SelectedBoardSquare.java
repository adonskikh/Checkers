/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphicalClient;

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
    public void Draw(Graphics g)
    {
        square.Draw(g);
        g.setColor(Color.RED);
        g.drawRect(getX()*getWidth(), getY()*getWidth(), getWidth() - 1, getWidth() - 1);
    }

    @Override
    public void MoveCheckerTo(IBoardSquare square) throws SquareIsNotEmptyException
    {
        this.square.MoveCheckerTo(square);
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
    public int getWidth()
    {
        return square.getWidth();
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
    
    public boolean Selected()
    {
        return true;
    }
    
    public IBoardSquare Select()
    {
        return this;
    }
    
    public IBoardSquare CancelSelection()
    {
        return square;
    }    
}

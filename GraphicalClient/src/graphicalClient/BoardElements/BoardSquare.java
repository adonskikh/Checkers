/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphicalClient.BoardElements;

import graphicalClient.Drawer;
import java.awt.*;

/**
 *
 * @author totzhe
 */
public class BoardSquare extends BoardElement implements IBoardSquare
{

    public BoardSquare(int x, int y)
    {
        this.x = x;
        this.y = y;
        if ((x + y) % 2 == 1)
        {
            color = Color.BLACK;
        } else
        {
            color = Color.WHITE;
        }
    }
    
    private IChecker checker;

    /**
     * @return the checker
     */
    @Override
    public IChecker getChecker()
    {
        return checker;
    }
    
    @Override
    public boolean isEmpty()
    {
        return getChecker() == null;
    }
    
    @Override
    public void Draw()
    {
        Drawer.DrawSquare(x, y, color);
        if(!isEmpty())
        {
            getChecker().Draw();
        }
    }
    
    @Override
    public void AddChecker(IChecker checker) throws SquareIsNotEmptyException
    {
        if(isEmpty())
        {
            checker.setX(this.getX());
            checker.setY(this.getY());
            this.checker = checker;
        }
        else
        {
            throw new SquareIsNotEmptyException();
        }
    }
    /*
    @Override
    public void MoveCheckerTo(IBoardSquare square) throws SquareIsNotEmptyException
    {
        if(square.isEmpty())
        {
            //Спросить разрешения у сервера, если ок, тогда
            square.AddChecker(getChecker());
            RemoveChecker();
        }
        else
        {
            throw new SquareIsNotEmptyException();
        }
    }*/
    
    @Override
    public void RemoveChecker()
    {
        checker = null;
    }
    
    @Override
    public boolean Selected()
    {
        return false;
    }
    
    @Override
    public IBoardSquare Select()
    {
        return new SelectedBoardSquare(this);
    }
    
    /**
     *
     * @return
     */
    @Override
    public IBoardSquare CancelSelection()
    {
        return this;
    }
}

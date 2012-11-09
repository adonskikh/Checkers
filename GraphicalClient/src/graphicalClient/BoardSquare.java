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
public class BoardSquare implements IBoardSquare
{

    public BoardSquare(int x, int y, int width)
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
        this.width = width;
    }
    private int width;

    /**
     * @return the width
     */
    @Override
    public int getWidth()
    {
        return width;
    }
    
    private Color color;

    /**
     * @return the color
     */
    @Override
    public Color getColor()
    {
        return color;
    }
    
    private int x;
    
    private int y;

    /**
     * @return the x
     */
    @Override
    public int getX()
    {
        return x;
    }

    /**
     * @return the y
     */
    @Override
    public int getY()
    {
        return y;
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
    public void Draw(Graphics g)
    {
        g.setColor(color);
        g.fillRect(x * width, y *width, width, width);
        if(!isEmpty())
        {
            getChecker().Draw(g);
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
    }
    
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
    
    public IBoardSquare CancelSelection()
    {
        return this;
    }
}

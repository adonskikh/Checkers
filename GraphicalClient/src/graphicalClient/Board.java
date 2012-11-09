/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphicalClient;

import java.awt.*;
import javax.swing.JOptionPane;

/**
 *
 * @author totzhe
 */
public class Board
{

    private IBoardSquare[][] squares;
    private static int boardSize = 8;
    private int boardWidth;
    private  int squareWidth;

    /**
     * @return the boardWidth
     */
    public int getBoardWidth()
    {
        return boardWidth;
    }
    private IBoardSquare selectedSquare;
    
    public boolean HasSelectedSquare()
    {
        return selectedSquare != null;
    }
    
    public boolean HasSelectedChecker()
    {
        if(HasSelectedSquare())
            return !(selectedSquare.isEmpty());
        else
            return false;
    }

    public Board(int boardWidth) throws SquareIsNotEmptyException
    {
        squares = new IBoardSquare[boardSize][boardSize];
        this.boardWidth = boardWidth;
        squareWidth = boardWidth / boardSize;
        for (int i = 0; i < boardSize; i++)
        {
            for (int j = 0; j < boardSize; j++)
            {
                squares[i][j] = new BoardSquare(i, j, squareWidth);
                if ((i + j) % 2 == 1)
                {
                    if (j < 3)
                    {
                        squares[i][j].AddChecker(new Checker(squares[i][j], Color.WHITE));
                    }
                    else if (j >= boardSize - 3)
                    {
                        squares[i][j].AddChecker(new Checker(squares[i][j], Color.RED));
                    }
                }
            }
        }
    }

    public void Draw(Graphics g)
    {

        for (int i = 0; i < boardSize; i++)
        {
            for (int j = 0; j < boardSize; j++)
            {
                squares[i][j].Draw(g);
            }
        }
    }

    public IBoardSquare FindSquare(int mouse_x, int mouse_y)
    {
        if (mouse_x < boardWidth && mouse_y < boardWidth && mouse_x >= 0 && mouse_y >= 0)
        {
            return squares[mouse_x / squareWidth][mouse_y / squareWidth];
        }
        else
        {
            return null;
        }
    }
    
    public void MoveChecker(int x, int y, int x_new, int y_new) throws SquareIsNotEmptyException
    {
        if(!squares[x][y].isEmpty())
        {
            squares[x_new][y_new].AddChecker(squares[x][y].getChecker());
            squares[x][y].RemoveChecker();
            CancelSquareSelection();
        }
    }

    public void SelectSquare(int mouse_x, int mouse_y)
    {
        CancelSquareSelection();
        if (mouse_x < boardWidth && mouse_y < boardWidth && mouse_x >= 0 && mouse_y >= 0)
        {
            int i = mouse_x / squareWidth;
            int j = mouse_y / squareWidth;
            selectedSquare = squares[i][j] = squares[i][j].Select();
        }
    }

    public void CancelSquareSelection()
    {
        if (HasSelectedSquare())
        {
            int i = selectedSquare.getX();
            int j = selectedSquare.getY();
            squares[i][j] = squares[i][j].CancelSelection();
            selectedSquare = null;
        }
    }

    public void OnClick(int mouse_x, int mouse_y)
    {
        if (HasSelectedChecker())
        {
            int i = mouse_x / squareWidth;
            int j = mouse_y / squareWidth;
            if (i < boardSize && j < boardSize && i >= 0 && j >= 0 && (i != selectedSquare.getX() || j != selectedSquare.getY()))
            {
                try
                {
                    //squares[selectedSquare.getX()][selectedSquare.getY()].RemoveChecker();
                    MoveChecker(selectedSquare.getX(), selectedSquare.getY(), mouse_x / squareWidth, mouse_y / squareWidth);
                }
                catch (SquareIsNotEmptyException e)
                {
                    JOptionPane.showMessageDialog(null, "Square is not empty!");
                }
            }
            else
                CancelSquareSelection();
        }
        else
        {
            SelectSquare(mouse_x, mouse_y);
        }

    }
    
    /*public String toString()
    {
        
    }*/
            
}

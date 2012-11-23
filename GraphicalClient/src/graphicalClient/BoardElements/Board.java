/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphicalClient.BoardElements;

import graphicalClient.Player;
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
    private int squareWidth;
    private Player player;

    public Board(int boardWidth, Player player) throws SquareIsNotEmptyException
    {
        this.player = player;
        squares = new IBoardSquare[boardSize][boardSize];
        this.boardWidth = boardWidth;
        squareWidth = boardWidth / boardSize;
        for (int i = 0; i < boardSize; i++)
        {
            for (int j = 0; j < boardSize; j++)
            {
                squares[i][j] = new BoardSquare(i, j, getSquareWidth());
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

    /**
     * @return the boardWidth
     */
    public int getBoardWidth()
    {
        return boardWidth;
    }

    /**
     * @return the squareWidth
     */
    public int getSquareWidth()
    {
        return squareWidth;
    }
    private IBoardSquare selectedSquare;

    public boolean HasSelectedSquare()
    {
        return selectedSquare != null;
    }

    public boolean HasSelectedChecker()
    {
        if (HasSelectedSquare())
        {
            if (!selectedSquare.isEmpty())
            {
                return true;

            }
        }
        return false;
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

    private IBoardSquare FindSquare(int x, int y)
    {
        if (x < boardSize && y < boardSize && x >= 0 && y >= 0)
        {
            return squares[x][y];
        }
        else
        {
            return null;
        }
    }

    private void MoveChecker(int x, int y, int x_new, int y_new) throws SquareIsNotEmptyException
    {
        //TODO: спросить разрешения у сервера
        squares[x_new][y_new].AddChecker(squares[x][y].getChecker());
        squares[x][y].RemoveChecker();
        CancelSquareSelection();

    }

    private void SelectSquare(int x, int y)
    {
        CancelSquareSelection();
        if (x < boardSize && y < boardSize && x >= 0 && y >= 0)
        {
            selectedSquare = squares[x][y] = squares[x][y].Select();
        }
    }

    private void CancelSquareSelection()
    {
        if (HasSelectedSquare())
        {
            int x = selectedSquare.getX();
            int y = selectedSquare.getY();
            squares[x][y] = squares[x][y].CancelSelection();
            selectedSquare = null;
        }
    }

    public void ClickAction(int x, int y)
    {
        if (HasSelectedChecker())
        {
            if (x < boardSize && y < boardSize && x >= 0 && y >= 0 && (x != selectedSquare.getX() || y != selectedSquare.getY()))
            {
                try
                {
                    if (squares[x][y].isEmpty() && selectedSquare.getChecker().getColor() == player.getColor() && selectedSquare.getColor() == squares[x][y].getColor())
                    {
                        //squares[selectedSquare.getX()][selectedSquare.getY()].RemoveChecker();
                        MoveChecker(selectedSquare.getX(), selectedSquare.getY(), x, y);
                    }
                    else
                    {
                        SelectSquare(x, y);
                    }
                }
                catch (SquareIsNotEmptyException e)
                {
                    JOptionPane.showMessageDialog(null, "Square is not empty!");
                }
            }
            else
            {
                CancelSquareSelection();
            }
        }
        else
        {
            SelectSquare(x, y);
        }

    }
    /*public String toString()
     {
        
     }*/
}

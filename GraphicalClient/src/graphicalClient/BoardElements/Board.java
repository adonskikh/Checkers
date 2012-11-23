/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphicalClient.BoardElements;

import graphicalClient.Player;
import graphicalClient.Server.*;
import java.awt.*;
import java.io.IOException;
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
    private ServerConnection server;

    public Board(int boardWidth, Player player, ServerConnection server) throws SquareIsNotEmptyException
    { 
        this.server = server;
        this.player = player;
        //JOptionPane.showMessageDialog(null, "Player: " + player.getColor().toString());
        
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
            server.SendMoveRequest(new Point(x, y), new Point(x_new, y_new));
            //JOptionPane.showMessageDialog(null, "Sent: [" + x + ", " + y + "] -> [" + x_new + ", " + y_new + "]");
            
            /*MoveCommand command = server.ReadMoveCommand();
            JOptionPane.showMessageDialog(null, "Received: [" + command.getX() + ", " + command.getY() + "] -> [" + command.getNewX() + ", " + command.getNewY() + "]");
            if (command != null)
            {
                ExecuteMoveCommand(command);
            }*/
    }
    
    public void ExecuteMoveCommand(MoveCommand command) throws SquareIsNotEmptyException
    {
        if (command.getNewX() >= 0 && command.getNewY() >= 0)
        {
            squares[command.getNewX()][command.getNewY()].AddChecker(squares[command.getX()][command.getY()].getChecker());
            squares[command.getX()][command.getY()].RemoveChecker();
            CancelSquareSelection();
        }
        if (command.getX_kill() >= 0 && command.getY_kill() >= 0)
        {
            squares[command.getX_kill()][command.getY_kill()].RemoveChecker();
        }
        if (command.isCrowned())
        {
            IChecker temp = squares[command.getNewX()][command.getNewY()].getChecker().Crown();
            squares[command.getNewX()][command.getNewY()].RemoveChecker(); 
            squares[command.getNewX()][command.getNewY()].AddChecker(temp);
        }
        if (command.isEndOfTurn())
        {
            player.ChangeTurn();
        }
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

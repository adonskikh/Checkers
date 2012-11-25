/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphicalClient;

import graphicalClient.BoardElements.*;
import graphicalClient.Server.*;
import java.awt.Graphics;
import java.io.*;
import java.util.*;
import javax.swing.JOptionPane;

/**
 *
 * @author totzhe
 */
public class Game extends Observable implements Runnable
{

    private Player player;
    private Board board;
    private ServerConnection server;
    private Thread reader;

    public Game() throws SquareIsNotEmptyException
    {
        player = new Player();
        server = new ServerConnection();
        board = new Board(player, server);
    }
    
    public void Start()
    {     
        try
        {
            server.Connect("localhost");
        }
        catch (IOException e)
        {
            JOptionPane.showMessageDialog(null, "Connection error: " + e.getMessage());
            return;
        };
        NewGameCommand command = server.ReadNewGameCommand();
        player.Initialize(command.getColor(), command.getName(), command.getOpponentName());


        reader = new Thread(this);
        reader.start();
    }
    
    public void Finish()
    {
        server.Disconnect();
    }

    @Override
    public void run()
    {
        boolean gameOver = false;
        while (!gameOver)
        {
            String commandType = server.ReadCommandType();
            if (commandType != null)
            {
                switch (commandType)
                {
                    case "Move":
                        MoveCommand cmd = server.ReadMoveCommand();
                        try
                        {
                            if (cmd != null && !(cmd.getX() == cmd.getNewX() && cmd.getY() == cmd.getNewY()))
                            {
                                //JOptionPane.showMessageDialog(null, cmd.toString());

                                board.ExecuteMoveCommand(cmd);
                                setChanged();
                                notifyObservers();
                                clearChanged();
                            }
                        }
                        catch (SquareIsNotEmptyException e)
                        {
                            JOptionPane.showMessageDialog(null, "Cann't move checker. Target square is not empty.");
                        }
                        break;

                    case "Game over":
                        gameOver = true;
                        JOptionPane.showMessageDialog(null, server.ReadGameOverMessage());
                        break;
                }
            }
            else
            {
                gameOver = true;
            }
        }
    }

    
    public void ClickAction(int mouse_x, int mouse_y)
    {
        if (player.isActive())
        {
            board.ClickAction(ConvertMouseCoordToBoardCoord(mouse_x), ConvertMouseCoordToBoardCoord(mouse_y));
            setChanged();
            notifyObservers();
            clearChanged();
        }
    }
    
    public int ConvertMouseCoordToBoardCoord(int mouse_coord)
    {
        return mouse_coord / Drawer.getSquareWidth();
    }
    
    public void Draw(Graphics g, int border)
    {
        Drawer.setGraphics(g.create(border, border, Board.getBoardSize() * Drawer.getSquareWidth(), Board.getBoardSize() * Drawer.getSquareWidth()));
        board.Draw();
    }
}

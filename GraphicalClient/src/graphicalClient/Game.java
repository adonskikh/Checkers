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
    
    public Game() throws SquareIsNotEmptyException
    {      
        server = new ServerConnection();
        try
        {
            server.Connect("localhost");
        }
        catch (IOException e)
        {
            JOptionPane.showMessageDialog(null, "Connection error: " + e.getMessage());
        };
        NewGameCommand command = server.ReadNewGameCommand();
        player = new Player(command.getColor(), command.getName(), command.getOpponentName());

        board = new Board(player, server);
        
        Thread reader = new Thread(this);
        reader.start();
    }

    @Override
    public void run()
    {
        while (true)
        {
            MoveCommand cmd = server.ReadMoveCommand();
            try
            {
                if (cmd != null)
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

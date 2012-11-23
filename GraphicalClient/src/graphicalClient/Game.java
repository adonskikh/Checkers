/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphicalClient;

import graphicalClient.BoardElements.*;
import graphicalClient.Server.*;
import java.awt.Color;
import java.awt.Graphics;
import java.io.*;
import javax.swing.JOptionPane;

/**
 *
 * @author totzhe
 */
public class Game implements Runnable
{
    private Player player;
    
    private Board board;
    
    private static int boardWidth = 400;
    
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
        player = new Player(command.getColor(), command.getName(), command.getOpponentName());//TODO: получение цвета от сервера (0 - белый, 1 - черный(красный))

        board = new Board(boardWidth, player, server);
        
        Thread thr = new Thread(this);
        thr.start();
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
                }
            }
            catch (SquareIsNotEmptyException e)
            {
            }
        }
    }

    public void ClickAction(int mouse_x, int mouse_y)
    {
        if(player.isActive())
            board.ClickAction(ConvertMouseCoordToBoardCoord(mouse_x), ConvertMouseCoordToBoardCoord(mouse_y));
    }
    
    public int ConvertMouseCoordToBoardCoord(int mouse_coord)
    {
        return mouse_coord / board.getSquareWidth();
    }
    
    public void Draw(Graphics g, int border)
    {        
        Graphics gr = g.create(border, border, board.getBoardWidth(), board.getBoardWidth());
        board.Draw(gr);
    }
}

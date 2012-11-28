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
    private String status;
    private boolean gameOver = false;

    public Game() throws SquareIsNotEmptyException
    {
        player = new Player();
        server = new ServerConnection();
        board = new Board(player, server);
        status = "Checkers";
    }
    
    public void Start(String login, String password)
    {     
        status = "Connecting to the server";
        try
        {
            server.Connect("localhost");
        }
        catch (IOException e)
        {
            JOptionPane.showMessageDialog(null, "Connection error: " + e.getMessage());
            return;
        };
        try
        {
            //JOptionPane.showMessageDialog(null, login + " " + password);
            server.SendNewGameRequest(login, password);
        }
        catch (IOException e)
        {
            System.out.println("Server connection error: " + e.getMessage());
            return;
        }
        NewGameCommand command = server.ReadNewGameCommand();
        if (command != null)
        {
            if (command.getColor() == null)
            {
                JOptionPane.showMessageDialog(null, "Bad login or password.");
                return;
            }
            player.Initialize(command.getColor(), command.getName(), command.getOpponentName());

            System.out.println("111111111111111");

            reader = new Thread(this);
            reader.start();
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Connection timeout.");
        }
    }
    
    public String getStatus()
    {
        return status;
    }
    
    public void Finish()
    {
        gameOver = true;
        server.Disconnect();
    }

    @Override
    public void run()
    {
        if (player.isActive())
        {
            status = "Your turn";
        }
        else
        {
            status = "Opponent's turn";
        }
        setChanged();
        notifyObservers();
        clearChanged();
        
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
                                if (cmd.isEndOfTurn())
                                {
                                    player.ChangeTurn();
                                    if(player.isActive())
                                        status = "Your turn";
                                    else
                                        status = "Opponent's turn";
                                }
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
                        status = server.ReadGameOverMessage();
                        JOptionPane.showMessageDialog(null, status);
                        setChanged();
                        notifyObservers();
                        clearChanged();
                        break;
                }
            }
            else
            {
                if (!gameOver)
                {
                    JOptionPane.showMessageDialog(null, "Connection lost");
                }
                status = "Disconnected";
                setChanged();
                notifyObservers();
                clearChanged();
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

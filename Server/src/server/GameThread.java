/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.awt.Color;
import java.io.*;
import java.net.*;
import server.players.Player;

/**
 *
 * @author totzhe
 */
public class GameThread implements Runnable
{

    private Socket playerSocket1;
    private Socket playerSocket2;
    private int id1;//TODO: Получить их от клиента !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    private int id2;
    private ObjectInputStream in1;
    private ObjectInputStream in2;
    private ObjectOutputStream out1;
    private ObjectOutputStream out2;

    public GameThread(Socket player1, Socket player2, int id1, int id2, ObjectInputStream in1, ObjectInputStream in2)
    {
        this.playerSocket1 = player1;
        this.playerSocket2 = player2;
        this.id1 = id1;
        this.id2 = id2;
        this.in1 = in1;
        this.in2 = in2;
    }

    @Override
    public void run()
    {
        //ObjectInputStream in1 = null;
        out1 = null;
        //ObjectInputStream in2 = null;
        out2 = null;

        try
        {
            out1 = new ObjectOutputStream(playerSocket1.getOutputStream());
            out2 = new ObjectOutputStream(playerSocket2.getOutputStream());
        }
        catch (IOException e)
        {
            System.out.println("Can't open output stream: " + e.getMessage());
            return;
        }

        /*try
        {
            in1 = new ObjectInputStream(playerSocket1.getInputStream());
            in2 = new ObjectInputStream(playerSocket2.getInputStream());
        }
        catch (IOException e)
        {
            System.out.println("Can't open input stream: " + e.getMessage());
            return;
        }*/
        
//        int id1 = 1;//TODO: Получить их от клиента !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//        int id2 = 2;
        
        Game game = new Game(new Player(id1, Color.WHITE, in1, out1), new Player(id2, Color.BLACK, in2, out2));

        game.Start();

        System.out.println("Game over");
        try
        {
            out1.close();
        }
        catch (IOException e)
        {
            System.out.println("Can't close output stream 1: " + e.getMessage());
        }
        try
        {
            out2.close();
        }
        catch (IOException e)
        {
            System.out.println("Can't close output stream 2: " + e.getMessage());
        }
        try
        {
            in1.close();
        }
        catch (IOException e)
        {
            System.out.println("Can't close input stream 1: " + e.getMessage());
        }
        try
        {
            in2.close();
        }
        catch (IOException e)
        {
            System.out.println("Can't close input stream 2: " + e.getMessage());
        }
        try
        {
            playerSocket1.close();
        }
        catch (IOException e)
        {
            System.out.println("Can't close socket 1: " + e.getMessage());
        }
        try
        {
            playerSocket2.close();
        }
        catch (IOException e)
        {
            System.out.println("Can't close socket 2: " + e.getMessage());
        }

    }
}
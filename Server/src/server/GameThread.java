/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.awt.Color;
import java.io.*;
import java.net.*;
import server.players.Player;
/*import java.util.logging.Level;
import java.util.logging.Logger;*/

/**
 *
 * @author totzhe
 */
public class GameThread implements Runnable
{

    private Socket playerSocket1;
    private Socket playerSocket2;

    public GameThread(Socket player1, Socket player2)
    {
        this.playerSocket1 = player1;
        this.playerSocket2 = player2;
    }

    @Override
    public void run()
    {
        ObjectInputStream in1 = null;
        ObjectOutputStream out1 = null;
        ObjectInputStream in2 = null;
        ObjectOutputStream out2 = null;

        try
        {
            /*out1 = new PrintWriter(new BufferedOutputStream(playerSocket1.getOutputStream()), true);
             out2 = new PrintWriter(new BufferedOutputStream(playerSocket2.getOutputStream()), true);*/
            System.out.println("33333333333333333");
            out1 = new ObjectOutputStream(playerSocket1.getOutputStream());
            out2 = new ObjectOutputStream(playerSocket2.getOutputStream());
            System.out.println("44444444444444444");
        }
        catch (IOException e)
        {
            System.out.println("Can't open output stream");
            System.exit(-1);
        }

        try
        {
            /*in1 = new BufferedReader(new InputStreamReader(playerSocket1.getInputStream()));
             in2 = new BufferedReader(new InputStreamReader(playerSocket2.getInputStream()));*/
            System.out.println("11111111111111111");
            in1 = new ObjectInputStream(playerSocket1.getInputStream());
            in2 = new ObjectInputStream(playerSocket2.getInputStream());
            System.out.println("2222222222222222");
        }
        catch (IOException e)
        {
            System.out.println("Can't open input stream");
            System.exit(-1);
        }
        
        int id1 = 1;//TODO: Получить их от клиента !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        int id2 = 2;
        
        Game game = new Game(new Player(id1, Color.WHITE, in1, out1), new Player(id2, Color.BLACK, in2, out2));

        game.Start();

        System.out.println("Game over");
        try
        {
            out1.close();
            out2.close();
        }
        catch (IOException e)
        {
            System.out.println("Can't close output stream");
            System.exit(-1);
        }
        try
        {
            in1.close();
            in2.close();
        }
        catch (IOException e)
        {
            System.out.println("Can't close input stream");
            System.exit(-1);
        }
        try
        {
            playerSocket1.close();
            playerSocket2.close();
        }
        catch (IOException e)
        {
            System.out.println("Can't close socket");
            System.exit(-1);
        }

    }
}
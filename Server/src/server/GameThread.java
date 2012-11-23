/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.*;
import java.net.*;
/*import java.util.logging.Level;
import java.util.logging.Logger;*/

/**
 *
 * @author totzhe
 */
public class GameThread implements Runnable
{

    private Socket player1;
    private Socket player2;

    public GameThread(Socket player1, Socket player2)
    {
        this.player1 = player1;
        this.player2 = player2;
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
            /*out1 = new PrintWriter(new BufferedOutputStream(player1.getOutputStream()), true);
             out2 = new PrintWriter(new BufferedOutputStream(player2.getOutputStream()), true);*/
            System.out.println("33333333333333333");
            out1 = new ObjectOutputStream(player1.getOutputStream());
            out2 = new ObjectOutputStream(player2.getOutputStream());
            System.out.println("44444444444444444");
        }
        catch (IOException e)
        {
            System.out.println("Can't open output stream");
            System.exit(-1);
        }

        try
        {
            /*in1 = new BufferedReader(new InputStreamReader(player1.getInputStream()));
             in2 = new BufferedReader(new InputStreamReader(player2.getInputStream()));*/
            System.out.println("11111111111111111");
            in1 = new ObjectInputStream(player1.getInputStream());
            in2 = new ObjectInputStream(player2.getInputStream());
            System.out.println("2222222222222222");
        }
        catch (IOException e)
        {
            System.out.println("Can't open input stream");
            System.exit(-1);
        }
        
        
        String input, output;
        MoveRequest request;
        String s;

        System.out.println("Wait for messages");
        while (true)
        {
            try
            {
                try
                {
                    if ((/*request*/s = /*(MoveRequest)*/(String)in1.readObject()) != null)
                    {
                        /*if (input.equalsIgnoreCase("exit"))
                        {
                            break;
                        }*/
                        /*out1.println("Player1: " + input);
                        out2.println("Player1: " + input);*/
                        System.out.println(/*request.getX()+" "+request.getY()*/s);
                    }
                }
                catch (ClassNotFoundException ex)
                {
                        System.out.println("ClassNotFoundException");
                }
            }
            catch (IOException e)
            {
                System.out.println("Stream 1 reading error");
                //out2.println("Player1 disconnected.");
                //System.exit(-1);
                break;
            }
            try
            {
                if ((input = in2.readLine()) != null)
                {
                    if (input.equalsIgnoreCase("exit"))
                    {
                        break;
                    }
                    //out1.println("Player2: " + input);
                    //out2.println("Player2: " + input);
                    System.out.println(input);
                }
            }
            catch (IOException e)
            {
                System.out.println("Stream 2 reading error");
                //out1.println("Player2 disconnected.");
                //System.exit(-1);
                break;
            }
        }
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
            player1.close();
            player2.close();
        }
        catch (IOException e)
        {
            System.out.println("Can't close socket");
            System.exit(-1);
        }

    }
}
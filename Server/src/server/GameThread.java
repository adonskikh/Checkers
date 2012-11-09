/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.*;
import java.net.*;

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
        BufferedReader in1 = null;
        PrintWriter out1 = null;
        BufferedReader in2 = null;
        PrintWriter out2 = null;

        try
        {
            in1 = new BufferedReader(new InputStreamReader(player1.getInputStream()));
            in2 = new BufferedReader(new InputStreamReader(player2.getInputStream()));
        } catch (IOException e)
        {
            System.out.println("Can't open input stream");
            System.exit(-1);
        }

        try
        {
            out1 = new PrintWriter(new BufferedOutputStream(player1.getOutputStream()), true);
            out2 = new PrintWriter(new BufferedOutputStream(player2.getOutputStream()), true);
        } catch (IOException e)
        {
            System.out.println("Can't open output stream");
            System.exit(-1);
        }
        String input, output;

        System.out.println("Wait for messages");
        while (true)
        {
            try
            {
                if ((input = in1.readLine()) != null)
                {
                    if (input.equalsIgnoreCase("exit"))
                    {
                        break;
                    }
                    out1.println("Player1: " + input);
                    out2.println("Player1: " + input);
                    System.out.println(input);
                }
            } catch (IOException e)
            {
                System.out.println("Stream 1 reading error");
                out2.println("Player1 disconnected.");
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
                    out1.println("Player2: " + input);
                    out2.println("Player2: " + input);
                    System.out.println(input);
                }
            } catch (IOException e)
            {
                System.out.println("Stream 2 reading error");
                out1.println("Player2 disconnected.");
                //System.exit(-1);
                break;
            }
        }
        out1.close();
        out2.close();
        try
        {
            in1.close();
            in2.close();
        } catch (IOException e)
        {
            System.out.println("Can't close input stream");
            System.exit(-1);
        }
        try
        {
            player1.close();
            player2.close();
        } catch (IOException e)
        {
            System.out.println("Can't close socket");
            System.exit(-1);
        }

    }
}
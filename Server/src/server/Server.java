/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

/**
 *
 * @author totzhe
 */
import java.io.*;
import java.net.*;

public class Server
{

    /**
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException
    {
        System.out.println("Welcome to Server side");

        ServerSocket listener = null;
        Socket clientSocket1 = null;
        Socket clientSocket2 = null;

        // create server socket
        try
        {
            listener = new ServerSocket(4445);
        } catch (IOException e)
        {
            System.out.println("Couldn't listen to port: " + e.getMessage());
            System.exit(-1);
        }

        int i = 0;
        boolean working = true;
        while (working)
        {
            try
            {
                System.out.print("Waiting for the first player...");
                clientSocket1 = listener.accept();
                System.out.println("Player connected");
                System.out.print("Waiting for the second player...");
                clientSocket2 = listener.accept();
                System.out.println("Player connected");
                i++;
            } catch (IOException e)
            {
                System.out.println("Can't accept");
                System.exit(-1);
            }
            Runnable game = new GameThread(clientSocket1, clientSocket2);
            Thread thr = new Thread(game);
            thr.start();
        }
        listener.close();
    }
}

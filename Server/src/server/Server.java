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
import java.sql.Connection;
import server.db.DataBase;
import server.db.MD5;

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
        
        /*DataBase dbc = new DataBase();
        Connection connection = dbc.CreateConnection();
        
        if(connection == null)
        {
            System.out.println("Connection with DB is null");
        }
        //connection
        MD5 md5 = new MD5();
        String password = md5.getHash("gamer");
        System.out.println(password);
        
        if (dbc.IsPlayerLikeThisInDB(connection, "gamer", password))
        {
            System.out.println("gamer in DB");
        }
        
        dbc.CloseConnection(connection);*/

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

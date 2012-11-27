/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

/**
 *
 * @author totzhe
 */
import java.awt.Color;
import java.io.*;
import java.net.*;
import java.sql.Connection;
import server.db.DataBase;
import server.db.MD5;
import server.players.Player;

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
        
        DataBase dbc = new DataBase();
        Connection connection = dbc.CreateConnection();
        //Connection connection = dbc.CreateConnection();
        
        /*if(connection == null)
        {
            System.out.println("Connection with DB is null");
        }
        //connection
        MD5 md5 = new MD5();
        String password = md5.getHash("gamer");
        System.out.println(password);
        
        int id = -1;
        
        if((id = dbc.IsPlayerLikeThisInDB(connection, "gamer", password)) != -1)
        {
            System.out.println("gamer in DB id = " + id);
        }
        
        
        Player pl = new Player(id, Color.WHITE, null, null);
        
        dbc.GetPlayersInfo(connection, pl);
        System.out.println("gamer won_count = " + pl.getWonGamesCount());
        System.out.println("gamer lost_count = " + pl.getLostGamesCount());
        //dbc.CloseConnection(connection);
        
        pl.setWonGamesCount(1);
        pl.setLostGamesCount(3);
        
        dbc.UpdatePlayerInfo(connection, pl);*/

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
        ObjectInputStream in1 = null;
        ObjectInputStream in2 = null;
        Player player1;
        Player player2;
        int id1 = -1, id2 = -1;
        while (working)
        {
            try
            {
                System.out.print("Waiting for the first player...");
                while (id1 < 0)
                {
                    clientSocket1 = listener.accept();
                    in1 = new ObjectInputStream(clientSocket1.getInputStream());
                    try
                    {
                        String login = (String) in1.readObject();
                        String password = (String) in1.readObject();
                        id1 = dbc.IsPlayerLikeThisInDB(connection, login, password);
                        
                        if(id1 != -1)
                        {
                            player1 = new Player(id2, Color.WHITE, in2, null)
                        }
                    }
                    catch (IOException e)
                    {
                        System.out.println("Can't open output stream: " + e.getMessage());
                        return;
                    }
                    catch (ClassNotFoundException ex)
                    {
                        System.out.println("ClassNotFoundException: " + ex.getMessage());
                    }
                }
                System.out.println("Player connected");
                System.out.print("Waiting for the second player...");
                
                while (id2 < 0)
                {
                    clientSocket2 = listener.accept();
                    in2 = new ObjectInputStream(clientSocket2.getInputStream());
                    try
                    {
                        String login = (String) in2.readObject();
                        String password = (String) in2.readObject();
                        id2 = dbc.IsPlayerLikeThisInDB(connection, login, password);
                        //if(id)
                    }
                    catch (IOException e)
                    {
                        System.out.println("Can't open output stream: " + e.getMessage());
                        return;
                    }
                    catch (ClassNotFoundException ex)
                    {
                        System.out.println("ClassNotFoundException: " + ex.getMessage());
                    }
                }
                System.out.println("Player connected");
                i++;
            } catch (IOException e)
            {
                System.out.println("Can't accept");
                System.exit(-1);
            }
            Runnable game = new GameThread(clientSocket1, clientSocket2, id1, id2, in1, in2);
            Thread thr = new Thread(game);
            thr.start();
        }
        listener.close();
        dbc.CloseConnection(connection);
    }
    
   
}

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
        
        /*if (dbc.IsPlayerLikeThisInDB(connection, "gamer", password))
        {
            System.out.println("gamer in DB");
        }*/
        
        //dbc.CloseConnection(connection);*/

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
        listener.setSoTimeout(10000);

        boolean working = true;
        while (working)
        {
            Player player1 = null;
            Player player2 = null;
            boolean ok = true;

            System.out.print("Waiting for the first player...");
            while (player1 == null)
            {
                try
                {
                    clientSocket1 = listener.accept();
                    System.out.println("Accepted");
                    player1 = ConnectPlayer(clientSocket1, Color.WHITE);
                }
                catch (SocketTimeoutException e)
                {
                    System.out.println("Timeout");
                }
                catch (IOException e)
                {
                    System.out.println("Can't accept");
                }
            }
            System.out.println("Player connected");

            System.out.print("Waiting for the second player...");
            while (player2 == null)
            {
                try
                {
                    clientSocket2 = listener.accept();
                    player2 = ConnectPlayer(clientSocket2, Color.BLACK);
                }
                catch (SocketTimeoutException e)
                {
                    System.out.println("Timeout");
                    player1.Disconnect();
                    ok = false;
                    break;
                }
                catch (IOException e)
                {
                    System.out.println("Can't accept");
                }
            }
            if(!ok)
                continue;
            System.out.println("Player connected");
            
            Runnable game = new GameThread(player1, player2);
            Thread thr = new Thread(game);
            thr.start();
        }
        listener.close();
    }
    
    private static Player ConnectPlayer(Socket socket, Color color)
    {
        ObjectInputStream in = null;
        ObjectOutputStream out = null;
        String login;
        String password;
        
        try
        {
            out = new ObjectOutputStream(socket.getOutputStream());
        }
        catch (IOException e)
        {
            System.out.println("Can't open output stream: " + e.getMessage());
            return null;
        }
        try
        {
            in = new ObjectInputStream(socket.getInputStream());
        }
        catch (IOException e)
        {
            System.out.println("Can't open input stream: " + e.getMessage());
            return null;
        }
        try
        {
            login = (String) in.readObject();
            password = (String) in.readObject();
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("ClassNotFoundException: " + e.getMessage());
            return null;
        }
        catch (IOException e)
        {
            System.out.println("IOException: " + e.getMessage());
            return null;
        }
        Player player = new Player(0, login);//TODO: Получить игрока с инициализированными именем и ID из БД
        if (player != null)
        {
            player.Initialize(color, socket, in, out);
        }
        return player;
    }
}

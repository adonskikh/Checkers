/*
 * To change this template, choose Tools | Templates
 * and open the template reader the editor.
 */
package graphicalClient.Server;

/**
 *
 * @author totzhe
 */
import graphicalClient.Server.MoveCommand;
import java.awt.Point;
import java.io.*;
import java.net.*;

public class ServerConnection
{
    private Socket server;
    private ObjectInputStream reader;
    private ObjectOutputStream writer;
    
    public void Connect(String hostname) throws IOException
    {
        server = new Socket(hostname, 4445);
        writer = new ObjectOutputStream(server.getOutputStream());
        reader = new ObjectInputStream(server.getInputStream());
    }
    
    //Считывает из сокета команду начала игры
    public NewGameCommand ReadNewGameCommand()
    {
        Integer color;
        String name;
        String opponentName;
        try
        {
            try
            {
                color = (Integer) reader.readObject();
                name = (String) reader.readObject();
                opponentName = (String) reader.readObject();
            }
            catch (ClassNotFoundException ex)
            {
                System.out.println("ClassNotFoundException");
                return null;
            }
        }
        catch (IOException e)
        {
            System.out.println("Stream reading error");
            return null;
        }
        if(color != null && name != null && opponentName != null)
        {
            return new NewGameCommand(color, name, opponentName);
        }
        else
            return null;
    }
    
    //Считывает из сокета команду на перемещение шашки
    public MoveCommand ReadMoveCommand()
    {
        Integer x;
        Integer y;
        Integer x_new;
        Integer y_new;
        Integer x_kill;
        Integer y_kill;
        Boolean crowned;
        Boolean endOfTurn;
        try
        {
            try
            {
                x = (Integer) reader.readObject();
                y = (Integer) reader.readObject();
                x_new = (Integer) reader.readObject();
                y_new = (Integer) reader.readObject();
                x_kill = (Integer) reader.readObject();
                y_kill = (Integer) reader.readObject();
                crowned = (Boolean) reader.readObject();
                endOfTurn = (Boolean) reader.readObject();
            }
            catch (ClassNotFoundException ex)
            {
                System.out.println("ClassNotFoundException");
                return null;
            }
        }
        catch (IOException e)
        {
            System.out.println("Stream reading error");
            return null;
        }
        if(x != null && y != null && x_new != null && y_new != null && x_kill != null && y_kill != null && crowned != null && endOfTurn != null)
        {
            return new MoveCommand(x, y, x_new, y_new, x_kill, y_kill, crowned, endOfTurn);
        }
        else
            return null;
    }

    public void SendMoveRequest(Point startPoint, Point finishPoint)
    {
        try
        {
            /*String s = "hihihihihihi";
            writer.writeObject(s);*/
            writer.writeObject(new Integer(startPoint.x));
            writer.writeObject(new Integer(startPoint.y));
            writer.writeObject(new Integer(finishPoint.x));
            writer.writeObject(new Integer(finishPoint.y));
            writer.flush();
        }
        catch(IOException e)
        {
            System.out.println("Stream writing error: " + e.getMessage());            
        }
    }
}

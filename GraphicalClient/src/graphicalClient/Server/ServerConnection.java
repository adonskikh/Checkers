/*
 * To change this template, choose Tools | Templates
 * and open the template reader the editor.
 */
package graphicalClient.Server;

/**
 *
 * @author totzhe
 */
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
                System.out.println("ClassNotFoundException: " + ex.getMessage());
                return null;
            }
        }
        catch (IOException e)
        {
            System.out.println("Stream reading error: " + e.getMessage());
            return null;
        }
        if(color != null && name != null && opponentName != null)
        {
            return new NewGameCommand(color, name, opponentName);
        }
        else
            return null;
    }
    
    //Считывает из сокета тип команды
    public String ReadCommandType()
    {
        String commandType;
        try
        {
            try
            {
                commandType = (String) reader.readObject();
            }
            catch (ClassNotFoundException ex)
            {
                System.out.println("ClassNotFoundException: " + ex.getMessage());
                return null;
            }
        }
        catch (IOException e)
        {
            System.out.println("Stream reading error: " + e.getMessage());
            return null;
        }
        return commandType;
    }
    
    //Считывает из сокета команду на перемещение шашки
    public MoveCommand ReadMoveCommand()
    {
        Point startPoint;
        Point finishPoint;
        Point killedCheckerPoint;
        Boolean crowned;
        Boolean endOfTurn;
        try
        {
            try
            {
                startPoint = (Point) reader.readObject();
                finishPoint = (Point) reader.readObject();
                killedCheckerPoint = (Point) reader.readObject();;
                crowned = (Boolean) reader.readObject();
                endOfTurn = (Boolean) reader.readObject();
            }
            catch (ClassNotFoundException ex)
            {
                System.out.println("ClassNotFoundException: " + ex.getMessage());
                return null;
            }
        }
        catch (IOException e)
        {
            System.out.println("Stream reading error: " + e.getMessage());
            return null;
        }
        if(startPoint != null && finishPoint != null && killedCheckerPoint != null && crowned != null && endOfTurn != null)
        {
            return new MoveCommand(startPoint, finishPoint, killedCheckerPoint, crowned, endOfTurn);
        }
        else
            return null;
    }
    
    //Считывает из сокета сообщение о конце игры
    public String ReadGameOverMessage()
    {
        String message;
        try
        {
            try
            {
                message = (String) reader.readObject();
            }
            catch (ClassNotFoundException ex)
            {
                System.out.println("ClassNotFoundException: " + ex.getMessage());
                return null;
            }
        }
        catch (IOException e)
        {
            System.out.println("Stream reading error: " + e.getMessage());
            return null;
        }
        return message;
    }

    public void SendNewGameRequest(String login, String password) throws IOException
    {
        writer.writeObject(login);
        writer.writeObject(password);
        writer.flush();
    }

    public void SendMoveRequest(Point startPoint, Point finishPoint)
    {
        try
        {
            writer.writeObject(startPoint);
            writer.writeObject(finishPoint);
            writer.flush();
        }
        catch (IOException e)
        {
            System.out.println("Stream writing error: " + e.getMessage());
        }
    }
    
    public void Disconnect()
    {
        try
        {
            if (reader != null)
            {
                reader.close();
            }
            if (writer != null)
            {
                writer.close();
            }
            if (server != null)
            {
                server.close();
            }
        }
        catch (IOException e)
        {
            System.out.println("Stream closing error: " + e.getMessage());
        }
    }
}

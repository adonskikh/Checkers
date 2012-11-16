/*
 * To change this template, choose Tools | Templates
 * and open the template reader the editor.
 */
package graphicalClient.Server;

/**
 *
 * @author totzhe
 */
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
    
    public void SendMoveRequest(int x, int y, int new_x, int new_y) throws IOException
    {
        writer.writeObject("hi!hi!hi!hi!"/*new MoveRequest(x, y, new_x, new_y)*/);
        writer.flush();
    }
}

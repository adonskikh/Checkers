/*
 * To change this template, choose Tools | Templates
 * and open the template reader the editor.
 */
package graphicalClient;

/**
 *
 * @author totzhe
 */
import java.io.*;
import java.net.*;

public class ServerConnection
{
    private Socket server;
    private BufferedReader reader;
    private PrintWriter writer;
    
    public void Connect(String hostname) throws IOException
    {
        server = new Socket(hostname, 4445);
        reader = new BufferedReader(new InputStreamReader(server.getInputStream()));
        writer = new PrintWriter(new BufferedOutputStream(server.getOutputStream()), true);
    }
    
    public void SendMessage(String message)
    {
        writer.println(message);
    }
}

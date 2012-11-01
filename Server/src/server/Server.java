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

public class Server {

    public static void work(Socket client) throws IOException  {
        BufferedReader in = null;
        PrintWriter out = null;
        
        in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        out = new PrintWriter(client.getOutputStream(), true);
        String input, output;

        System.out.println("Wait for messages");
        while ((input = in.readLine()) != null) {
            if (input.equalsIgnoreCase("exit")) {
                break;
            }
            out.println("S ::: " + input);
            System.out.println(input);
        }
        out.close();
        in.close();
        client.close();
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to Server side");

        ServerSocket servers = null;
        Socket client = null;

        // create server socket
        try {
            servers = new ServerSocket(4444);
        } catch (IOException e) {
            System.out.println("Couldn't listen to port 4444");
            System.exit(-1);
        }

        try {
            System.out.print("Waiting for a client...");
            client = servers.accept();
            System.out.println("Client connected");
        } catch (IOException e) {
            System.out.println("Can't accept");
            System.exit(-1);
        }
        Server.work(client);
        servers.close();
    }
}

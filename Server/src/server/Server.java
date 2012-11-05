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
    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to Server side");

        ServerSocket listener = null;
        Socket client1 = null;
        Socket client2 = null;

        // create server socket
        try {
            listener = new ServerSocket(4445);
        } catch (IOException e) {
            System.out.println("Couldn't listen to port 4445");
            System.exit(-1);
        }

        int i = 0;
        while (i < 4) {
            try {
                System.out.print("Waiting for the first player...");
                client1 = listener.accept();
                System.out.println("Player connected");
                System.out.print("Waiting for the second player...");
                client2 = listener.accept();
                System.out.println("Player connected");
                i++;
            } catch (IOException e) {
                System.out.println("Can't accept");
                System.exit(-1);
            }
            Runnable game = new GameThread(client1, client2);
            Thread thr = new Thread(game);
            thr.start();
        }
        listener.close();
    }
}

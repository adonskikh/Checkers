/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

/**
 *
 * @author totzhe
 */
import java.io.*;
import java.net.*;

public class Client
{

    public static void main(String[] args) throws IOException
    {

        System.out.println("Welcome to Client side");

        Socket fromserver = null;

        String hostname;
        if (args.length == 0)
        {
            //System.out.println("use: client hostname");
            //System.exit(-1);

            hostname = "localhost";
        } else
        {
            hostname = args[0];
        }


        System.out.println("Connecting to... " + hostname);

        fromserver = new Socket(hostname, 4445);
        BufferedReader in = new BufferedReader(new InputStreamReader(fromserver.getInputStream()));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(fromserver.getOutputStream()), true);
        BufferedReader inu = new BufferedReader(new InputStreamReader(System.in));

        String fuser, fserver;

        while ((fuser = inu.readLine()) != null)
        {
            out.println(fuser);
            try
            {
                fserver = in.readLine();
                System.out.println(fserver);
            } catch (SocketException e)
            {
                System.out.println("Opponent disconnected.");
                break;
            }
            if (fuser.equalsIgnoreCase("close"))
            {
                break;
            }
            if (fuser.equalsIgnoreCase("exit"))
            {
                break;
            }
        }

        out.close();
        in.close();
        inu.close();
        fromserver.close();
    }
}
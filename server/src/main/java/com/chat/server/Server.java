package com.chat.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gdimitrova
 */
public class Server {

    private ServerSocket socket;

    private final int port;

    public Server() {
        this(1230);
    }

    public Server(int port) {
        this.port = port;
    }

    public void start() {
        System.out.println("Opening port...");
        try {
            socket = new ServerSocket(port);
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Unable to set up port!");
            System.exit(1);
        }
        do {
            handle();
        } while (true);
    }

    private void handle() {
        Socket link = null;
        Scanner input = null;
        try {
            link = socket.accept();
            input = new Scanner(link.getInputStream());
            PrintWriter output = new PrintWriter(link.getOutputStream(), true);
            int messages = 0;
            String message = input.nextLine();
            do {
                System.out.println("\nMessage received ");
                messages++;
                output.println("Message " + messages + ":" + message);
                message = input.nextLine();
            } while (!message.equals("close"));

        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            System.err.println("Closing connection.");
            input.close();
            try {
                link.close();
            } catch (IOException ex) {
                System.err.println("Unable to close.");
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                System.exit(1);
            }
        }
    }

   public void stop() {
    }
}

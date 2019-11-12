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
public class ServerApplication {

    public static void main(String[] args) {
        Server server = new Server();
        Scanner scanner = new Scanner(System.in);
        String command = "";
        try {
            server.start();
            System.out.println("Type stop to stop the server..");
            do {
                command = scanner.nextLine();
            } while (!command.equals("stop"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            server.stop();
        }
    }

}

package com.chat.server.app;

import com.chat.server.Server;
import java.util.Scanner;

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
                switch (command) {
                    case "restart":
                        server.restart();
                        break;
                    case "stop":
                        server.stop();
                        break;
                }
            } while (!command.equals("stop"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            server.stop();
        }
    }

}

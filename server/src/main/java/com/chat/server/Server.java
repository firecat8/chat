package com.chat.server;

import com.chat.mapper.ServiceEndpointMapper;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gdimitrova
 */
public class Server {

    private static final int PORT = 1230;

    private ServerSocket serverSocket;

    private EndpointRegistry endpointRegistry = new EndpointRegistry();

    private ServiceEndpointMapper mapper = new ServiceEndpointMapper(endpointRegistry);

    public void start() {
        System.out.println("Opening port...");
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Unable to set up port!");
            System.exit(1);
        }

        while (!serverSocket.isClosed()) {
            try {
                Socket socket = serverSocket.accept();
                System.out.print("Handling client");
                new ClientHandler(socket, mapper).processRequest();
                
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void stop() {
        try {
            serverSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

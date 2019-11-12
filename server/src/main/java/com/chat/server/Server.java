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

    private Properties config = null;

    private ServerSocket serverSocket;

    private ThreadPool pool;

    private EndpointRegistry endpointRegistry;

    private ServiceEndpointMapper mapper;

    public Server() {
        init();
    }

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
                pool.accept(new ClientHandler(socket, endpointRegistry));
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private Properties loadProperties() {
        if (config == null) {
            try {
                config = new Properties();
                InputStreamReader in = new InputStreamReader(getClass().getResourceAsStream("/config.properties"));
                config.load(in);
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return config;
    }

    public void stop() {
        config = null;
        try {
            serverSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void restart() {
        stop();
        init();
        start();
    }

    private void init() {
        Properties props = loadProperties();
        String maxThreads = props.getProperty("maxThreads");
    }
}

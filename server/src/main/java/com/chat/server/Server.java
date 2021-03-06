package com.chat.server;

import com.chat.dao.DaoRegistry;
import com.chat.persistence.dao.DaoImplRegistry;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gdimitrova
 */
public class Server {

    private final static Logger LOGGER = Logger.getLogger(Server.class.getName());

    private static final int PORT = 1230;

    private ServerSocket serverSocket;

    private DaoRegistry registry = new DaoImplRegistry();

    private ServiceProvider svcProvider = new ServiceProvider(registry);

    private ExecutorService pool = Executors.newFixedThreadPool(40);

    public void start() {
        System.out.println("Opening port...");
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException ex) {
            LOGGER.severe(ex.getMessage());
            System.err.println("Unable to set up port!");
            System.exit(1);
        }

        while (!serverSocket.isClosed()) {
            try {
                Socket socket = serverSocket.accept();
                String sessionId = UUID.randomUUID().toString();
                LOGGER.log(Level.INFO, "Handling client, Session ID {0}", sessionId);
                pool.execute(new ClientHandler(sessionId, socket, svcProvider));

            } catch (IOException ex) {
                LOGGER.severe(ex.getMessage());
            }
        }
    }

    public void stop() {
        try {
            serverSocket.close();
        } catch (IOException ex) {
            LOGGER.severe(ex.getMessage());
        } catch (Exception ex) {
            LOGGER.severe(ex.getMessage());
        }
    }

}

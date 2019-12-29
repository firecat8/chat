package com.chat.app;

import com.chat.messaging.ServicePointRegistryImpl;
import com.chat.messaging.services.ServicePointRegistry;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gdimitrova
 */
public class ClientApp {

    private final static Logger LOGGER = Logger.getLogger(ClientApp.class.getName());

    private static ServicePointRegistry registry;

    private static boolean isDisconnectedMode = false;

    public static void start() {
        connectToServer();
    }

    public static void connectToServer() {

        try {
            registry = new ServicePointRegistryImpl(loadProperties());
        } catch (IOException | ClassNotFoundException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            try {
                registry.close();
            } catch (Exception ex1) {
                LOGGER.log(Level.SEVERE, ex.getMessage(), ex1);
            }
        }
    }

    public static void disconnect() {
        try {
            registry.close();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    public static ServicePointRegistry getRegistry() {
        return registry;
    }

    public static boolean isDisconnectedMode() {
        return isDisconnectedMode;
    }

    public static void setDisconnectedMode(boolean isDisconnectedMode) {
        ClientApp.isDisconnectedMode = isDisconnectedMode;
    }

    public static boolean isConnected() {
        return registry == null;
    }

    private static Properties loadProperties() {
        Properties config = null;
        try {
            config = new Properties();
            InputStreamReader in = new InputStreamReader(ChatApp.class.getResourceAsStream("/config.properties"));
            config.load(in);
            return config;
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        return config;

    }
}

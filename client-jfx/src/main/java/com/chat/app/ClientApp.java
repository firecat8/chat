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

    public static ServicePointRegistry registry;

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

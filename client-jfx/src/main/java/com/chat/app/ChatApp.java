package com.chat.app;

import static com.chat.app.ChatApp.registry;
import com.chat.messaging.services.ServicePointRegistry;
import com.chat.messaging.ServicePointRegistryImpl;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChatApp {

    private final static Logger LOGGER = Logger.getLogger(ChatApp.class.getName());

    public static ServicePointRegistry registry;

    private static InputStreamReader in;

    public static void main(String[] args) {
        try {
            startServicePoint();
            GUIApp.start(args);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    private static void startServicePoint() {
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
            in = new InputStreamReader(ChatApp.class.getResourceAsStream("/config.properties"));
            config.load(in);
            return config;
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        return config;

    }
}

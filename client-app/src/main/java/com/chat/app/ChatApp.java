package com.chat.app;

import com.chat.messaging.ServicePointRegistry;
import com.chat.messaging.ServicePointRegistryImpl;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChatApp {

    public static ServicePointRegistry registry;

    private static InputStreamReader in;

    public static void main(String[] args) {
        try {
            registry = new ServicePointRegistryImpl(loadProperties());
            App.start(args);
        } catch (IOException ex) {
            Logger.getLogger(ChatApp.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ChatApp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return config;

    }
}

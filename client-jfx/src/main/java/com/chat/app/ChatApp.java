package com.chat.app;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ChatApp {

    private final static Logger LOGGER = Logger.getLogger(ChatApp.class.getName());

    public static void main(String[] args) {
        try {
            ClientApp.start();
            GUIApp.start();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

}

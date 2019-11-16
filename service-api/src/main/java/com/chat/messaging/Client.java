package com.chat.messaging;

import com.chat.bl.service.messaging.RequestWrapper;
import com.chat.bl.service.messaging.response.ResponseImpl;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author gdimitrova
 */
class Client {

    private static Socket clientSocket;

    private final String host;

    private final int port;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    void connectToServer() throws IOException {
        clientSocket = new Socket(host, port);
    }

    ResponseImpl sendMessage() {
        return null;
    }

    void closeConnection() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

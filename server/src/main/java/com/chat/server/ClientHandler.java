package com.chat.server;

import com.chat.bl.service.messaging.MessageWrapper;
import com.chat.mapper.ServiceEndpointMapper;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gdimitrova
 */
public class ClientHandler implements Runnable {

    private EndpointRegistry registry;

    private Socket socket;

    private ObjectInputStream objInput;

    private ObjectOutputStream objOutput;

    public ClientHandler(Socket s, EndpointRegistry registry) throws IOException {
        this.socket = s;
        this.registry = registry;
        objInput = new ObjectInputStream(s.getInputStream());
        objOutput = new ObjectOutputStream(s.getOutputStream());
    }

    @Override
    public void run() {
        try {
            MessageWrapper msgWrapper = (MessageWrapper) objInput.readObject();
            // to do

            socket.close();
            objInput.close();
            objOutput.close();

        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

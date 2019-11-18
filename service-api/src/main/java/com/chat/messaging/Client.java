package com.chat.messaging;

import com.chat.bl.service.messaging.ResponseListener;
import com.chat.bl.service.messaging.Request;
import com.chat.bl.service.messaging.RequestWrapper;
import com.chat.bl.service.messaging.ResponseCode;
import com.chat.bl.service.messaging.ResponseWrapper;
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
class Client {

    private final static Logger LOGGER = Logger.getLogger(Client.class.getName());

    public static String sessionId;

    private static Socket clientSocket;

    private final String host;

    private final int port;

    private ObjectInputStream ois;

    private ObjectOutputStream oos;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    void connectToServer() throws IOException, ClassNotFoundException {
        clientSocket = new Socket(host, port);
        oos = new ObjectOutputStream(clientSocket.getOutputStream());
        ois = new ObjectInputStream(clientSocket.getInputStream());
        sessionId = (String) ois.readObject();
        LOGGER.log(Level.INFO, "Received session id: {0}", sessionId);
    }

    synchronized <Resp> void sendMessage(Class serviceClass, String method, Request req, Class respClass, ResponseListener<Resp> listener) {
        try {
            oos.writeObject(new RequestWrapper(serviceClass, method, req, respClass));
            ResponseWrapper<Resp> respWrapper = (ResponseWrapper<Resp>) ois.readObject();

            LOGGER.log(Level.INFO, "Received response {0}", respWrapper);
            if (respWrapper.getCode() == ResponseCode.OK) {
                listener.onSuccess(respWrapper.getResponse());
            } else {
                listener.onError(respWrapper.getError());
            }
        } catch (IOException | ClassNotFoundException ex) {
            listener.onError(ex.getMessage());
        }
    }

    void closeConnection() throws IOException {
//        sendMessage(this.getClass(), "close", new CloseConnectionRequest());
//        ois.close();
//        oos.close();
    }
}

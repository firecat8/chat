package com.chat.messaging;

import com.chat.bl.service.messaging.CloseConnectionRequest;
import com.chat.bl.service.messaging.ResponseListener;
import com.chat.bl.service.messaging.Request;
import com.chat.bl.service.messaging.RequestWrapper;
import com.chat.bl.service.messaging.ResponseCode;
import com.chat.bl.service.messaging.ResponseWrapper;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gdimitrova
 */
public class Client {

    private final static Logger LOGGER = Logger.getLogger(Client.class.getName());

    private DateFormat date_formater = new SimpleDateFormat("HH:mm:ss");

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
            oos.flush();
            log("Sent request " + req.toString());
            ResponseWrapper<Resp> respWrapper = (ResponseWrapper<Resp>) ois.readObject();

            log("Received response " + respWrapper.toString());
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
        ois.close();
        oos.close();
    }

    private void log(String message) {
        LOGGER.log(Level.INFO, "{0} {1}", new Object[]{date_formater.format(Calendar.getInstance().getTime()), message});
    }
}

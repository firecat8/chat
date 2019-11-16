package com.chat.server;

import com.chat.bl.service.messaging.CloseConnectionRequest;
import com.chat.bl.service.messaging.EndPoint;
import com.chat.bl.service.messaging.RequestWrapper;
import com.chat.bl.service.messaging.response.ResponseCode;
import com.chat.bl.service.messaging.response.ResponseImpl;
import com.chat.mapper.ServiceEndpointMapper;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gdimitrova
 */
public class ClientHandler {

    private final ServiceEndpointMapper mapper;

    private final Socket socket;

    private final ObjectInputStream objInput;

    private final ObjectOutputStream objOutput;

    public ClientHandler(Socket s, ServiceEndpointMapper mapper) throws IOException {
        this.socket = s;
        this.mapper = mapper;
        objInput = new ObjectInputStream(s.getInputStream());
        objOutput = new ObjectOutputStream(s.getOutputStream());
    }

    public void processRequest() {
        try {
            try (objOutput; objInput; socket) {
                boolean connected = true;
                while (connected) {
                    RequestWrapper msgWrapper = (RequestWrapper) objInput.readObject();
                    if (msgWrapper.getMessage() instanceof CloseConnectionRequest) {
                        connected = false;
                        break;
                    }
                    EndPoint endpoint = mapper.getEndpoint(msgWrapper.getServiceClass());
                    Object response = invokeMethod(endpoint.getClass(), msgWrapper);
                    objOutput.writeObject(response);
                }
            }

        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Object invokeMethod(Class<? extends EndPoint> endpointClass, RequestWrapper msgWrapper) {
        try {
            Method method = endpointClass.getMethod(msgWrapper.getResource(), msgWrapper.getMessageClass());
            return method.invoke(this, msgWrapper.getMessage());
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseImpl(ResponseCode.SERVER_ERROR, ex.getMessage());
        }
    }

}

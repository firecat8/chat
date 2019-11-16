package com.chat.server;

import com.chat.bl.service.dao.MessageException;
import com.chat.bl.service.messaging.CloseConnectionRequest;
import com.chat.bl.service.messaging.EndPoint;
import com.chat.bl.service.messaging.RequestWrapper;
import com.chat.bl.service.messaging.ResponseCode;
import com.chat.bl.service.messaging.ResponseWrapper;
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
        objOutput = new ObjectOutputStream(s.getOutputStream());
        objInput = new ObjectInputStream(s.getInputStream());
    }

    public void processRequest() {
        try (objOutput; objInput; socket) {
            boolean connected = true;
            while (connected) {
                RequestWrapper msgWrapper = (RequestWrapper) objInput.readObject();
                if (msgWrapper.getRequest() instanceof CloseConnectionRequest) {
                    connected = false;
                    break;
                }
                try {
                    EndPoint endpoint = mapper.getEndpoint(msgWrapper.getServiceClass());
                    Method method = getMethod(endpoint.getClass(), msgWrapper);
                    Object response = method.invoke(this, msgWrapper.getRequest());
                    objOutput.writeObject(new ResponseWrapper(ResponseCode.OK, response));
                } catch (IOException | IllegalAccessException | IllegalArgumentException | NoSuchMethodException | InvocationTargetException ex) {
                    objOutput.writeObject(new ResponseWrapper(ResponseCode.SERVER_ERROR, ex.getMessage()));

                } catch (MessageException ex) {
                    objOutput.writeObject(new ResponseWrapper(ResponseCode.ERROR, ex.getMessage()));
                }
            }

        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Method getMethod(Class<? extends EndPoint> endpointClass, RequestWrapper msgWrapper) throws NoSuchMethodException {
        return endpointClass.getMethod(msgWrapper.getMethod(), msgWrapper.getReqClass());
    }

}

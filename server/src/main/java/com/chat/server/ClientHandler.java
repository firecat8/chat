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

    private final static Logger LOGGER = Logger.getLogger(ClientHandler.class.getName());

    private String sessionId;

    private final ServiceEndpointMapper mapper;

    private final Socket socket;

    private final ObjectInputStream objInput;

    private final ObjectOutputStream objOutput;

    public ClientHandler(String sessionId, Socket s, ServiceEndpointMapper mapper) throws IOException {
        this.sessionId = sessionId;
        this.socket = s;
        this.mapper = mapper;
        objOutput = new ObjectOutputStream(s.getOutputStream());
        objInput = new ObjectInputStream(s.getInputStream());
    }

    public void processRequest() {
        try (objOutput; objInput; socket) {
            objOutput.writeObject(sessionId);
            while (true) {
                RequestWrapper msgWrapper = (RequestWrapper) objInput.readObject();
                log("Received request: " + msgWrapper.getRequest().getClass().getSimpleName());
                if (msgWrapper.getRequest() instanceof CloseConnectionRequest) {
                    break;
                }
                try {
                    EndPoint endpoint = mapper.getEndpoint(msgWrapper.getServiceClass());
                    Method method = getMethod(endpoint.getClass(), msgWrapper);
                    Object respWrapper = method.invoke(endpoint, msgWrapper.getRequest());
                    log("Invoked response: " + respWrapper.toString());
                    objOutput.writeObject(respWrapper);
                } catch (IOException | IllegalAccessException | IllegalArgumentException | NoSuchMethodException | InvocationTargetException ex) {
                    logError(ResponseCode.SERVER_ERROR, ex.getMessage());
                } catch (MessageException ex) {
                    logError(ResponseCode.ERROR, ex.getMessage());
                }
            }

        } catch (IOException | ClassNotFoundException ex) {
            LOGGER.severe(ex.getMessage());
        }
        log("Session ended.");
    }

    private Method getMethod(Class<? extends EndPoint> endpointClass, RequestWrapper msgWrapper) throws NoSuchMethodException {
        return endpointClass.getMethod(msgWrapper.getMethod(), msgWrapper.getReqClass());
    }

    private void log(String msg) {
        LOGGER.log(Level.INFO, "Session: {0}\n{1}", new Object[]{sessionId, msg});
    }

    private void logError(ResponseCode code, String msg) throws IOException {
        LOGGER.severe(msg);
        objOutput.writeObject(new ResponseWrapper(code, msg));
    }

}

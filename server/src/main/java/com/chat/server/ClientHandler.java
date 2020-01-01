package com.chat.server;

import com.chat.bl.service.messaging.EndPoint;
import com.chat.messaging.message.RequestWrapper;
import com.chat.messaging.message.ResponseCode;
import com.chat.messaging.message.ResponseWrapper;
import com.chat.messaging.vo.ErrorVo;
import com.chat.messaging.message.ResponseListener;
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
public class ClientHandler implements Runnable {

    private final static Logger LOGGER = Logger.getLogger(ClientHandler.class.getName());

    private final String sessionId;

    private final ServiceProvider svcProvider;

    private final Socket socket;

    private final ObjectInputStream objInput;

    private final ObjectOutputStream objOutput;

    public ClientHandler(String sessionId, Socket s, ServiceProvider svcProvider) throws IOException {
        this.sessionId = sessionId;
        this.socket = s;
        this.svcProvider = svcProvider;
        objOutput = new ObjectOutputStream(s.getOutputStream());
        objInput = new ObjectInputStream(s.getInputStream());
    }

    @Override
    public void run() {
        try (objOutput; objInput; socket) {
            objOutput.writeObject(sessionId);
            objOutput.flush();
            while (true) {
                RequestWrapper msgWrapper = (RequestWrapper) objInput.readObject();
                log("Received request: " + msgWrapper.getRequest().getClass().getSimpleName());
                handleRequest(msgWrapper);
            }

        } catch (IOException | ClassNotFoundException ex) {
            LOGGER.severe(ex.getMessage());
        }
        log("Session ended.");
    }

    private void handleRequest(RequestWrapper rqWrapper) {
        ResponseListener listener = new ResponseListenerImpl(objOutput);
        try {
            Object svc = svcProvider.getService(rqWrapper.getServiceClass());
            Method method = rqWrapper.getServiceClass().getMethod(rqWrapper.getMethod(), rqWrapper.getReqClass(), ResponseListener.class);
            method.invoke(svc, rqWrapper.getRequest(), listener);
        } catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
            listener.onError(new ErrorVo(ResponseCode.SERVER_ERROR, e.getMessage()));
        }
    }

    private void log(String msg) {
        LOGGER.log(Level.INFO, "Session: {0}\n{1}", new Object[]{sessionId, msg});
    }

    private static class ResponseListenerImpl<T> implements ResponseListener<T> {

        private final ObjectOutputStream objOutput;

        public ResponseListenerImpl(ObjectOutputStream objOutput) {
            this.objOutput = objOutput;
        }

        @Override
        public void onSuccess(T response) {
            writeResponse(new ResponseWrapper(ResponseCode.OK, response));
        }

        @Override
        public void onError(ErrorVo error) {
            writeResponse(new ResponseWrapper(error.getErrorCode(), error.getMessage()));
        }

        private void writeResponse(ResponseWrapper respWrapper) {
            try {
                objOutput.writeObject(respWrapper);
                objOutput.flush();
            } catch (IOException ex) {
                LOGGER.severe(ex.getMessage());
            }
        }

    }

}

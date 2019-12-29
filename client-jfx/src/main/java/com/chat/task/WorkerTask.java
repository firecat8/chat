package com.chat.task;

import com.chat.app.ClientApp;
import com.chat.messaging.dto.ErrorMessageDto;
import com.chat.messaging.message.Request;
import com.chat.messaging.message.Response;
import com.chat.messaging.message.ResponseCode;
import com.chat.messaging.message.ResponseListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.Task;

/**
 *
 * @author gdimitrova
 * @param <Req>
 * @param <Resp>
 */
public abstract class WorkerTask<Req extends Request, Resp extends Response> extends Task<Void> {

    private final static Logger LOGGER = Logger.getLogger(WorkerTask.class.getName());

    protected final ResponseListener<Resp> listener;

    protected final Req request;

    public WorkerTask(Req request, ResponseListener<Resp> listener) {
        this.request = request;
        this.listener = listener;
    }

    @Override
    protected Void call() throws Exception {
        if (ClientApp.getRegistry() == null) {
            try {
                ClientApp.connectToServer();
            } catch (Exception ex) {
                LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
                listener.onError(new ErrorMessageDto(ResponseCode.SERVER_ERROR, ex.getMessage()));
                return null;
            }
        }
        doWork();
        return null;
    }

    abstract protected void doWork();
}

package com.chat.task;

import com.chat.app.ClientApp;
import com.chat.messaging.dto.ErrorMessageDto;
import com.chat.messaging.message.Request;
import com.chat.messaging.message.Response;
import com.chat.messaging.message.ResponseCode;
import com.chat.messaging.message.ResponseListener;
import java.util.function.BiConsumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.Task;

/**
 *
 * @author gdimitrova
 * @param <Req>
 * @param <Resp>
 */
public class TaskWorker<Req extends Request, Resp extends Response> extends Task<Void> {

    private final static Logger LOGGER = Logger.getLogger(TaskWorker.class.getName());

    private final BiConsumer< Req, ResponseListener<Resp>> doWork;

    private final ResponseListener<Resp> listener;

    private final Req request;

    public TaskWorker(BiConsumer< Req, ResponseListener<Resp>> doWork, Req request, ResponseListener<Resp> listener) {
        this.doWork = doWork;
        this.request = request;
        this.listener = listener;
    }

    @Override
    protected Void call() throws Exception {
        if (ClientApp.isDisconnectedMode()) {
            return null;
        }
        if (ClientApp.getRegistry() == null) {
            try {
                ClientApp.connectToServer();
            } catch (Exception ex) {
                LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
                listener.onError(new ErrorMessageDto(ResponseCode.SERVER_ERROR, ex.getMessage()));
                return null;
            }
        }
        doWork.accept(request, listener);
        return null;
    }

}

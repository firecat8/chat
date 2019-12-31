package com.chat.task;

import com.chat.app.ClientApp;
import com.chat.messaging.dto.ErrorMessageDto;
import com.chat.messaging.message.Request;
import com.chat.messaging.message.Response;
import com.chat.messaging.message.ResponseCode;
import com.chat.messaging.message.ResponseListener;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;

/**
 *
 * @author gdimitrova
 * @param <Req>
 * @param <Resp>
 */
public class Task<Req extends Request, Resp extends Response> implements Runnable {

    private final static Logger LOGGER = Logger.getLogger(Task.class.getName());

    private final BiConsumer< Req, ResponseListener<Resp>> doWork;

    private final Consumer<Resp> onSuccess;

    private final Consumer<ErrorMessageDto> onError;

    private final Req request;

    public Task(BiConsumer< Req, ResponseListener<Resp>> work, Req request, Consumer<Resp> onSuccess, Consumer<ErrorMessageDto> onError) {
        this.doWork = work;
        this.request = request;
        this.onSuccess = onSuccess;
        this.onError = onError;
    }

    @Override
    public void run() {
        if (ClientApp.isDisconnectedMode()) {
            return;
        }
        if (ClientApp.getRegistry() == null) {
            try {
                ClientApp.connectToServer();
            } catch (Exception ex) {
                LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
                createListener().onError(new ErrorMessageDto(ResponseCode.SERVER_ERROR, ex.getMessage()));
                return;
            }
        }
        doWork.accept(request, createListener());
    }

    private ResponseListener<Resp> createListener() {
        return new ResponseListener<Resp>() {
            @Override
            public void onSuccess(Resp rsp) {
                Platform.runLater(() -> {
                    onSuccess.accept(rsp);
                });
            }

            @Override
            public void onError(ErrorMessageDto error) {
                Platform.runLater(() -> {
                    onError.accept(error);
                });
            }
        };
    }

}

package com.chat.task;

import com.chat.app.ChatApp;
import com.chat.messaging.dto.ErrorMessageDto;
import com.chat.messaging.dto.MessageDto;
import com.chat.messaging.message.ResponseCode;
import com.chat.messaging.message.ResponseListener;
import javafx.concurrent.Task;

/**
 *
 * @author gdimitrova
 * @param <Resp>
 */
public abstract class ActionTask< Resp extends MessageDto> extends Task<Void> {

    protected final ResponseListener<Resp> listener;

    public ActionTask(ResponseListener<Resp> listener) {
        this.listener = listener;
    }

    @Override
    protected Void call() throws Exception {
        if (ChatApp.registry == null) {
            listener.onError(new ErrorMessageDto(ResponseCode.SERVER_ERROR, "Disconnected"));
        } else {
            callAction();
        }
        return null;
    }

    abstract protected void callAction();
}

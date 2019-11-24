package com.chat.task;

import com.chat.app.ChatApp;
import com.chat.bl.service.messaging.ResponseListener;
import com.chat.bl.service.messaging.chat.SendMessageRequest;
import com.chat.domain.Chat;
import com.chat.domain.ChatEvent;
import com.chat.domain.User;
import javafx.concurrent.Task;

/**
 *
 * @author gdimitrova
 */
public class SendMessageTask extends Task<Void> {

    private final ResponseListener<ChatEvent> listener;

    private final SendMessageRequest request;

    public SendMessageTask(String message, User sender, Chat chat, ResponseListener<ChatEvent> listener) {
        this.listener = listener;
        this.request = new SendMessageRequest(message, sender, chat);
    }

    @Override
    protected Void call() throws Exception {
        ChatApp.registry.getChatService().sendMessage(request, listener);
        return null;
    }

}

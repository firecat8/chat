package com.chat.task;

import com.chat.app.ChatApp;
import com.chat.messaging.message.ResponseListener;
import com.chat.messaging.message.chat.SendMessageRequest;
import com.chat.messaging.dto.ChatEventMessageDto;
import com.chat.messaging.dto.ChatMessageDto;
import com.chat.messaging.dto.UserMessageDto;
import javafx.concurrent.Task;

/**
 *
 * @author gdimitrova
 */
public class SendMessageTask extends Task<Void> {

    private final ResponseListener<ChatEventMessageDto> listener;

    private final SendMessageRequest request;

    public SendMessageTask(String message, UserMessageDto sender, ChatMessageDto chat, ResponseListener<ChatEventMessageDto> listener) {
        this.listener = listener;
        this.request = new SendMessageRequest(message, sender, chat);
    }

    @Override
    protected Void call() throws Exception {
        ChatApp.registry.getChatService().sendMessage(request, listener);
        return null;
    }

}

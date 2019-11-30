package com.chat.task;

import com.chat.app.ChatApp;
import com.chat.messaging.message.ResponseListener;
import com.chat.messaging.message.chat.SendMessageRequest;
import com.chat.messaging.dto.ChatEventMessageDto;
import com.chat.messaging.dto.ChatMessageDto;
import com.chat.messaging.dto.UserMessageDto;

/**
 *
 * @author gdimitrova
 */
public class SendMessageTask extends ActionTask<ChatEventMessageDto> {

    private final SendMessageRequest request;

    public SendMessageTask(String message, UserMessageDto sender, ChatMessageDto chat, ResponseListener<ChatEventMessageDto> listener) {
        super(listener);
        this.request = new SendMessageRequest(message, sender, chat);
    }

    @Override
    protected void callAction() {
        ChatApp.registry.getChatService().sendMessage(request, listener);
    }

}

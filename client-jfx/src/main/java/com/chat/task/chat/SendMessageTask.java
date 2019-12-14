package com.chat.task.chat;

import static com.chat.app.ClientApp.registry;
import com.chat.messaging.message.ResponseListener;
import com.chat.messaging.message.chat.SendMessageRequest;
import com.chat.messaging.dto.ChatMessageDto;
import com.chat.messaging.dto.UserMessageDto;
import com.chat.messaging.message.chat.ChatEventResponse;
import com.chat.task.ActionTask;

/**
 *
 * @author gdimitrova
 */
public class SendMessageTask extends ActionTask<SendMessageRequest, ChatEventResponse> {

    public SendMessageTask(String message, UserMessageDto sender, ChatMessageDto chat, ResponseListener<ChatEventResponse> listener) {
        super(new SendMessageRequest(message, sender, chat), listener);
    }

    @Override
    protected void callAction() {
        registry.getChatService().sendMessage(request, listener);
    }

}

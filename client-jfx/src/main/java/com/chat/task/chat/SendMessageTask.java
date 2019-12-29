package com.chat.task.chat;

import com.chat.app.ClientApp;
import com.chat.messaging.message.ResponseListener;
import com.chat.messaging.message.chat.SendMessageRequest;
import com.chat.messaging.dto.ChatMessageDto;
import com.chat.messaging.dto.UserMessageDto;
import com.chat.messaging.message.chat.ChatEventResponse;
import com.chat.task.WorkerTask;

/**
 *
 * @author gdimitrova
 */
public class SendMessageTask extends WorkerTask<SendMessageRequest, ChatEventResponse> {

    public SendMessageTask(String message, UserMessageDto sender, ChatMessageDto chat, ResponseListener<ChatEventResponse> listener) {
        super(new SendMessageRequest(message, sender, chat), listener);
    }

    @Override
    protected void doWork() {
        ClientApp.getRegistry().getChatService().sendMessage(request, listener);
    }

}

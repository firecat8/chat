package com.chat.task.chat;

import com.chat.app.ClientApp;
import com.chat.messaging.dto.ChatMessageDto;
import com.chat.messaging.message.ResponseListener;
import com.chat.messaging.message.chat.ChatHistoryResponse;
import com.chat.messaging.message.chat.LoadHistoryRequest;
import com.chat.task.ActionTask;

/**
 *
 * @author gdimitrova
 */
public class LoadLastTenEventsTask extends ActionTask<LoadHistoryRequest, ChatHistoryResponse> {

    public LoadLastTenEventsTask(ChatMessageDto chat, ResponseListener<ChatHistoryResponse> listener) {
        super(new LoadHistoryRequest(chat), listener);
    }

    @Override
    protected void callAction() {
        ClientApp.registry.getChatService().loadLastTenEvents(request, listener);
    }

}

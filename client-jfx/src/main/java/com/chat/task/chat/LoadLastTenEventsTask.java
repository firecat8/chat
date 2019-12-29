package com.chat.task.chat;

import com.chat.app.ClientApp;
import com.chat.messaging.dto.ChatMessageDto;
import com.chat.messaging.message.ResponseListener;
import com.chat.messaging.message.chat.ChatHistoryResponse;
import com.chat.messaging.message.chat.LoadHistoryRequest;
import com.chat.task.WorkerTask;

/**
 *
 * @author gdimitrova
 */
public class LoadLastTenEventsTask extends WorkerTask<LoadHistoryRequest, ChatHistoryResponse> {

    public LoadLastTenEventsTask(ChatMessageDto chat, ResponseListener<ChatHistoryResponse> listener) {
        super(new LoadHistoryRequest(chat), listener);
    }

    @Override
    protected void doWork() {
        ClientApp.getRegistry().getChatService().loadLastTenEvents(request, listener);
    }

}

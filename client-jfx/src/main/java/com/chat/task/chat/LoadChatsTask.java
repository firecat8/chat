package com.chat.task.chat;

import com.chat.app.ClientApp;
import com.chat.messaging.dto.UserMessageDto;
import com.chat.messaging.message.ResponseListener;
import com.chat.messaging.message.chat.ChatsResponse;
import com.chat.messaging.message.chat.LoadChatsRequest;
import com.chat.task.WorkerTask;

/**
 *
 * @author gdimitrova
 */
public class LoadChatsTask extends WorkerTask<LoadChatsRequest, ChatsResponse> {

    public LoadChatsTask(UserMessageDto user, ResponseListener<ChatsResponse> listener) {
        super(new LoadChatsRequest(user.getId()), listener);
    }

    @Override
    protected void doWork() {
        ClientApp.getRegistry().getChatService().loadChats(request, listener);
    }

}

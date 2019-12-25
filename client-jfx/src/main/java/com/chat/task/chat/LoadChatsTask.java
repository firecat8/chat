package com.chat.task.chat;

import com.chat.app.ClientApp;
import com.chat.messaging.dto.UserMessageDto;
import com.chat.messaging.message.ResponseListener;
import com.chat.messaging.message.chat.ChatsResponse;
import com.chat.messaging.message.chat.LoadChatsRequest;
import com.chat.task.ActionTask;

/**
 *
 * @author gdimitrova
 */
public class LoadChatsTask extends ActionTask<LoadChatsRequest, ChatsResponse> {

    public LoadChatsTask(UserMessageDto user, ResponseListener<ChatsResponse> listener) {
        super(new LoadChatsRequest(user.getId()), listener);
    }

    @Override
    protected void callAction() {
        ClientApp.registry.getChatService().loadChats(request, listener);
    }

}

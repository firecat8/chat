package com.chat.task.chat;

import com.chat.app.ClientApp;
import com.chat.messaging.message.ResponseListener;
import com.chat.messaging.message.chat.ChatsResponse;
import com.chat.messaging.message.chat.FindChatRequest;
import com.chat.task.ActionTask;

/**
 *
 * @author gdimitrova
 */
public class FindChatTask extends ActionTask<FindChatRequest, ChatsResponse> {

    public FindChatTask(String chatName, ResponseListener<ChatsResponse> listener) {
        super(new FindChatRequest(chatName), listener);
    }

    @Override
    protected void callAction() {
        ClientApp.registry.getChatService().findChats(request, listener);
    }

}

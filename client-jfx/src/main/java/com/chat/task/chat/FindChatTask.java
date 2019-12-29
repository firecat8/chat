package com.chat.task.chat;

import com.chat.app.ClientApp;
import com.chat.messaging.message.ResponseListener;
import com.chat.messaging.message.chat.ChatsResponse;
import com.chat.messaging.message.chat.FindChatRequest;
import com.chat.task.WorkerTask;

/**
 *
 * @author gdimitrova
 */
public class FindChatTask extends WorkerTask<FindChatRequest, ChatsResponse> {

    public FindChatTask(String chatName, ResponseListener<ChatsResponse> listener) {
        super(new FindChatRequest(chatName), listener);
    }

    @Override
    protected void doWork() {
        ClientApp.getRegistry().getChatService().findChats(request, listener);
    }

}

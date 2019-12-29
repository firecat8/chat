package com.chat.task.chat;

import com.chat.app.ClientApp;
import com.chat.messaging.message.ResponseListener;
import com.chat.messaging.message.chat.ChatResponse;
import com.chat.messaging.message.chat.CreateChatRequest;
import com.chat.task.WorkerTask;

/**
 *
 * @author gdimitrova
 */
public class CreateChatTask extends WorkerTask<CreateChatRequest, ChatResponse> {
    
    public CreateChatTask(String name, ResponseListener<ChatResponse> listener) {
        super(new CreateChatRequest(name), listener);
    }
    
    @Override
    protected void doWork() {
        ClientApp.getRegistry().getChatService().createChat(request, listener);
    }
    
}

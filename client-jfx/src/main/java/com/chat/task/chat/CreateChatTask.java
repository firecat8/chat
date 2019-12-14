package com.chat.task.chat;

import com.chat.app.ClientApp;
import com.chat.messaging.dto.ChatTypeMsgDto;
import com.chat.messaging.message.ResponseListener;
import com.chat.messaging.message.chat.ChatResponse;
import com.chat.messaging.message.chat.CreateChatRequest;
import com.chat.task.ActionTask;

/**
 *
 * @author gdimitrova
 */
public class CreateChatTask extends ActionTask<CreateChatRequest, ChatResponse> {
    
    public CreateChatTask(String name, ResponseListener<ChatResponse> listener) {
        super(new CreateChatRequest(name, ChatTypeMsgDto.GROUP), listener);
    }
    
    @Override
    protected void callAction() {
        ClientApp.registry.getChatService().createChat(request, listener);
    }
    
}

package com.chat.task.chat;

import com.chat.app.ClientApp;
import com.chat.messaging.dto.ChatMessageDto;
import com.chat.messaging.dto.UserMessageDto;
import com.chat.messaging.message.ResponseListener;
import com.chat.messaging.message.chat.ChatEventResponse;
import com.chat.messaging.message.chat.SendFileRequest;
import com.chat.task.ActionTask;

/**
 *
 * @author gdimitrova
 */
public class SendFileTask extends ActionTask<SendFileRequest, ChatEventResponse> {
    
    public SendFileTask(String filename, byte[] file, UserMessageDto sender, ChatMessageDto chat,
            ResponseListener<ChatEventResponse> listener) {
        super(new SendFileRequest(filename, file, sender, chat), listener);
    }
    
    @Override
    protected void callAction() {
        ClientApp.registry.getChatService().sendFile(request, listener);
    }
    
}

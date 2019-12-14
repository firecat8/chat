package com.chat.task.chat;

import com.chat.app.ClientApp;
import com.chat.messaging.dto.ChatEventMessageDto;
import com.chat.messaging.dto.DownloadFile;
import com.chat.messaging.message.ResponseListener;
import com.chat.messaging.message.chat.DownloadFileRequest;
import com.chat.task.ActionTask;

/**
 *
 * @author gdimitrova
 */
public class DownloadFileTask extends ActionTask<DownloadFileRequest, DownloadFile>{

    public DownloadFileTask(ChatEventMessageDto event, ResponseListener<DownloadFile> listener) {
        super(new DownloadFileRequest(event), listener);
    }

    @Override
    protected void callAction() {
        ClientApp.registry.getChatService().downloadFile(request, listener);
    }
    
}

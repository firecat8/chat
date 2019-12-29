package com.chat.task.chat;

import com.chat.app.ClientApp;
import com.chat.messaging.dto.ChatEventMessageDto;
import com.chat.messaging.dto.DownloadFile;
import com.chat.messaging.message.ResponseListener;
import com.chat.messaging.message.chat.DownloadFileRequest;
import com.chat.task.WorkerTask;

/**
 *
 * @author gdimitrova
 */
public class DownloadFileTask extends WorkerTask<DownloadFileRequest, DownloadFile>{

    public DownloadFileTask(ChatEventMessageDto event, ResponseListener<DownloadFile> listener) {
        super(new DownloadFileRequest(event), listener);
    }

    @Override
    protected void doWork() {
        ClientApp.getRegistry().getChatService().downloadFile(request, listener);
    }
    
}

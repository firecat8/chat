package com.chat.messaging.message.chat;

import com.chat.messaging.message.AbstractRequest;
import com.chat.messaging.dto.ChatEventMessageDto;

/**
 *
 * @author gdimitrova
 */
public class DownloadFileRequest extends AbstractRequest {

    private final String fileName;

    public DownloadFileRequest(ChatEventMessageDto event) {
        this.fileName = event.getStorageFileName();
    }

    public String getFileName() {
        return fileName;
    }

}

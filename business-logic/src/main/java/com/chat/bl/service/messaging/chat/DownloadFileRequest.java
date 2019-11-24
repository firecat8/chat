package com.chat.bl.service.messaging.chat;

import com.chat.bl.service.messaging.AbstractRequest;
import com.chat.domain.ChatEvent;

/**
 *
 * @author gdimitrova
 */
public class DownloadFileRequest extends AbstractRequest {

    private final String fileName;

    public DownloadFileRequest(ChatEvent event) {
        this.fileName = event.getStorageFileName();
    }

    public String getFileName() {
        return fileName;
    }

}

package com.chat.bl.service.messaging.chat;

import com.chat.domain.Chat;
import com.chat.domain.User;

/**
 *
 * @author gdimitrova
 */
public class SendFileRequest extends AbstractChatEventRequest {

    private final byte[] file;

    public SendFileRequest(String filename, byte[] file, User sender, Chat chat) {
        super(filename, sender, chat);
        this.file = file;
    }

    public byte[] getFile() {
        return file;
    }

}

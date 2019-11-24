package com.chat.bl.service.messaging.chat;

import com.chat.domain.Chat;
import com.chat.domain.User;
import java.io.File;

/**
 *
 * @author gdimitrova
 */
public class SendFileRequest extends AbstractChatEventRequest {

    private final File file;

    public SendFileRequest(String filename, File file, User sender, Chat chat) {
        super(filename, sender, chat);
        this.file = file;
    }

    public File getFile() {
        return file;
    }

}

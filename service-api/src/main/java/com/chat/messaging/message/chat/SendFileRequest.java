package com.chat.messaging.message.chat;

import com.chat.messaging.dto.ChatMessageDto;
import com.chat.messaging.dto.UserMessageDto;

/**
 *
 * @author gdimitrova
 */
public class SendFileRequest extends AbstractChatEventRequest {

    private final byte[] file;

    public SendFileRequest(String filename, byte[] file, UserMessageDto sender, ChatMessageDto chat) {
        super(filename, sender, chat);
        this.file = file;
    }

    public byte[] getFile() {
        return file;
    }

}

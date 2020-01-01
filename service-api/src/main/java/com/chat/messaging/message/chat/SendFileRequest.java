package com.chat.messaging.message.chat;

import com.chat.messaging.vo.ChatVo;
import com.chat.messaging.vo.UserVo;

/**
 *
 * @author gdimitrova
 */
public class SendFileRequest extends AbstractChatEventRequest {

    private final byte[] file;

    public SendFileRequest(String filename, byte[] file, UserVo sender, ChatVo chat) {
        super(filename, sender, chat);
        this.file = file;
    }

    public byte[] getFile() {
        return file;
    }

}

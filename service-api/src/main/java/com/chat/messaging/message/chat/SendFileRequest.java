package com.chat.messaging.message.chat;

/**
 *
 * @author gdimitrova
 */
public class SendFileRequest extends AbstractChatEventRequest {

    private final byte[] file;

    public SendFileRequest(String filename, byte[] file,  Long sender, Long chat) {
        super(filename, sender, chat);
        this.file = file;
    }

    public byte[] getFile() {
        return file;
    }

}

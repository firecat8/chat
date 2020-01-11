package com.chat.messaging.message.chat;

/**
 *
 * @author gdimitrova
 */
public class SendLogRequest extends AbstractChatEventRequest {

    public SendLogRequest(String message, Long sender, Long chat) {
        super(message, sender, chat);
    }

}

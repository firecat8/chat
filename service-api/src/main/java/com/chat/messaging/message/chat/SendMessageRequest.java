package com.chat.messaging.message.chat;

/**
 *
 * @author gdimitrova
 */
public class SendMessageRequest extends AbstractChatEventRequest {

    public SendMessageRequest(String message, Long sender, Long chat) {
        super(message, sender, chat);
    }

}

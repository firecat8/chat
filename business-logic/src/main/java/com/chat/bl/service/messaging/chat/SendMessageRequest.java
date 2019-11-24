package com.chat.bl.service.messaging.chat;

import com.chat.domain.Chat;
import com.chat.domain.User;

/**
 *
 * @author gdimitrova
 */
public class SendMessageRequest extends AbstractChatEventRequest {

    public SendMessageRequest(String message, User sender, Chat chat) {
        super(message, sender, chat);
    }

}

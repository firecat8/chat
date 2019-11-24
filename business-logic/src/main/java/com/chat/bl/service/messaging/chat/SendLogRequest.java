package com.chat.bl.service.messaging.chat;

import com.chat.domain.Chat;
import com.chat.domain.User;

/**
 *
 * @author gdimitrova
 */
public class SendLogRequest extends AbstractChatEventRequest {

    public SendLogRequest(String message, User sender, Chat chat) {
        super(message, sender, chat);
    }

}

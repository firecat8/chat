package com.chat.messaging.message.chat;

import com.chat.messaging.dto.ChatMessageDto;
import com.chat.messaging.dto.UserMessageDto;

/**
 *
 * @author gdimitrova
 */
public class SendMessageRequest extends AbstractChatEventRequest {

    public SendMessageRequest(String message, UserMessageDto sender, ChatMessageDto chat) {
        super(message, sender, chat);
    }

}

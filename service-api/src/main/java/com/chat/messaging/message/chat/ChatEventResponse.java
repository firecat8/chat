package com.chat.messaging.message.chat;

import com.chat.messaging.dto.ChatEventMessageDto;
import com.chat.messaging.message.AbstractResponse;

/**
 *
 * @author gdimitrova
 */
public class ChatEventResponse extends AbstractResponse{
    private final ChatEventMessageDto chatEvent;

    public ChatEventResponse(ChatEventMessageDto chatEvent) {
        this.chatEvent = chatEvent;
    }

    public ChatEventMessageDto getChatEvent() {
        return chatEvent;
    }
    
}

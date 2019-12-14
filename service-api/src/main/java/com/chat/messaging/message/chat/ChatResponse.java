package com.chat.messaging.message.chat;

import com.chat.messaging.dto.ChatMessageDto;
import com.chat.messaging.message.AbstractResponse;

/**
 *
 * @author gdimitrova
 */
public class ChatResponse extends AbstractResponse {

    private final ChatMessageDto chat;

    public ChatResponse(ChatMessageDto chat) {
        this.chat = chat;
    }

    public ChatMessageDto getChat() {
        return chat;
    }

}

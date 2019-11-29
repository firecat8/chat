package com.chat.messaging.message.chat;

import com.chat.messaging.message.AbstractRequest;
import com.chat.messaging.dto.ChatMessageDto;

/**
 *
 * @author gdimitrova
 */
public class LoadHistoryRequest extends AbstractRequest {

    private final ChatMessageDto chat;

    public LoadHistoryRequest(ChatMessageDto chat) {
        this.chat = chat;
    }

    public ChatMessageDto getChat() {
        return chat;
    }

}

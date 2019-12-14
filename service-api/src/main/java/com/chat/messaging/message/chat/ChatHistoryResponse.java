package com.chat.messaging.message.chat;

import com.chat.messaging.dto.ChatEventMessageDto;
import com.chat.messaging.message.AbstractResponse;
import java.util.List;

/**
 *
 * @author gdimitrova
 */
public class ChatHistoryResponse extends AbstractResponse{
     private final List<ChatEventMessageDto> history;

    public ChatHistoryResponse(List<ChatEventMessageDto> history) {
        this.history = history;
    }

    public List<ChatEventMessageDto> getHistory() {
        return history;
    }

    
}

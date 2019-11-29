package com.chat.messaging.dto;

import java.util.List;

/**
 *
 * @author gdimitrova
 */
public class ChatHistoryMessageDto extends MessageDto {

    private final List<ChatEventMessageDto> history;

    public ChatHistoryMessageDto(List<ChatEventMessageDto> history) {
        this.history = history;
    }

    public List<ChatEventMessageDto> getHistory() {
        return history;
    }

}

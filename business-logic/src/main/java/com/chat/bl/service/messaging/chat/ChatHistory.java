package com.chat.bl.service.messaging.chat;

import com.chat.domain.ChatEvent;
import java.util.List;

/**
 *
 * @author gdimitrova
 */
public class ChatHistory {

    private final List<ChatEvent> history;

    public ChatHistory(List<ChatEvent> history) {
        this.history = history;
    }

    public List<ChatEvent> getHistory() {
        return history;
    }

}

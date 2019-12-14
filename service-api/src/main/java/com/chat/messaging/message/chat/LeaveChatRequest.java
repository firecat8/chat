package com.chat.messaging.message.chat;

import com.chat.messaging.message.AbstractRequest;

/**
 *
 * @author gdimitrova
 */
public class LeaveChatRequest extends AbstractRequest {

    private final Long chatId;

    private final Long userId;

    public LeaveChatRequest(Long chatId, Long userId) {
        this.chatId = chatId;
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getId() {
        return chatId;
    }
}

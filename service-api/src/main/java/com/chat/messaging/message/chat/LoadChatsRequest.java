package com.chat.messaging.message.chat;

import com.chat.messaging.message.AbstractRequest;

/**
 *
 * @author gdimitrova
 */
public class LoadChatsRequest extends AbstractRequest {

    private final Long userId;

    public LoadChatsRequest(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

}

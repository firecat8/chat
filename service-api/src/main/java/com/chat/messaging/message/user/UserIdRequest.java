package com.chat.messaging.message.user;

import com.chat.messaging.message.AbstractRequest;

/**
 *
 * @author gdimitrova
 */
public abstract class UserIdRequest extends AbstractRequest {

    private final Long userId;

    public UserIdRequest(Long userId) {
        this.userId = userId;
    }

    public Long getId() {
        return userId;
    }

}

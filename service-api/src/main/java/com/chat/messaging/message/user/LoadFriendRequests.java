package com.chat.messaging.message.user;

import com.chat.messaging.dto.UserMessageDto;
import com.chat.messaging.message.AbstractRequest;

/**
 *
 * @author gdimitrova
 */
public class LoadFriendRequests extends AbstractRequest {

    private final UserMessageDto user;

    public LoadFriendRequests(UserMessageDto user) {
        this.user = user;
    }

    public UserMessageDto getUser() {
        return user;
    }

}

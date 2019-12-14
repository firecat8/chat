package com.chat.messaging.message.user;

import com.chat.messaging.dto.UserMessageDto;
import com.chat.messaging.message.AbstractResponse;

/**
 *
 * @author gdimitrova
 */
public class UserResponse extends AbstractResponse{
    private final UserMessageDto user;

    public UserResponse(UserMessageDto user) {
        this.user = user;
    }

    public UserMessageDto getUser() {
        return user;
    }
    
}

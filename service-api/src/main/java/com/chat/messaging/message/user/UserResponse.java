package com.chat.messaging.message.user;

import com.chat.messaging.vo.UserVo;
import com.chat.messaging.message.AbstractResponse;

/**
 *
 * @author gdimitrova
 */
public class UserResponse extends AbstractResponse{
    private final UserVo user;

    public UserResponse(UserVo user) {
        this.user = user;
    }

    public UserVo getUser() {
        return user;
    }
    
}

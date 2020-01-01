package com.chat.messaging.message.user;

import com.chat.messaging.vo.UserStatusVo;
import com.chat.messaging.message.AbstractRequest;

/**
 *
 * @author gdimitrova
 */
public class ChangeStatusRequest extends AbstractRequest {

    private final String username;

    private final UserStatusVo status;

    public ChangeStatusRequest(UserStatusVo status, String username) {
        this.username = username;
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public UserStatusVo getStatus() {
        return status;
    }

}

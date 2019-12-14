package com.chat.messaging.message.user;

import com.chat.messaging.dto.UserStatusMsgDto;
import com.chat.messaging.message.AbstractRequest;

/**
 *
 * @author gdimitrova
 */
public class ChangeStatusRequest extends AbstractRequest {

    private final String username;

    private final UserStatusMsgDto status;

    public ChangeStatusRequest(UserStatusMsgDto status, String username) {
        this.username = username;
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public UserStatusMsgDto getStatus() {
        return status;
    }

}

package com.chat.messaging.message.user;

import com.chat.messaging.dto.UserStatusMsgDto;

/**
 *
 * @author gdimitrova
 */
public class ChangeStatusRequest extends LoginRequest {

    private final UserStatusMsgDto status;

    public ChangeStatusRequest(UserStatusMsgDto status, String username, String password) {
        super(username, password);
        this.status = status;
    }

    public UserStatusMsgDto getStatus() {
        return status;
    }

}

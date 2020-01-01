package com.chat.controller.chat;

import com.chat.messaging.vo.UserStatusVo;

/**
 *
 * @author gdimitrova
 */
class UserStatusSelectionItem {

    private final UserStatusVo userStatus;

    private final String statusLabel;

    public UserStatusSelectionItem(UserStatusVo userStatus, String statusLabel) {
        this.userStatus = userStatus;
        this.statusLabel = statusLabel;
    }

    public String getStatusLabel() {
        return statusLabel;
    }

    public UserStatusVo getUserStatus() {
        return userStatus;
    }

}

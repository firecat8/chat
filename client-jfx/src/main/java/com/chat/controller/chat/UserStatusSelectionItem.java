package com.chat.controller.chat;

import com.chat.messaging.dto.UserStatusMsgDto;

/**
 *
 * @author gdimitrova
 */
class UserStatusSelectionItem {

    private final UserStatusMsgDto userStatus;

    private final String statusLabel;

    public UserStatusSelectionItem(UserStatusMsgDto userStatus, String statusLabel) {
        this.userStatus = userStatus;
        this.statusLabel = statusLabel;
    }

    public String getStatusLabel() {
        return statusLabel;
    }

    public UserStatusMsgDto getUserStatus() {
        return userStatus;
    }

}

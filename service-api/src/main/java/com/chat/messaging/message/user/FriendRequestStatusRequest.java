package com.chat.messaging.message.user;

import com.chat.messaging.vo.FriendRequestVo;
import com.chat.messaging.message.AbstractRequest;

/**
 *
 * @author gdimitrova
 */
public class FriendRequestStatusRequest extends AbstractRequest {

    private final FriendRequestVo friendRequest;

    public FriendRequestStatusRequest(FriendRequestVo friendRequest) {
        this.friendRequest = friendRequest;
    }

    public FriendRequestVo getFriendRequest() {
        return friendRequest;
    }

}

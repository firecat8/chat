package com.chat.messaging.message.user;

import com.chat.messaging.vo.FriendRequestVo;
import com.chat.messaging.message.AbstractResponse;

/**
 *
 * @author gdimitrova
 */
public class FriendRequestResponse  extends AbstractResponse{
    private final FriendRequestVo friendRequest;

    public FriendRequestResponse(FriendRequestVo friendRequest) {
        this.friendRequest = friendRequest;
    }

    public FriendRequestVo getFriendRequest() {
        return friendRequest;
    }
    
}

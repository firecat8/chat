package com.chat.messaging.message.user;

import com.chat.messaging.dto.FriendRequestMessageDto;
import com.chat.messaging.message.AbstractResponse;

/**
 *
 * @author gdimitrova
 */
public class FriendRequestResponse  extends AbstractResponse{
    private final FriendRequestMessageDto friendRequest;

    public FriendRequestResponse(FriendRequestMessageDto friendRequest) {
        this.friendRequest = friendRequest;
    }

    public FriendRequestMessageDto getFriendRequest() {
        return friendRequest;
    }
    
}

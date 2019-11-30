package com.chat.messaging.message.user;

import com.chat.messaging.dto.FriendRequestMessageDto;
import com.chat.messaging.message.AbstractRequest;

/**
 *
 * @author gdimitrova
 */
public class FriendRequestStatusRequest extends AbstractRequest {

    private final FriendRequestMessageDto friendRequest;

    public FriendRequestStatusRequest(FriendRequestMessageDto friendRequest) {
        this.friendRequest = friendRequest;
    }

    public FriendRequestMessageDto getFriendRequest() {
        return friendRequest;
    }

}

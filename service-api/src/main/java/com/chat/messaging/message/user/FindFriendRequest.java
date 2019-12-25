package com.chat.messaging.message.user;

import com.chat.messaging.message.AbstractRequest;

/**
 *
 * @author gdimitrova
 */
public class FindFriendRequest extends AbstractRequest{
    private final String friendName;

    public FindFriendRequest(String friendName) {
        this.friendName = friendName;
    }

    public String getFriendName() {
        return friendName;
    }
    
}

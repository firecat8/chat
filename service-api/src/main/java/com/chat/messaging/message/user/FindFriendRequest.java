package com.chat.messaging.message.user;

import com.chat.messaging.message.AbstractRequest;

/**
 *
 * @author gdimitrova
 */
public class FindFriendRequest extends AbstractRequest {

    private final String friendName;

    private final Long searcherId;

    public FindFriendRequest(String friendName, Long searcherId) {
        this.friendName = friendName;
        this.searcherId = searcherId;
    }

    public Long getSearcherId() {
        return searcherId;
    }

    public String getFriendName() {
        return friendName;
    }

}

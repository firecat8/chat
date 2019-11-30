package com.chat.domain;

/**
 *
 * @author gdimitrova
 */
public class FriendRequest extends Entity {

    private final User sender;

    private final User receiver;

    private final FriendRequestStatus requestStatus;

    public FriendRequest(User sender, User receiver, FriendRequestStatus requestStatus) {
        this.sender = sender;
        this.receiver = receiver;
        this.requestStatus = requestStatus;
    }

    public User getSender() {
        return sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public FriendRequestStatus getRequestStatus() {
        return requestStatus;
    }

}

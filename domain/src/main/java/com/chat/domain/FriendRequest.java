package com.chat.domain;

/**
 *
 * @author gdimitrova
 */
public class FriendRequest extends Entity {

    private User sender;

    private User receiver;

    private FriendRequestStatus requestStatus;

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

    public void setSender(User sender) {
        this.sender = sender;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public void setRequestStatus(FriendRequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }

}

package com.chat.messaging.vo;

/**
 *
 * @author gdimitrova
 */
public class FriendRequestVo extends EntityVo {

    private final UserVo sender;

    private final UserVo receiver;

    private final FriendRequestStatusVo requestStatus;

    public FriendRequestVo(UserVo sender, UserVo receiver, FriendRequestStatusVo requestStatus) {
        this.sender = sender;
        this.receiver = receiver;
        this.requestStatus = requestStatus;
    }

    public UserVo getSender() {
        return sender;
    }

    public UserVo getReceiver() {
        return receiver;
    }

    public FriendRequestStatusVo getRequestStatus() {
        return requestStatus;
    }
}

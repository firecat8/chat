package com.chat.messaging.dto;

/**
 *
 * @author gdimitrova
 */
public class FriendRequestMessageDto extends MessageDto {

    private final UserMessageDto sender;

    private final UserMessageDto receiver;

    private final FriendRequestStatusMsgDto requestStatus;

    public FriendRequestMessageDto(UserMessageDto sender, UserMessageDto receiver, FriendRequestStatusMsgDto requestStatus) {
        this.sender = sender;
        this.receiver = receiver;
        this.requestStatus = requestStatus;
    }

    public UserMessageDto getSender() {
        return sender;
    }

    public UserMessageDto getReceiver() {
        return receiver;
    }

    public FriendRequestStatusMsgDto getRequestStatus() {
        return requestStatus;
    }
}

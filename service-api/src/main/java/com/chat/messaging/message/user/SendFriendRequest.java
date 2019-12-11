package com.chat.messaging.message.user;

import com.chat.messaging.message.AbstractRequest;

/**
 *
 * @author gdimitrova
 */
public class SendFriendRequest extends AbstractRequest {

    private final Long senderId;

    private final Long receiverId;

    public SendFriendRequest(Long senderId, Long receiverId) {
        this.senderId = senderId;
        this.receiverId = receiverId;
    }

    public Long getSenderId() {
        return senderId;
    }

    public Long getReceiverId() {
        return receiverId;
    }


}

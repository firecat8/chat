package com.chat.messaging.message.user;

import com.chat.messaging.dto.UserMessageDto;
import com.chat.messaging.message.AbstractRequest;

/**
 *
 * @author gdimitrova
 */
public class SendFriendRequest extends AbstractRequest {

    private final UserMessageDto sender;

    private final UserMessageDto receiver;

    public SendFriendRequest(UserMessageDto sender, UserMessageDto receiver) {
        this.sender = sender;
        this.receiver = receiver;
    }

    public UserMessageDto getSender() {
        return sender;
    }

    public UserMessageDto getReceiver() {
        return receiver;
    }

}

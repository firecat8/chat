package com.chat.task.user;

import com.chat.messaging.dto.FriendRequestMessageDto;
import com.chat.messaging.message.ResponseListener;
import com.chat.messaging.message.user.SendFriendRequest;
import com.chat.task.ActionTask;

/**
 *
 * @author gdimitrova
 */
public class SendFriendRequestTask extends ActionTask<SendFriendRequest, FriendRequestMessageDto>{

    public SendFriendRequestTask(Long userId,Long friendId, ResponseListener<FriendRequestMessageDto> listener) {
        super(new SendFriendRequest(Long.MIN_VALUE, Long.MIN_VALUE), listener);
    }

    @Override
    protected void callAction() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

package com.chat.task.user;

import com.chat.app.ClientApp;
import com.chat.messaging.message.ResponseListener;
import com.chat.messaging.message.user.FriendRequestResponse;
import com.chat.messaging.message.user.SendFriendRequest;
import com.chat.task.ActionTask;

/**
 *
 * @author gdimitrova
 */
public class SendFriendRequestTask extends ActionTask<SendFriendRequest, FriendRequestResponse> {

    public SendFriendRequestTask(Long userId, Long friendId, ResponseListener<FriendRequestResponse> listener) {
        super(new SendFriendRequest(userId, friendId), listener);
    }

    @Override
    protected void callAction() {
        ClientApp.registry.getUserService().sendFriendRequest(request, listener);
    }

}

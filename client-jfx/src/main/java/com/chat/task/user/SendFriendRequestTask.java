package com.chat.task.user;

import com.chat.app.ClientApp;
import com.chat.messaging.message.ResponseListener;
import com.chat.messaging.message.user.FriendRequestResponse;
import com.chat.messaging.message.user.SendFriendRequest;
import com.chat.task.WorkerTask;

/**
 *
 * @author gdimitrova
 */
public class SendFriendRequestTask extends WorkerTask<SendFriendRequest, FriendRequestResponse> {

    public SendFriendRequestTask(Long userId, Long friendId, ResponseListener<FriendRequestResponse> listener) {
        super(new SendFriendRequest(userId, friendId), listener);
    }

    @Override
    protected void doWork() {
        ClientApp.getRegistry().getUserService().sendFriendRequest(request, listener);
    }

}

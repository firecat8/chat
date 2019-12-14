package com.chat.task.user;

import com.chat.messaging.message.ResponseListener;
import com.chat.messaging.message.user.LoadFriendRequests;
import com.chat.messaging.message.user.FriendRequestsResponse;
import com.chat.task.ActionTask;

/**
 *
 * @author gdimitrova
 */
public abstract class AbstractFriendRequestsTask extends ActionTask<LoadFriendRequests, FriendRequestsResponse> {

    public AbstractFriendRequestsTask(Long userId, ResponseListener<FriendRequestsResponse> listener) {
        super(new LoadFriendRequests(userId), listener);
    }

}

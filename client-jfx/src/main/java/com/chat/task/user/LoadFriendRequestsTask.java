package com.chat.task.user;

import com.chat.app.ClientApp;
import com.chat.messaging.message.ResponseListener;
import com.chat.messaging.message.user.FriendRequestsResponse;

/**
 *
 * @author gdimitrova
 */
public class LoadFriendRequestsTask extends AbstractFriendRequestsTask{

    public LoadFriendRequestsTask(Long userId, ResponseListener<FriendRequestsResponse> listener) {
        super(userId, listener);
    }

    @Override
    protected void callAction() {
        ClientApp.registry.getUserService().loadFriendRequests(request, listener);
    }
    
}

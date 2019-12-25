package com.chat.task.user;

import com.chat.app.ClientApp;
import com.chat.messaging.message.ResponseListener;
import com.chat.messaging.message.user.FindFriendRequest;
import com.chat.messaging.message.user.UsersResponse;
import com.chat.task.ActionTask;

/**
 *
 * @author gdimitrova
 */
public class FindFriendTask extends ActionTask<FindFriendRequest, UsersResponse> {

    public FindFriendTask(String friendName, ResponseListener<UsersResponse> listener) {
        super(new FindFriendRequest(friendName), listener);
    }

    @Override
    protected void callAction() {
        ClientApp.registry.getUserService().findFriend(request, listener);
    }

}

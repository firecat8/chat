package com.chat.task.chat;

import com.chat.app.ClientApp;
import com.chat.messaging.message.ResponseListener;
import com.chat.messaging.message.SuccessResponse;
import com.chat.messaging.message.chat.AddFriendRequest;
import com.chat.task.WorkerTask;

/**
 *
 * @author gdimitrova
 */
public class AddFriendTask extends WorkerTask<AddFriendRequest, SuccessResponse> {

    public AddFriendTask(Long chatId, Long userId, Long friendId, ResponseListener<SuccessResponse> listener) {
        super(new AddFriendRequest(chatId, userId, friendId), listener);
    }

    @Override
    protected void doWork() {
        ClientApp.getRegistry().getChatService().addFriend(request, listener);
    }
}

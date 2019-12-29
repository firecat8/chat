package com.chat.task.chat;

import com.chat.app.ClientApp;
import com.chat.messaging.message.ResponseListener;
import com.chat.messaging.message.SuccessResponse;
import com.chat.messaging.message.chat.LeaveChatRequest;
import com.chat.task.WorkerTask;

/**
 *
 * @author gdimitrova
 */
public class LeaveChatTask extends WorkerTask<LeaveChatRequest, SuccessResponse> {

    public LeaveChatTask(Long chatId, Long userId, ResponseListener<SuccessResponse> listener) {
        super(new LeaveChatRequest(chatId, userId), listener);
    }

    @Override
    protected void doWork() {
        ClientApp.getRegistry().getChatService().leaveChat(request, listener);
    }

}

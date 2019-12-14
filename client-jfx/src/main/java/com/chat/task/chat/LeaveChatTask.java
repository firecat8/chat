package com.chat.task.chat;

import com.chat.app.ClientApp;
import com.chat.messaging.message.ResponseListener;
import com.chat.messaging.message.SuccessResponse;
import com.chat.messaging.message.chat.LeaveChatRequest;
import com.chat.task.ActionTask;

/**
 *
 * @author gdimitrova
 */
public class LeaveChatTask extends ActionTask<LeaveChatRequest, SuccessResponse> {

    public LeaveChatTask(Long chatId, Long userId, ResponseListener<SuccessResponse> listener) {
        super(new LeaveChatRequest(chatId, userId), listener);
    }

    @Override
    protected void callAction() {
        ClientApp.registry.getChatService().leaveChat(request, listener);
    }

}

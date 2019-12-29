package com.chat.task.user;

import com.chat.app.ClientApp;
import com.chat.messaging.message.ResponseListener;
import com.chat.messaging.message.SuccessResponse;
import com.chat.messaging.message.user.LogoutRequest;
import com.chat.task.WorkerTask;

/**
 *
 * @author gdimitrova
 */
public class LogoutTask extends WorkerTask<LogoutRequest, SuccessResponse> {

    public LogoutTask(Long userId, ResponseListener<SuccessResponse> listener) {
        super(new LogoutRequest(userId), listener);
    }

    @Override
    protected void doWork() {
        ClientApp.getRegistry().getUserService().logout(request, listener);
    }

}

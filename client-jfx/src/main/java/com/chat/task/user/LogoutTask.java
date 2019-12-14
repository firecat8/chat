package com.chat.task.user;

import static com.chat.app.ClientApp.registry;
import com.chat.messaging.message.ResponseListener;
import com.chat.messaging.message.SuccessResponse;
import com.chat.messaging.message.user.LogoutRequest;
import com.chat.task.ActionTask;

/**
 *
 * @author gdimitrova
 */
public class LogoutTask extends ActionTask<LogoutRequest, SuccessResponse> {

    public LogoutTask(Long userId, ResponseListener<SuccessResponse> listener) {
        super(new LogoutRequest(userId), listener);
    }

    @Override
    protected void callAction() {
        registry.getUserService().logout(request, listener);
    }

}

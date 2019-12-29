package com.chat.task.user;

import com.chat.app.ClientApp;
import com.chat.messaging.message.ResponseListener;
import com.chat.messaging.message.user.LoginRequest;
import com.chat.messaging.message.user.UserResponse;
import com.chat.task.WorkerTask;

/**
 *
 * @author gdimitrova
 */
public class LoginTask extends WorkerTask<LoginRequest, UserResponse> {

    public LoginTask(String username, String password, ResponseListener<UserResponse> listener) {
        super(new LoginRequest(username, password), listener);
    }

    @Override
    protected void doWork() {
        ClientApp.getRegistry().getUserService().login(request, listener);
    }

}

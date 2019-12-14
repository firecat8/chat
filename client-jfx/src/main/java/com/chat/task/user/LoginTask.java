package com.chat.task.user;

import static com.chat.app.ClientApp.registry;
import com.chat.messaging.message.ResponseListener;
import com.chat.messaging.message.user.LoginRequest;
import com.chat.messaging.message.user.UserResponse;
import com.chat.task.ActionTask;

/**
 *
 * @author gdimitrova
 */
public class LoginTask extends ActionTask<LoginRequest, UserResponse> {

    public LoginTask(String username, String password, ResponseListener<UserResponse> listener) {
        super(new LoginRequest(username, password), listener);
    }

    @Override
    protected void callAction() {
        registry.getUserService().login(request, listener);
    }

}

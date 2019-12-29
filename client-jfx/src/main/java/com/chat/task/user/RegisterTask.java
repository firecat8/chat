package com.chat.task.user;

import com.chat.app.ClientApp;
import com.chat.messaging.message.ResponseListener;
import com.chat.messaging.message.user.RegistrationRequest;
import com.chat.messaging.message.user.UserResponse;
import com.chat.task.WorkerTask;

/**
 *
 * @author gdimitrova
 */
public class RegisterTask extends WorkerTask<RegistrationRequest, UserResponse> {

    public RegisterTask(String username, String password, String firstName, String lastName, ResponseListener<UserResponse> listener) {
        super(new RegistrationRequest(username, password, firstName, lastName), listener);
    }

    @Override
    protected void doWork() {
        ClientApp.getRegistry().getUserService().register(request, listener);
    }
}

package com.chat.task;

import com.chat.app.ChatApp;
import com.chat.bl.service.messaging.ResponseListener;
import com.chat.bl.service.messaging.user.UserRequest;
import com.chat.domain.User;
import javafx.concurrent.Task;

/**
 *
 * @author gdimitrova
 */
public class LogoutTask extends Task<Void> {

    private final UserRequest userRequest;

    private final ResponseListener<User> listener;

    public LogoutTask(String username, String password, ResponseListener<User> listener) {
        userRequest = new UserRequest(username, password);
        this.listener = listener;
    }

    @Override
    protected Void call() throws Exception {
        ChatApp.registry.getUserService().logout(userRequest, listener);
        return null;
    }

}

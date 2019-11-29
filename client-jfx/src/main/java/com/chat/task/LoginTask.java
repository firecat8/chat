package com.chat.task;

import com.chat.app.ChatApp;
import com.chat.messaging.message.ResponseListener;
import com.chat.messaging.message.user.UserRequest;
import com.chat.messaging.dto.UserMessageDto;
import javafx.concurrent.Task;

/**
 *
 * @author gdimitrova
 */
public class LoginTask extends Task<Void> {

    private final UserRequest userRequest;

    private final ResponseListener<UserMessageDto> listener;

    public LoginTask(String username, String password, ResponseListener<UserMessageDto> listener) {
        userRequest = new UserRequest(username, password);
        this.listener = listener;
    }

    @Override
    protected Void call() throws Exception {
        ChatApp.registry.getUserService().login(userRequest, listener);
        return null;
    }

}

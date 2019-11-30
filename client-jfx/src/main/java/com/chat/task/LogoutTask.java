package com.chat.task;

import com.chat.app.ChatApp;
import com.chat.messaging.message.ResponseListener;
import com.chat.messaging.message.user.UserRequest;
import com.chat.messaging.dto.UserMessageDto;

/**
 *
 * @author gdimitrova
 */
public class LogoutTask extends ActionTask<UserMessageDto> {

    private final UserRequest userRequest;

    public LogoutTask(String username, String password, ResponseListener<UserMessageDto> listener) {
        super(listener);
        userRequest = new UserRequest(username, password);
    }

    @Override
    protected void callAction() {
        ChatApp.registry.getUserService().logout(userRequest, listener);
    }

}

package com.chat.task;

import com.chat.app.ChatApp;
import com.chat.messaging.message.ResponseListener;
import com.chat.messaging.message.user.RegistrationRequest;
import com.chat.messaging.dto.UserMessageDto;

/**
 *
 * @author gdimitrova
 */
public class RegisterTask extends ActionTask<UserMessageDto> {

    private final RegistrationRequest request;

    public RegisterTask(String username, String password, String firstname, String lastname, String email, String phone, String city, ResponseListener<UserMessageDto> listener) {
        super(listener);
        request = new RegistrationRequest(username, password, firstname, lastname, email, phone, city);
    }

    @Override
    protected void callAction() {
        ChatApp.registry.getUserService().register(request, listener);
    }
}

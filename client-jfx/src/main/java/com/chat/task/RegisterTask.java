package com.chat.task;

import com.chat.app.ChatApp;
import com.chat.messaging.message.ResponseListener;
import com.chat.messaging.message.user.RegistrationRequest;
import com.chat.messaging.dto.UserMessageDto;
import javafx.concurrent.Task;

/**
 *
 * @author gdimitrova
 */
public class RegisterTask extends Task<Void> {

    private final ResponseListener<UserMessageDto> listener;

    private final RegistrationRequest request;

    public RegisterTask(String username, String password, String firstname, String lastname, String email, String phone, String city, ResponseListener<UserMessageDto> listener) {
        this.listener = listener;
        request = new RegistrationRequest(username, password, firstname, lastname, email, phone, city);
    }

    @Override
    protected Void call() throws Exception {
        ChatApp.registry.getUserService().register(request, listener);
        return null;
    }

}

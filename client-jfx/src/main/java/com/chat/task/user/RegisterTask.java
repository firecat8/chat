package com.chat.task.user;

import static com.chat.app.ClientApp.registry;
import com.chat.messaging.message.ResponseListener;
import com.chat.messaging.message.user.RegistrationRequest;
import com.chat.messaging.dto.UserMessageDto;
import com.chat.task.ActionTask;

/**
 *
 * @author gdimitrova
 */
public class RegisterTask extends ActionTask<RegistrationRequest, UserMessageDto> {

    public RegisterTask(String username, String password, String firstName, String lastName, ResponseListener<UserMessageDto> listener) {
        super(new RegistrationRequest(username, password, firstName, lastName), listener);
    }

    @Override
    protected void callAction() {
        registry.getUserService().register(request, listener);
    }
}

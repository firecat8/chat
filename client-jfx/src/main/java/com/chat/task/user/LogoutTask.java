package com.chat.task.user;

import static com.chat.app.ClientApp.registry;
import com.chat.messaging.message.ResponseListener;
import com.chat.messaging.dto.UserMessageDto;
import com.chat.messaging.message.user.LogoutRequest;
import com.chat.task.ActionTask;

/**
 *
 * @author gdimitrova
 */
public class LogoutTask extends ActionTask<LogoutRequest, UserMessageDto> {

    public LogoutTask(Long userId, ResponseListener<UserMessageDto> listener) {
        super(new LogoutRequest(userId), listener);
    }

    @Override
    protected void callAction() {
        registry.getUserService().logout(request, listener);
    }

}

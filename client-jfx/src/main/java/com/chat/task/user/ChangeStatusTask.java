package com.chat.task.user;

import static com.chat.app.ClientApp.registry;
import com.chat.messaging.dto.UserMessageDto;
import com.chat.messaging.dto.UserStatusMsgDto;
import com.chat.messaging.message.ResponseListener;
import com.chat.messaging.message.user.ChangeStatusRequest;
import com.chat.task.ActionTask;

/**
 *
 * @author gdimitrova
 */
public class ChangeStatusTask extends ActionTask<ChangeStatusRequest, UserMessageDto> {

    public ChangeStatusTask(UserStatusMsgDto status, String username, String password, ResponseListener<UserMessageDto> listener) {
        super(new ChangeStatusRequest(status, username, password), listener);
    }

    @Override
    protected void callAction() {
        registry.getUserService().changeStatus(request, listener);
    }

}

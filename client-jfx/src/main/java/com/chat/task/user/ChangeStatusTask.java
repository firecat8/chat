package com.chat.task.user;

import static com.chat.app.ClientApp.registry;
import com.chat.messaging.dto.UserStatusMsgDto;
import com.chat.messaging.message.ResponseListener;
import com.chat.messaging.message.SuccessResponse;
import com.chat.messaging.message.user.ChangeStatusRequest;
import com.chat.task.ActionTask;

/**
 *
 * @author gdimitrova
 */
public class ChangeStatusTask extends ActionTask<ChangeStatusRequest, SuccessResponse> {

    public ChangeStatusTask(UserStatusMsgDto status, String username, ResponseListener<SuccessResponse> listener) {
        super(new ChangeStatusRequest(status, username), listener);
    }

    @Override
    protected void callAction() {
        registry.getUserService().changeStatus(request, listener);
    }

}

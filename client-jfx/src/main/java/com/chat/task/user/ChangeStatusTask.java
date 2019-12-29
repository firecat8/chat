package com.chat.task.user;

import com.chat.app.ClientApp;
import com.chat.messaging.dto.UserStatusMsgDto;
import com.chat.messaging.message.ResponseListener;
import com.chat.messaging.message.SuccessResponse;
import com.chat.messaging.message.user.ChangeStatusRequest;
import com.chat.task.WorkerTask;

/**
 *
 * @author gdimitrova
 */
public class ChangeStatusTask extends WorkerTask<ChangeStatusRequest, SuccessResponse> {

    public ChangeStatusTask(UserStatusMsgDto status, String username, ResponseListener<SuccessResponse> listener) {
        super(new ChangeStatusRequest(status, username), listener);
    }

    @Override
    protected void doWork() {
        ClientApp.getRegistry().getUserService().changeStatus(request, listener);
    }

}

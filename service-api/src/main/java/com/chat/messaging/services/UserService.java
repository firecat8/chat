package com.chat.messaging.services;

import com.chat.messaging.message.user.RegistrationRequest;
import com.chat.messaging.message.ResponseListener;
import com.chat.messaging.dto.UserMessageDto;
import com.chat.messaging.message.user.ChangeStatusRequest;
import com.chat.messaging.message.user.UserRequest;

/**
 *
 * @author gdimitrova
 */
public interface UserService {

    public void login(UserRequest req, ResponseListener<UserMessageDto> listener);

    public void logout(UserRequest req, ResponseListener<UserMessageDto> listener);

    public void changeStatus(ChangeStatusRequest req, ResponseListener<UserMessageDto> listener);

    public void register(RegistrationRequest request, ResponseListener<UserMessageDto> listener);
}

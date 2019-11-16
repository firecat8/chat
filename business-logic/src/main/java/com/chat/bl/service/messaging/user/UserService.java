package com.chat.bl.service.messaging.user;

import com.chat.bl.service.messaging.ResponseListener;
import com.chat.domain.User;

/**
 *
 * @author gdimitrova
 */
public interface UserService {

    public void login(UserRequest req, ResponseListener<User> listener);

    public void logout(UserRequest req, ResponseListener<User> listener);

    public void register(RegistrationRequest request, ResponseListener<User> listener);
}

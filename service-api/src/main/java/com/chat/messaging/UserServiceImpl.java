package com.chat.messaging;

import com.chat.bl.service.messaging.ResponseListener;
import com.chat.bl.service.messaging.user.RegistrationRequest;
import com.chat.bl.service.messaging.user.UserRequest;
import com.chat.bl.service.messaging.user.UserService;
import com.chat.domain.User;

/**
 *
 * @author gdimitrova
 */
public class UserServiceImpl implements UserService, ServicePoint {

    private Client client;

    public UserServiceImpl(Client client) {
        this.client = client;
    }

    @Override
    public void login(UserRequest req, ResponseListener<User> listener) {
        client.sendMessage(this.getClass(), "login", req, User.class, listener);
    }

    @Override
    public void logout(UserRequest req, ResponseListener<User> listener) {
        client.sendMessage(this.getClass(), "logout", req, User.class, listener);
    }

    @Override
    public void register(RegistrationRequest req, ResponseListener<User> listener) {
        client.sendMessage(this.getClass(), "register", req, User.class, listener);
    }

}

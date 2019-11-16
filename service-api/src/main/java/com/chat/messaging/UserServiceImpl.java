package com.chat.messaging;

import com.chat.bl.service.messaging.response.ResponseImpl;
import com.chat.bl.service.messaging.user.RegistrationRequest;
import com.chat.bl.service.messaging.user.UserRequest;
import com.chat.bl.service.messaging.user.UserService;

/**
 *
 * @author gdimitrova
 */
public class UserServiceImpl implements UserService,ServicePoint {

    private Client client;

    public UserServiceImpl(Client client) {
        this.client = client;
    }

    @Override
    public ResponseImpl login(UserRequest req) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ResponseImpl logout(UserRequest req) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ResponseImpl register(RegistrationRequest request) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

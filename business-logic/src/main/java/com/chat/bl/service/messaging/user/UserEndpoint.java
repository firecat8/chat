package com.chat.bl.service.messaging.user;

import com.chat.bl.service.dao.ServiceProviderRegistry;
import com.chat.bl.service.dao.UserServiceProvider;
import com.chat.bl.service.messaging.EndPoint;
import com.chat.bl.service.messaging.response.ResponseImpl;
import com.chat.domain.User;

/**
 *
 * @author gdimitrova
 */
public class UserEndpoint implements EndPoint, UserService {

    private UserServiceProvider provider;

    public UserEndpoint(ServiceProviderRegistry registry) {
        provider = registry.getUserServiceProvider();
    }

    @Override
    public synchronized ResponseImpl login(UserRequest req) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public synchronized ResponseImpl logout(UserRequest req) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public synchronized ResponseImpl register(RegistrationRequest req) {
        User user = provider.register(
                req.getUsername(), req.getPassword(), req.getFirstname(),
                req.getLastname(), req.getEmail(), req.getEmail(), req.getCity());
        return null;
    }

}

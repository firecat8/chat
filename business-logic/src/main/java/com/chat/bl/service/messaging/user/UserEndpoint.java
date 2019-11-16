package com.chat.bl.service.messaging.user;

import com.chat.bl.service.dao.ServiceProviderRegistry;
import com.chat.bl.service.dao.UserServiceProvider;
import com.chat.bl.service.messaging.EndPoint;
import com.chat.domain.User;

/**
 *
 * @author gdimitrova
 */
public class UserEndpoint implements EndPoint {

    private UserServiceProvider provider;

    public UserEndpoint(ServiceProviderRegistry registry) {
        provider = registry.getUserServiceProvider();
    }

    public synchronized User login(UserRequest req) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public synchronized User logout(UserRequest req) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public synchronized User register(RegistrationRequest req) {
        User user = provider.register(
                req.getUsername(), req.getPassword(), req.getFirstname(),
                req.getLastname(), req.getEmail(), req.getEmail(), req.getCity());
        return user;
    }

}

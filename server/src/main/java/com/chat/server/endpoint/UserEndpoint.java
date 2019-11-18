package com.chat.server.endpoint;

import com.chat.bl.service.dao.MessageException;
import com.chat.bl.service.dao.ServiceProviderRegistry;
import com.chat.bl.service.dao.UserServiceProvider;
import com.chat.bl.service.messaging.EndPoint;
import com.chat.bl.service.messaging.ResponseCode;
import com.chat.bl.service.messaging.ResponseWrapper;
import com.chat.bl.service.messaging.user.RegistrationRequest;
import com.chat.bl.service.messaging.user.UserRequest;
import com.chat.domain.User;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gdimitrova
 */
public class UserEndpoint implements EndPoint {

    private static final Logger LOGGER = Logger.getLogger(UserEndpoint.class.getName());

    private final UserServiceProvider provider;

    public UserEndpoint(ServiceProviderRegistry registry) {
        provider = registry.getUserServiceProvider();
    }

    public synchronized ResponseWrapper<User> login(UserRequest req) {
        try {
            User user = provider.login(req.getUsername(), req.getPassword());
            return new ResponseWrapper(ResponseCode.OK, user);
        } catch (MessageException ex) {
            if (provider.getTransaction() != null) {
                provider.rollback();
            }
            LOGGER.log(Level.SEVERE, ex.getMessage());
            return new ResponseWrapper(ResponseCode.ERROR, ex.getMessage());
        } catch (Throwable ex) {
            if (provider.getTransaction() != null) {
                provider.rollback();
            }
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            return new ResponseWrapper(ResponseCode.SERVER_ERROR, ex.getMessage());
        }
    }

    public synchronized ResponseWrapper<User> logout(UserRequest req) {
        try {
            User user = provider.logout(req.getUsername(), req.getPassword());
            return new ResponseWrapper(ResponseCode.OK, user);
        } catch (MessageException ex) {
            if (provider.getTransaction() != null) {
                provider.rollback();
            }
            LOGGER.log(Level.SEVERE, ex.getMessage());
            return new ResponseWrapper(ResponseCode.ERROR, ex.getMessage());
        } catch (Throwable ex) {
            if (provider.getTransaction() != null) {
                provider.rollback();
            }
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            return new ResponseWrapper(ResponseCode.SERVER_ERROR, ex.getMessage());
        }
    }

    public synchronized ResponseWrapper<User> register(RegistrationRequest req) {
        try {
            User user = provider.register(
                    req.getUsername(), req.getPassword(), req.getFirstname(),
                    req.getLastname(), req.getEmail(), req.getEmail(), req.getCity());
            return new ResponseWrapper(ResponseCode.OK, user);
        } catch (MessageException ex) {
            if (provider.getTransaction() != null) {
                provider.rollback();
            }
            LOGGER.log(Level.SEVERE, ex.getMessage());
            return new ResponseWrapper(ResponseCode.ERROR, ex.getMessage());
        } catch (Throwable ex) {
            if (provider.getTransaction() != null) {
                provider.rollback();
            }
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            return new ResponseWrapper(ResponseCode.SERVER_ERROR, ex.getMessage());
        }
    }

}

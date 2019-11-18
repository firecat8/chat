package com.chat.bl.service.dao;

import com.chat.dao.DaoRegistry;
import com.chat.domain.User;
import com.chat.domain.UserInfo;
import com.chat.domain.UserStatus;
import java.util.logging.Logger;
import javax.persistence.EntityManager;

/**
 *
 * @author gdimitrova
 */
public class UserServiceProvider extends AbstractServiceProvider {

    private final static Logger LOGGER = Logger.getLogger(UserServiceProvider.class.getName());

    public UserServiceProvider(EntityManager em, DaoRegistry registry) {
        super(em, registry);
    }

    public User login(String username, String password) {
        return updateStatus(username, password, UserStatus.ACTIVE);
    }

    public User logout(String username, String password) {
        return updateStatus(username, password, UserStatus.INACTIVE);
    }

    public User register(String username, String password, String firstname, String lastname, String email, String phone, String city) {
        beginTransaction();
        User user = registry.getUserDao().find(username);
        if (user != null) {
            throw new MessageException("Already exist user with username: " + username);
        }
        UserInfo userInfo = registry.getUserInfoDao().save(username, password, firstname, lastname, email, phone, city);
        user = userInfo.getUser();
        commit();
        return user;
    }

    private User updateStatus(String username, String password, UserStatus status) {
        beginTransaction();
        User user = registry.getUserDao().find(username);
        if (user == null) {
            throw new MessageException("Not exist user with username: " + username);
        }
        if (!user.getPassword().equals(password)) {
            throw new MessageException("Not valid password!");
        }
        registry.getUserDao().updateStatus(user, status);
        commit();
        return user;
    }

}

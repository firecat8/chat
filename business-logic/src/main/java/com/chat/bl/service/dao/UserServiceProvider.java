package com.chat.bl.service.dao;

import com.chat.bl.service.messaging.user.RegistrationVo;
import com.chat.dao.DaoRegistry;
import com.chat.domain.User;
import com.chat.domain.UserInfo;
import com.chat.domain.UserStatus;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;

/**
 *
 * @author gdimitrova
 */
public class UserServiceProvider extends AbstractServiceProvider {

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
        User user = null;
        try {
            beginTransaction();
            UserInfo userInfo = registry.getUserInfoDao().save(username, password, firstname, lastname, email, phone, city);
            user = userInfo.getUser();
            commit();

        } catch (Exception ex) {
            Logger.getLogger(UserServiceProvider.class.getName()).log(Level.SEVERE, null, ex);
            if (getTransaction() != null) {
                rollback();
            }
            throw ex;
        }
        return user;
    }

    private User updateStatus(String username, String password, UserStatus status) {
        User user = null;
        try {
            beginTransaction();
            user = registry.getUserDao().find(username);
            if (user == null) {
                throw new MessageException("Not exist user with username: " + username);
            }
            if (!user.getPassword().equals(password)) {
                throw new MessageException("Not valid password:!");
            }
            registry.getUserDao().updateStatus(user, status);
            commit();

        } catch (Exception ex) {
            Logger.getLogger(UserServiceProvider.class.getName()).log(Level.SEVERE, null, ex);
            if (getTransaction() != null) {
                rollback();
            }
            throw ex;
        }
        return user;
    }

}

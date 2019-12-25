package com.chat.dao;

import com.chat.domain.User;
import com.chat.domain.UserStatus;
import java.util.List;

/**
 *
 * @author gdimitrova
 */
public interface UserDao extends CrudDao<User> {

    public User find(String username);

    public List<User> findUsers(String username);

    public boolean updateStatus(User user, UserStatus status);
}

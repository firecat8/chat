package com.chat.persistence.dao;

import com.chat.dao.UserDao;
import com.chat.domain.User;
import com.chat.domain.UserStatus;
import com.chat.persistence.dto.UserDto;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;

/**
 *
 * @author gdimitrova
 */
public class UserDaoImpl extends AbstractDao<UserDto> implements UserDao {

    public UserDaoImpl(EntityManager em) {
        super(UserDto.class, em);
    }

    @Override
    public User find(String username) {
        return getResult(UserDto.USER_NAME, username);
    }

    @Override
    public boolean updateStatus(User user, UserStatus status) {
        user.setStatus(status);
        return super.update((UserDto) user, new HashMap<>()) == 1;
    }

    @Override
    protected Map<String, Object> loadProperties(UserDto newOne) {
        return new HashMap<>();
    }

}

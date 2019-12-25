package com.chat.persistence.dao;

import com.chat.dao.UserDao;
import com.chat.domain.User;
import com.chat.domain.UserStatus;
import com.chat.persistence.dto.UserDto;
import com.chat.persistence.exchanger.UserDtoExchanger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;

/**
 *
 * @author gdimitrova
 */
public class UserDaoImpl extends AbstractCrudDao<UserDto, User> implements UserDao {

    public UserDaoImpl(EntityManager em) {
        super(UserDto.class, em, UserDtoExchanger.INSTANCE);
    }

    @Override
    public User find(String username) {
        return getResult(UserDto.USER_NAME, username);
    }

    @Override
    public List<User> findUsers(String username) {
        return getResultsLikeExpr(UserDto.USER_NAME, username);
    }
    @Override
    public boolean updateStatus(User user, UserStatus status) {
        user.setStatus(status);
        UserDto dto = exchanger.exchange(user);
        return super.update(dto, loadProperties(dto)) == 1;
    }

    @Override
    protected Map<String, Object> loadProperties(UserDto newOne) {
        HashMap<String, Object> map = new HashMap<>();
        map.put(UserDto.USER_NAME, newOne.getUsername());
        map.put(UserDto.PASSWORD, newOne.getPassword());
        map.put(UserDto.STATUS, newOne.getStatus());
        return map;
    }


}

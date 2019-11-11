package com.chat.persistence.dao;

import com.chat.dao.UserInfoDao;
import com.chat.persistence.dto.UserInfoDto;
import java.util.Map;
import javax.persistence.EntityManager;

/**
 *
 * @author gdimitrova
 */
public class UserInfoDaoImpl extends AbstractCrudDao<UserInfoDto> implements UserInfoDao {

    public UserInfoDaoImpl(EntityManager em) {
        super(UserInfoDto.class, em);
    }

    @Override
    public UserInfoDto save(String username, String password, String firstname, String lastname, String email, String phone, String city) {
        UserInfoDto userInfoDto = new UserInfoDto(username, password, firstname, lastname, email, phone, city);
        super.save(userInfoDto);
        return userInfoDto;
    }

    @Override
    protected Map<String, Object> loadProperties(UserInfoDto newOne) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

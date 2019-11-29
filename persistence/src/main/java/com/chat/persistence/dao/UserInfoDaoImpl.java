package com.chat.persistence.dao;

import com.chat.dao.UserInfoDao;
import com.chat.domain.UserInfo;
import com.chat.persistence.dto.UserInfoDto;
import com.chat.persistence.exchanger.UserInfoDtoExchanger;
import java.util.Map;
import javax.persistence.EntityManager;

/**
 *
 * @author gdimitrova
 */
public class UserInfoDaoImpl extends AbstractCrudDao<UserInfoDto, UserInfo> implements UserInfoDao {

    public UserInfoDaoImpl(EntityManager em) {
        super(UserInfoDto.class, em, UserInfoDtoExchanger.INSTANCE);
    }

    @Override
    public UserInfo save(String username, String password, String firstname, String lastname, String email, String phone, String city) {
        return save(new UserInfoDto(username, password, firstname, lastname, email, phone, city));
    }

    @Override
    protected Map<String, Object> loadProperties(UserInfoDto newOne) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

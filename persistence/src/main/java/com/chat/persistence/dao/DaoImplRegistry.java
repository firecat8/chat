package com.chat.persistence.dao;

import com.chat.dao.DaoRegistry;
import com.chat.dao.UserDao;
import com.chat.dao.UserInfoDao;
import javax.persistence.EntityManager;

/**
 *
 * @author gdimitrova
 */
public class DaoImplRegistry implements DaoRegistry {

    private final UserDao userDao;

    private final UserInfoDao userInfoDao;

    public DaoImplRegistry(EntityManager em) {
        userDao = new UserDaoImpl(em);
        userInfoDao = new UserInfoDaoImpl(em);
    }

    @Override
    public UserDao getUserDao() {
        return userDao;
    }

    @Override
    public UserInfoDao getUserInfoDao() {
        return userInfoDao;
    }

}

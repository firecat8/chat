package com.chat.persistence.dao;

import com.chat.dao.ChatDao;
import com.chat.dao.ChatEventDao;
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

    private final ChatDao chatDao;

    private final ChatEventDao chatEventDao;

    public DaoImplRegistry(EntityManager em) {
        userDao = new UserDaoImpl(em);
        userInfoDao = new UserInfoDaoImpl(em);
        chatDao = new ChatDaoImpl(em);
        chatEventDao = new ChatEventDaoImpl(em);
    }

    @Override
    public UserDao getUserDao() {
        return userDao;
    }

    @Override
    public UserInfoDao getUserInfoDao() {
        return userInfoDao;
    }

    @Override
    public ChatDao getChatDao() {
        return chatDao;
    }

    @Override
    public ChatEventDao getChatEventDao() {
        return chatEventDao;
    }

}

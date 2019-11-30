package com.chat.persistence.dao;

import com.chat.dao.ChatDao;
import com.chat.dao.ChatEventDao;
import com.chat.dao.DaoRegistry;
import com.chat.dao.FriendRequestDao;
import com.chat.dao.UserDao;
import com.chat.dao.UserInfoDao;
import javax.persistence.EntityManager;

/**
 *
 * @author gdimitrova
 */
public class DaoImplRegistry implements DaoRegistry {

    private final EntityManager em;

    private final UserDao userDao;

    private final UserInfoDao userInfoDao;

    private final ChatDao chatDao;

    private final ChatEventDao chatEventDao;

    private final FriendRequestDao friendRequestDao;

    public DaoImplRegistry(EntityManager em) {
        this.em = em;
        userDao = new UserDaoImpl(em);
        userInfoDao = new UserInfoDaoImpl(em);
        chatDao = new ChatDaoImpl(em);
        chatEventDao = new ChatEventDaoImpl(em);
        friendRequestDao = new FriendRequestDaoImpl(em);
    }

    public DaoImplRegistry() {
        this(EntityManagerFactoryHolder.FACTORY.createEntityManager());
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

    @Override
    public FriendRequestDao getFriendRequestDao() {
        return friendRequestDao;
    }

    @Override
    public void beginTransaction() {
        if (!em.getTransaction().isActive()) {
            em.getTransaction().begin();
        }
    }

    @Override
    public void rollbackTransaction() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
    }

    @Override
    public void commitTransaction() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().commit();
        }
    }

}

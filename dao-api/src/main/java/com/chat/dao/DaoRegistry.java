package com.chat.dao;

/**
 *
 * @author gdimitrova
 */
public interface DaoRegistry {

    public UserDao getUserDao();

    public UserInfoDao getUserInfoDao();

    public ChatDao getChatDao();

    public ChatEventDao getChatEventDao();

    public FriendRequestDao getFriendRequestDao();

    public void beginTransaction();

    public void rollbackTransaction();

    public void commitTransaction();
}

package com.chat.dao;

/**
 *
 * @author gdimitrova
 */
public interface DaoRegistry{

    public UserDao getUserDao();

    public UserInfoDao getUserInfoDao();
}

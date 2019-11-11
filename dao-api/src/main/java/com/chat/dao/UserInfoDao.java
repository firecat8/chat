package com.chat.dao;

import com.chat.domain.UserInfo;

/**
 *
 * @author gdimitrova
 */
public interface UserInfoDao {

    public UserInfo save(String username, String password, String firstname, String lastname, String email, String phone, String city);
}

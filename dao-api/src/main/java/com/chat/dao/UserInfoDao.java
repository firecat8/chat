package com.chat.dao;

import com.chat.domain.User;
import com.chat.domain.UserInfo;

/**
 *
 * @author gdimitrova
 */
public interface UserInfoDao {

    public UserInfo save(User user, String firstname, String lastname, String email, String phone, String city);
}

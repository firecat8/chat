package com.chat.bl.service.messaging.user;

import com.chat.bl.service.messaging.Request;

/**
 *
 * @author gdimitrova
 */
public class UserRequest implements Request{

    String username;

    String password;

    public UserRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
    
}

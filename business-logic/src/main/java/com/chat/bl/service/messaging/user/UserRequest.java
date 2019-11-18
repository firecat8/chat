package com.chat.bl.service.messaging.user;

import com.chat.bl.service.messaging.AbstractRequest;

/**
 *
 * @author gdimitrova
 */
public class UserRequest extends AbstractRequest{

    private final String username;

    private final String password;

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

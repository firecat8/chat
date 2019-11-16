package com.chat.bl.service.messaging.user;

/**
 *
 * @author gdimitrova
 */
public class UserRequest {

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

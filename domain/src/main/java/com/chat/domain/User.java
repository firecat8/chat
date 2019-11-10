package com.chat.domain;

/**
 *
 * @author gdimitrova
 */
public interface User extends Entity {

    public String getUsername();

    public void setUsername(String username);

    public String getPassword();

    public void setPassword(String password);

    public UserStatus getStatus();

    public void setStatus(UserStatus status);
}

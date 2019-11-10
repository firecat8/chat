
package com.chat.persistence.dto;

import com.chat.domain.User;
import com.chat.domain.UserStatus;

/**
 *
 * @author gdimitrova
 */
public class UserDto  extends AbstractDto implements User {

    private String username;

    private String password;
    
    private UserStatus status;

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

   

}

package com.chat.persistence.dto;

import com.chat.domain.User;
import com.chat.domain.UserStatus;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

/**
 *
 * @author gdimitrova
 */
@Entity(name = "users")
@Table(name = "users")
public class UserDto extends AbstractDto implements User {

    public final static String USER_NAME_COLUMN = "user_name";

    public final static String USER_NAME = "username";

    public final static String PASSWORD = "password";

    public final static String STATUS = "status";

    @Column(name = USER_NAME_COLUMN, unique = true)
    private String username;

    @Column
    private String password;

    @Enumerated(EnumType.STRING)
    @Column
    private UserStatus status;

    public UserDto() {
    }

    public UserDto(String username, String password, UserStatus status) {
        this.username = username;
        this.password = password;
        this.status = status;
    }

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

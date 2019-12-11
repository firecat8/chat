package com.chat.persistence.dto;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author gdimitrova
 */
@Entity(name = "users_info")
@Table(name = "users_info")
public class UserInfoDto extends AbstractDto {

    public final static String FIRST_NAME_COLUMN = "first_name";

    public final static String LAST_NAME_COLUMN = "last_name";

    public final static String USER_ID = "user_id";

    @Column(name = FIRST_NAME_COLUMN, nullable = false)
    private String firstName;

    @Column(name = LAST_NAME_COLUMN, nullable = false)
    private String lastName;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = USER_ID)
    private UserDto user;

    public UserInfoDto() {
        //  hibernate
    }

    public UserInfoDto(UserDto user, String firstName, String lastName) {
        this.user = user;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public String getFirstname() {
        return firstName;
    }

    public void setFirstname(String firstName) {
        this.firstName = firstName;
    }

    public String getLastname() {
        return lastName;
    }

    public void setLastname(String lastName) {
        this.lastName = lastName;
    }

}

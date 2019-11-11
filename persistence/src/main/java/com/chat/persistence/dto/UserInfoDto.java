package com.chat.persistence.dto;

import com.chat.domain.User;
import com.chat.domain.UserInfo;
import com.chat.domain.UserStatus;
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
public class UserInfoDto extends AbstractDto implements UserInfo {

    public final static String FIRST_NAME_COLUMN = "first_name";

    public final static String LAST_NAME_COLUMN = "last_name";

    public final static String USER_ID = "user_id";

    @Column(name = FIRST_NAME_COLUMN)
    private String firstname;

    @Column(name = LAST_NAME_COLUMN)
    private String lastname;

    @Column
    private String email;

    @Column
    private String phone;

    @Column
    private String city;

    @OneToOne(cascade = CascadeType.ALL, targetEntity = UserDto.class)
    @JoinColumn(name = USER_ID)
    private User user;

    public UserInfoDto() {
      //  hibernate
    }

    public UserInfoDto(String username, String password, String firstname, String lastname, String email, String phone, String city) {
        this.user = new UserDto(username,password,UserStatus.INACTIVE);
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phone = phone;
        this.city = city;
    }
    

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String getFirstname() {
        return firstname;
    }

    @Override
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    @Override
    public String getLastname() {
        return lastname;
    }

    @Override
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getPhone() {
        return phone;
    }

    @Override
    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String getCity() {
        return city;
    }

    @Override
    public void setCity(String city) {
        this.city = city;
    }

}

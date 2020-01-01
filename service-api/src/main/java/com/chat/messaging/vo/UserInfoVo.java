package com.chat.messaging.vo;

/**
 *
 * @author gdimitrova
 */
public class UserInfoVo extends EntityVo {

    private String firstName;

    private String lastName;

    private UserVo user;

    public UserInfoVo(UserVo user, String firstName, String lastName) {
        this.user = user;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public UserVo getUser() {
        return user;
    }

    public void setUser(UserVo user) {
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

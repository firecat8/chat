package com.chat.domain;

/**
 *
 * @author gdimitrova
 */
public class UserInfo extends Entity {

    private String firstname;

    private String lastname;

    private String email;

    private String phone;

    private String city;

    private User user;

    public UserInfo() {
    }

    public UserInfo(String username, String password, String firstname, String lastname, String email, String phone, String city) {
        this.user = new User(username, password, UserStatus.INACTIVE);
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phone = phone;
        this.city = city;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

}

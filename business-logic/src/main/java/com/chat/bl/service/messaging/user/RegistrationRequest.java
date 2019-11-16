package com.chat.bl.service.messaging.user;

import com.chat.bl.service.messaging.Request;

/**
 *
 * @author gdimitrova
 */
public class RegistrationRequest implements Request {

    private final String username;

    private final String password;

    private final String firstname;

    private final String lastname;

    private final String email;

    private final String phone;

    private final String city;

    public RegistrationRequest(String username, String password, String firstname, String lastname, String email, String phone, String city) {
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phone = phone;
        this.city = city;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }


    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getCity() {
        return city;
    }

}

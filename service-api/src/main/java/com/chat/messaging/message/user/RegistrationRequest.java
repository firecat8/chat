package com.chat.messaging.message.user;

import com.chat.messaging.message.AbstractRequest;


/**
 *
 * @author gdimitrova
 */
public class RegistrationRequest extends AbstractRequest{

    private final String username;

    private final String password;

    private final String firstname;

    private final String lastname;

    public RegistrationRequest(String username, String password, String firstname, String lastname) {
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
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

}

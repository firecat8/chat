package com.chat.messaging.dto;

/**
 *
 * @author gdimitrova
 */
public class UserInfoMessageDto extends MessageDto {

    private String firstName;

    private String lastName;

    private UserMessageDto user;

    public UserInfoMessageDto(UserMessageDto user, String firstName, String lastName) {
        this.user = user;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public UserMessageDto getUser() {
        return user;
    }

    public void setUser(UserMessageDto user) {
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

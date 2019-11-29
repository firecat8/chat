package com.chat.messaging.dto;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author gdimitrova
 */
public class UserMessageDto extends MessageDto {

    private String username;

    private String password;

    private UserStatusMsgDto status;

    private final Set<UserMessageDto> friends = new HashSet<>();

    public UserMessageDto(String username, String password, UserStatusMsgDto status) {
        this.username = username;
        this.password = password;
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserStatusMsgDto getStatus() {
        return status;
    }

    public void setStatus(UserStatusMsgDto status) {
        this.status = status;
    }

    public Set<UserMessageDto> getFriends() {
        return friends;
    }

    public void addFriend(UserMessageDto friend) {
        friends.add(friend);
    }

    public void removeFriend(UserMessageDto friend) {
        friends.remove(friend);
    }

}

package com.chat.domain;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author gdimitrova
 */
public class User extends Entity {

    private String username;

    private String password;

    private UserStatus status;

    private Long statusTime;

    private final Set<User> friends = new HashSet<>();

    public User(String username, String password, UserStatus status, Long statusTime) {
        this.username = username;
        this.password = password;
        this.status = status;
        this.statusTime = statusTime;
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

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public Set<User> getFriends() {
        return friends;
    }

    public void addFriend(User friend) {
        friends.add(friend);
    }

    public void removeFriend(User friend) {
        friends.remove(friend);
    }

    public Long getStatusTime() {
        return statusTime;
    }

    public void setStatusTime(Long statusTime) {
        this.statusTime = statusTime;
    }
}

package com.chat.messaging.vo;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author gdimitrova
 */
public class UserVo extends EntityVo {

    private String username;

    private String password;

    private UserStatusVo status;

    private Long statusTime;

    private final Set<UserVo> friends = new HashSet<>();

    public UserVo(String username, String password, UserStatusVo status, Long statusTime) {
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

    public UserStatusVo getStatus() {
        return status;
    }

    public void setStatus(UserStatusVo status) {
        this.status = status;
    }

    public Long getStatusTime() {
        return statusTime;
    }

    public void setStatusTime(Long statusTime) {
        this.statusTime = statusTime;
    }

    public Set<UserVo> getFriends() {
        return friends;
    }

    public void addFriend(UserVo friend) {
        friends.add(friend);
    }

    public void removeFriend(UserVo friend) {
        friends.remove(friend);
    }

}

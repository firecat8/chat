package com.chat.persistence.dto;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

/**
 *
 * @author gdimitrova
 */
@Entity(name = "users")
@Table(name = "users")
public class UserDto extends AbstractDto {

    public final static String USER_NAME_COLUMN = "user_name";

    public final static String STATUS_TIME_COLUMN = "status_time";

    public final static String USER_NAME = "username";

    public final static String PASSWORD = "password";

    public final static String STATUS = "status";

    public final static String STATUS_TIME = "statusTime";

    @Column(name = USER_NAME_COLUMN, unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column
    private UserStatusDto status;

    @Column(name = STATUS_TIME_COLUMN, nullable = false)
    private Long statusTime;

    @ElementCollection
    @CollectionTable(name = "friends", joinColumns = @JoinColumn(name = "friend_id"))
    @Column(name = "USER_ID")
    protected Set<Long> friendsIdentifiers = new HashSet<>();

    public UserDto() {
        // Hibernate
    }

    public UserDto(String username, String password, UserStatusDto status, Long statusTime) {
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

    public UserStatusDto getStatus() {
        return status;
    }

    public void setStatus(UserStatusDto status) {
        this.status = status;
    }

    public Set<Long> getFriends() {
        return friendsIdentifiers;
    }

    public void addFriend(Long friend) {
        friendsIdentifiers.add(friend);
    }

    public void removeFriend(Long friend) {
        friendsIdentifiers.remove(friend);
    }

    public Long getStatusTime() {
        return statusTime;
    }

    public void setStatusTime(Long statusTime) {
        this.statusTime = statusTime;
    }

}

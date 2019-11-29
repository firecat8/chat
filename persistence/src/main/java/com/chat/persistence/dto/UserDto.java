package com.chat.persistence.dto;

import com.chat.persistence.dto.UserStatusDto;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 *
 * @author gdimitrova
 */
@Entity(name = "users")
@Table(name = "users")
public class UserDto extends AbstractDto {

    public final static String USER_NAME_COLUMN = "user_name";

    public final static String USER_NAME = "username";

    public final static String PASSWORD = "password";

    public final static String STATUS = "status";

    @Column(name = USER_NAME_COLUMN, unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column
    private UserStatusDto status;

    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinTable(name = "friends",
            joinColumns = {
                @JoinColumn(name = "user_id")},
            inverseJoinColumns = {
                @JoinColumn(name = "friend_id")})
    private final Set<UserDto> friends = new HashSet<>();

    @ManyToMany(mappedBy = "friends", fetch = FetchType.EAGER)
    private final Set<UserDto> users = new HashSet<>();

    public UserDto() {
        // Hibernate
    }

    public UserDto(String username, String password, UserStatusDto status) {
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

    public UserStatusDto getStatus() {
        return status;
    }

    public void setStatus(UserStatusDto status) {
        this.status = status;
    }

    public Set<UserDto> getFriends() {
        return friends;
    }

    public void addFriend(UserDto friend) {
        friends.add(friend);
    }

    public void removeFriend(UserDto friend) {
        friends.remove(friend);
    }

}

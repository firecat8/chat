package com.chat.persistence.dto;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author gdimitrova
 */
@Entity(name = "participants")
@Table(name = "participants")
public class ParticipantDto extends AbstractDto {

    public final static String USER = "user";

    public final static String USER_ID = "user_id";

    public final static String CHAT_COLUMN = "chat_id";

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = USER_ID)
    private UserDto user;

    @Enumerated(EnumType.STRING)
    @Column
    private ChatUserDto userType;

    @ManyToOne
    @JoinColumn(name = CHAT_COLUMN)
    private ChatDto chat;

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public ChatUserDto getUserType() {
        return userType;
    }

    public void setUserType(ChatUserDto userType) {
        this.userType = userType;
    }

    public ChatDto getChat() {
        return chat;
    }

    public void setChat(ChatDto chat) {
        this.chat = chat;
    }

}

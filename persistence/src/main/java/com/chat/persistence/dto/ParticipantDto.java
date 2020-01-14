package com.chat.persistence.dto;

import java.util.Objects;
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

    @OneToOne()
    @JoinColumn(name = USER_ID)
    private UserDto user;

    @Enumerated(EnumType.STRING)
    @Column
    private ChatUserDto userType;

    @ManyToOne
    @JoinColumn(name = CHAT_COLUMN)
    private ChatDto chat;

    public ParticipantDto() {
        //Hibernate
    }

    public ParticipantDto(UserDto user) {
        this.user = user;
        this.userType = ChatUserDto.OWNER;
    }

    public ParticipantDto(UserDto user, ChatUserDto userType) {
        this.user = user;
        this.userType = userType;
    }

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

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.user);
        hash = 67 * hash + Objects.hashCode(this.userType);
        hash = 67 * hash + Objects.hashCode(this.chat);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ParticipantDto other = (ParticipantDto) obj;
        if (!Objects.equals(this.user, other.user)) {
            return false;
        }
        if (this.userType != other.userType) {
            return false;
        }
        if (!Objects.equals(this.getId(), other.getId())) {
            return false;
        }
        return Objects.equals(this.chat, other.chat);
    }

}

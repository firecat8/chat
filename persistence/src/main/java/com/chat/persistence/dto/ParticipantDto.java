package com.chat.persistence.dto;

import com.chat.domain.Chat;
import com.chat.domain.ChatUser;
import com.chat.domain.Participant;
import com.chat.domain.User;
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
public class ParticipantDto extends AbstractDto implements Participant {

    public final static String USER = "user";

    public final static String USER_ID = "user_id";

    public final static String CHAT_COLUMN = "chat_id";

    @OneToOne(cascade = CascadeType.ALL, targetEntity = UserDto.class)
    @JoinColumn(name = USER_ID)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column
    private ChatUser userType;

    @ManyToOne(targetEntity = ChatDto.class)
    @JoinColumn(name = CHAT_COLUMN)
    private Chat chat;

    @Override
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public ChatUser getUserType() {
        return userType;
    }

    public void setUserType(ChatUser userType) {
        this.userType = userType;
    }

    @Override
    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

}

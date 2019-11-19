package com.chat.persistence.dto;

import com.chat.domain.Chat;
import com.chat.domain.ChatEvent;
import com.chat.domain.User;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

/**
 *
 * @author gdimitrova
 */

@MappedSuperclass
public abstract class ChatEventDto extends AbstractDto implements ChatEvent {

    public final static String EVENT_TIME_COLUMN = "event_time";

    public final static String EVENT_TIME = "eventTime";

    public final static String SENDER = "sender";

    public final static String CHAT = "chat";

    @Column(name = EVENT_TIME_COLUMN)
    private Long eventTime;

    @ManyToOne(targetEntity = UserDto.class)
    @Column
    private User sender;

    @ManyToOne(targetEntity = ChatDto.class)
    @Column
    private Chat chat;

    public ChatEventDto() {
        //Hibernate
    }

    public ChatEventDto(Long eventTime, User sender, Chat chat) {
        this.eventTime = eventTime;
        this.sender = sender;
        this.chat = chat;
    }

    @Override
    public Long getEventTime() {
        return eventTime;
    }

    public void setEventTime(Long sentTime) {
        this.eventTime = sentTime;
    }

    @Override
    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    @Override
    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }
}

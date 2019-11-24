package com.chat.persistence.dto;

import com.chat.domain.Chat;
import com.chat.domain.ChatEvent;
import com.chat.domain.ChatEventType;
import com.chat.domain.User;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author gdimitrova
 */
@Entity(name = "chat_events")
@Table(name = "chat_events")
public class ChatEventDto extends AbstractDto implements ChatEvent {

    public final static String EVENT_TIME_COLUMN = "event_time";

    public final static String CHAT_EVENT_TYPE_COLUMN = "event_type";

    public final static String EVENT_TIME = "eventTime";

    public final static String SENDER = "sender";

    public final static String CHAT = "chat";

    public final static String MESSAGE = "message";

    public final static String CHAT_EVENT_TYPE = "chatEventType";

    @Column()
    private String message;

    @Column(name = CHAT_EVENT_TYPE_COLUMN)
    private ChatEventType chatEventType;

    @Column(name = EVENT_TIME_COLUMN)
    private Long eventTime;

    @ManyToOne(targetEntity = UserDto.class)
    private User sender;

    @ManyToOne(targetEntity = ChatDto.class)
    private Chat chat;

    public ChatEventDto() {
        //Hibernate
    }

    public ChatEventDto(String message, ChatEventType chatEventType, Long eventTime, User sender, Chat chat) {
        this.message = message;
        this.chatEventType = chatEventType;
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

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public ChatEventType getChatEventType() {
        return chatEventType;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setChatEventType(ChatEventType chatEventType) {
        this.chatEventType = chatEventType;
    }

}

package com.chat.persistence.dto;

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
public class ChatEventDto extends AbstractDto {

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
    private ChatEventTypeDto chatEventType;

    @Column(name = EVENT_TIME_COLUMN)
    private Long eventTime;

    @ManyToOne(targetEntity = UserDto.class)
    private UserDto sender;

    @ManyToOne(targetEntity = ChatDto.class)
    private ChatDto chat;

    public ChatEventDto() {
        //Hibernate
    }

    public ChatEventDto(String message, ChatEventTypeDto chatEventType, Long eventTime, UserDto sender, ChatDto chat) {
        this.message = message;
        this.chatEventType = chatEventType;
        this.eventTime = eventTime;
        this.sender = sender;
        this.chat = chat;
    }

    public Long getEventTime() {
        return eventTime;
    }

    public void setEventTime(Long sentTime) {
        this.eventTime = sentTime;
    }

    public UserDto getSender() {
        return sender;
    }

    public void setSender(UserDto sender) {
        this.sender = sender;
    }

    public ChatDto getChat() {
        return chat;
    }

    public void setChat(ChatDto chat) {
        this.chat = chat;
    }

    public String getMessage() {
        return message;
    }

    public ChatEventTypeDto getChatEventType() {
        return chatEventType;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setChatEventType(ChatEventTypeDto chatEventType) {
        this.chatEventType = chatEventType;
    }

    public String getStorageFileName() {
        return getId() + "_" + getMessage();
    }

}

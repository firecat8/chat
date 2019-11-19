package com.chat.persistence.dto;

import com.chat.domain.Chat;
import com.chat.domain.ChatMessage;
import com.chat.domain.User;
import javax.persistence.Column;
import javax.persistence.ManyToOne;

/**
 *
 * @author gdimitrova
 */
public class ChatMessageDto extends AbstractDto implements ChatMessage {

    public final static String SENT_TIME_COLUMN = "sent_time";

    public final static String MESSAGE = "message";

    public final static String SENT_TIME = "sentTime";

    public final static String SENDER = "sender";

    public final static String CHAT = "chat";

    @Column()
    private String message;

    @Column(name = SENT_TIME_COLUMN)
    private Long sentTime;

    @ManyToOne(targetEntity = UserDto.class)
    @Column
    private User sender;

    @ManyToOne(targetEntity = ChatDto.class)
    @Column
    private Chat chat;

    public ChatMessageDto() {
        //Hibernate
    }

    public ChatMessageDto(String message, Long sentTime, User sender, Chat chat) {
        this.message = message;
        this.sentTime = sentTime;
        this.sender = sender;
        this.chat = chat;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getSentTime() {
        return sentTime;
    }

    public void setSentTime(Long sentTime) {
        this.sentTime = sentTime;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

}

package com.chat.persistence.dto;

import com.chat.domain.Chat;
import com.chat.domain.ChatMessage;
import com.chat.domain.User;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author gdimitrova
 */
@Entity(name = "chat_messages")
@Table(name = "chat_messages")
public class ChatMessageDto extends ChatEventDto implements ChatMessage {

    public final static String MESSAGE = "message";

    @Column()
    private String message;

    public ChatMessageDto() {
        //Hibernate
    }

    public ChatMessageDto(String message, Long eventTime, User sender, Chat chat) {
        super(eventTime, sender, chat);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}

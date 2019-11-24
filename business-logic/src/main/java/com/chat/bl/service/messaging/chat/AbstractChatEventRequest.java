package com.chat.bl.service.messaging.chat;

import com.chat.bl.service.messaging.AbstractRequest;
import com.chat.domain.Chat;
import com.chat.domain.ChatEventType;
import com.chat.domain.User;
import java.util.Calendar;

/**
 *
 * @author gdimitrova
 */
public abstract class AbstractChatEventRequest extends AbstractRequest {

    private final String message;


    private final Long eventTime;

    private final User sender;

    private final Chat chat;

    protected AbstractChatEventRequest(String message, User sender, Chat chat) {
        this.message = message;
        this.sender = sender;
        this.chat = chat;
        this.eventTime = Calendar.getInstance().getTimeInMillis();
    }

    public String getMessage() {
        return message;
    }

    public Long getEventTime() {
        return eventTime;
    }

    public User getSender() {
        return sender;
    }

    public Chat getChat() {
        return chat;
    }

}

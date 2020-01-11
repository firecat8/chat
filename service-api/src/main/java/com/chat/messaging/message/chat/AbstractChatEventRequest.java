package com.chat.messaging.message.chat;

import com.chat.messaging.message.AbstractRequest;
import java.util.Calendar;

/**
 *
 * @author gdimitrova
 */
public abstract class AbstractChatEventRequest extends AbstractRequest {

    private final String message;

    private final Long eventTime;

    private final Long senderId ;

    private final Long chatId ;

    protected AbstractChatEventRequest(String message, Long senderId , Long chatId ) {
        this.message = message;
        this.senderId  = senderId ;
        this.chatId  = chatId ;
        this.eventTime = Calendar.getInstance().getTimeInMillis();
    }

    public String getMessage() {
        return message;
    }

    public Long getEventTime() {
        return eventTime;
    }

    public Long getSender() {
        return senderId ;
    }

    public Long getChat() {
        return chatId ;
    }

}

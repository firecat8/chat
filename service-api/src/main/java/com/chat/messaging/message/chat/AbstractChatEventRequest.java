package com.chat.messaging.message.chat;

import com.chat.messaging.dto.ChatMessageDto;
import com.chat.messaging.dto.UserMessageDto;
import com.chat.messaging.message.AbstractRequest;
import java.util.Calendar;

/**
 *
 * @author gdimitrova
 */
public abstract class AbstractChatEventRequest extends AbstractRequest {

    private final String message;

    private final Long eventTime;

    private final UserMessageDto sender;

    private final ChatMessageDto chat;

    protected AbstractChatEventRequest(String message, UserMessageDto sender, ChatMessageDto chat) {
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

    public UserMessageDto getSender() {
        return sender;
    }

    public ChatMessageDto getChat() {
        return chat;
    }

}

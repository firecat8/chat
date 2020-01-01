package com.chat.messaging.message.chat;

import com.chat.messaging.vo.ChatVo;
import com.chat.messaging.vo.UserVo;
import com.chat.messaging.message.AbstractRequest;
import java.util.Calendar;

/**
 *
 * @author gdimitrova
 */
public abstract class AbstractChatEventRequest extends AbstractRequest {

    private final String message;

    private final Long eventTime;

    private final UserVo sender;

    private final ChatVo chat;

    protected AbstractChatEventRequest(String message, UserVo sender, ChatVo chat) {
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

    public UserVo getSender() {
        return sender;
    }

    public ChatVo getChat() {
        return chat;
    }

}

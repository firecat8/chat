package com.chat.messaging.message.chat;

import com.chat.messaging.vo.ChatEventVo;
import com.chat.messaging.message.AbstractResponse;

/**
 *
 * @author gdimitrova
 */
public class ChatEventResponse extends AbstractResponse{
    private final ChatEventVo chatEvent;

    public ChatEventResponse(ChatEventVo chatEvent) {
        this.chatEvent = chatEvent;
    }

    public ChatEventVo getChatEvent() {
        return chatEvent;
    }
    
}

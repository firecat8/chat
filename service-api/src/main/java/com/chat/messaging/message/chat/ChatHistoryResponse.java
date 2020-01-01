package com.chat.messaging.message.chat;

import com.chat.messaging.vo.ChatEventVo;
import com.chat.messaging.message.AbstractResponse;
import java.util.List;

/**
 *
 * @author gdimitrova
 */
public class ChatHistoryResponse extends AbstractResponse{
     private final List<ChatEventVo> history;

    public ChatHistoryResponse(List<ChatEventVo> history) {
        this.history = history;
    }

    public List<ChatEventVo> getHistory() {
        return history;
    }

    
}

package com.chat.messaging.message.chat;

import com.chat.messaging.message.AbstractRequest;
import com.chat.messaging.vo.ChatVo;

/**
 *
 * @author gdimitrova
 */
public class LoadHistoryRequest extends AbstractRequest {

    private final ChatVo chat;

    public LoadHistoryRequest(ChatVo chat) {
        this.chat = chat;
    }

    public ChatVo getChat() {
        return chat;
    }

}

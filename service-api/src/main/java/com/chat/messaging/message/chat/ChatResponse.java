package com.chat.messaging.message.chat;

import com.chat.messaging.vo.ChatVo;
import com.chat.messaging.message.AbstractResponse;

/**
 *
 * @author gdimitrova
 */
public class ChatResponse extends AbstractResponse {

    private final ChatVo chat;

    public ChatResponse(ChatVo chat) {
        this.chat = chat;
    }

    public ChatVo getChat() {
        return chat;
    }

}

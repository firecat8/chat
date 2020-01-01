package com.chat.messaging.message.chat;

import com.chat.messaging.vo.ChatVo;
import com.chat.messaging.vo.UserVo;

/**
 *
 * @author gdimitrova
 */
public class SendMessageRequest extends AbstractChatEventRequest {

    public SendMessageRequest(String message, UserVo sender, ChatVo chat) {
        super(message, sender, chat);
    }

}

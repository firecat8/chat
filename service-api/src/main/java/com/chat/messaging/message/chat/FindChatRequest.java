package com.chat.messaging.message.chat;

import com.chat.messaging.message.AbstractRequest;

/**
 *
 * @author gdimitrova
 */
public class FindChatRequest extends AbstractRequest {

    private final String chatName;

    public FindChatRequest(String chatName) {
        this.chatName = chatName;
    }

    public String getChatNmae() {
        return chatName;
    }

}

package com.chat.messaging.message.chat;

import com.chat.messaging.message.AbstractRequest;

/**
 *
 * @author gdimitrova
 */
public class CreateChatRequest extends AbstractRequest {

    private final String name;

    public CreateChatRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

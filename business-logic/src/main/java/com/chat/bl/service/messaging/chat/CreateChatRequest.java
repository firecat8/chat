package com.chat.bl.service.messaging.chat;

import com.chat.bl.service.messaging.AbstractRequest;
import com.chat.domain.ChatType;

/**
 *
 * @author gdimitrova
 */
public class CreateChatRequest extends AbstractRequest {

    private final String name;

    private final ChatType type;

    public CreateChatRequest(String name, ChatType type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public ChatType getType() {
        return type;
    }
    
}

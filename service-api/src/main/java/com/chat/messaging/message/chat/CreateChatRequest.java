package com.chat.messaging.message.chat;

import com.chat.messaging.message.AbstractRequest;
import com.chat.messaging.dto.ChatTypeMsgDto;

/**
 *
 * @author gdimitrova
 */
public class CreateChatRequest extends AbstractRequest {

    private final String name;

    private final ChatTypeMsgDto type;

    public CreateChatRequest(String name, ChatTypeMsgDto type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public ChatTypeMsgDto getType() {
        return type;
    }
    
}

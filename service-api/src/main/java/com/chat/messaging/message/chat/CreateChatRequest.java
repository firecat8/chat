package com.chat.messaging.message.chat;

import com.chat.messaging.vo.UserVo;
import com.chat.messaging.message.AbstractRequest;

/**
 *
 * @author gdimitrova
 */
public class CreateChatRequest extends AbstractRequest {

    private final String name;

    private final UserVo owner;

    public CreateChatRequest(String name, UserVo owner) {
        this.name = name;
        this.owner = owner;
    }

    public UserVo getOwner() {
        return owner;
    }

    public String getName() {
        return name;
    }
}

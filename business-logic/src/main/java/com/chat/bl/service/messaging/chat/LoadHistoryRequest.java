package com.chat.bl.service.messaging.chat;

import com.chat.bl.service.messaging.AbstractRequest;
import com.chat.domain.Chat;

/**
 *
 * @author gdimitrova
 */
public class LoadHistoryRequest extends AbstractRequest {

    private final Chat chat;

    public LoadHistoryRequest(Chat chat) {
        this.chat = chat;
    }

    public Chat getChat() {
        return chat;
    }

}

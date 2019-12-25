package com.chat.messaging.message.chat;

import com.chat.messaging.dto.ChatMessageDto;
import com.chat.messaging.message.AbstractCollectionResponse;
import java.util.List;

/**
 *
 * @author gdimitrova
 */
public class ChatsResponse extends AbstractCollectionResponse<ChatMessageDto>{
    
    public ChatsResponse(List<ChatMessageDto> entities) {
        super(entities);
    }
    
}

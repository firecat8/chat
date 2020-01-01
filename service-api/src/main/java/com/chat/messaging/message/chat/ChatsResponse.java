package com.chat.messaging.message.chat;

import com.chat.messaging.vo.ChatVo;
import com.chat.messaging.message.AbstractCollectionResponse;
import java.util.List;

/**
 *
 * @author gdimitrova
 */
public class ChatsResponse extends AbstractCollectionResponse<ChatVo>{
    
    public ChatsResponse(List<ChatVo> entities) {
        super(entities);
    }
    
}

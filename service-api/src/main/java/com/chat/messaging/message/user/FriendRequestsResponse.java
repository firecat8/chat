package com.chat.messaging.message.user;

import com.chat.messaging.dto.FriendRequestMessageDto;
import com.chat.messaging.message.AbstractCollectionResponse;
import java.util.List;

/**
 *
 * @author gdimitrova
 */
public class FriendRequestsResponse extends AbstractCollectionResponse<FriendRequestMessageDto> {

    public FriendRequestsResponse(List<FriendRequestMessageDto> entities) {
        super(entities);
    }


}

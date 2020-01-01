package com.chat.messaging.message.user;

import com.chat.messaging.vo.FriendRequestVo;
import com.chat.messaging.message.AbstractCollectionResponse;
import java.util.List;

/**
 *
 * @author gdimitrova
 */
public class FriendRequestsResponse extends AbstractCollectionResponse<FriendRequestVo> {

    public FriendRequestsResponse(List<FriendRequestVo> entities) {
        super(entities);
    }


}

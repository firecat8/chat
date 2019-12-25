package com.chat.messaging.message.user;

import com.chat.messaging.dto.UserMessageDto;
import com.chat.messaging.message.AbstractCollectionResponse;
import java.util.List;

/**
 *
 * @author gdimitrova
 */
public class UsersResponse extends AbstractCollectionResponse<UserMessageDto> {

    public UsersResponse(List<UserMessageDto> users) {
        super(users);
    }

}

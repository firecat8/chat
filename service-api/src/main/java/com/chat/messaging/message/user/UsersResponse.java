package com.chat.messaging.message.user;

import com.chat.messaging.vo.UserVo;
import com.chat.messaging.message.AbstractCollectionResponse;
import java.util.List;

/**
 *
 * @author gdimitrova
 */
public class UsersResponse extends AbstractCollectionResponse<UserVo> {

    public UsersResponse(List<UserVo> users) {
        super(users);
    }

}

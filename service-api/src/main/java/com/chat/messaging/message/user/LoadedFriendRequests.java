package com.chat.messaging.message.user;

import com.chat.messaging.dto.FriendRequestMessageDto;
import com.chat.messaging.message.AbstractResponse;
import java.util.List;

/**
 *
 * @author gdimitrova
 */
public class LoadedFriendRequests extends AbstractResponse {

    private final List<FriendRequestMessageDto> requests;

    public LoadedFriendRequests(List<FriendRequestMessageDto> requests) {
        this.requests = requests;
    }

    public List<FriendRequestMessageDto> getRequests() {
        return requests;
    }

}

package com.chat.messaging.message.chat;

import com.chat.messaging.message.AbstractRequest;

/**
 *
 * @author gdimitrova
 */
public class AddFriendRequest extends AbstractRequest{

    private final Long chatId;

    private final Long userId;

    private final Long friendId;

    public AddFriendRequest(Long chatId, Long userId, Long friendId) {
        this.chatId = chatId;
        this.userId = userId;
        this.friendId = friendId;
    }

    public Long getChatId() {
        return chatId;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getFriendId() {
        return friendId;
    }
    
}

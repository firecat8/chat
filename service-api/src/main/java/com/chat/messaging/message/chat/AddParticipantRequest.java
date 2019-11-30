package com.chat.messaging.message.chat;

/**
 *
 * @author gdimitrova
 */
public class AddParticipantRequest {

    private final Long chatId;

    private final Long userId;

    private final Long friendId;

    public AddParticipantRequest(Long chatId, Long userId, Long friendId) {
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

package com.chat.persistence.dto;

import com.chat.domain.ConversationType;
import com.chat.domain.User;

/**
 *
 * @author gdimitrova
 */
public class PrivateChat  extends ConversationDto{

    private User user1;

    private User user2;

    public User getUser1() {
        return user1;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    public User getUser2() {
        return user2;
    }

    public void setUser2(User user2) {
        this.user2 = user2;
    }

    @Override
    public ConversationType getChatType() {
        return ConversationType.PRIVATE;
    }
    
}

package com.chat.persistence.dto;

import com.chat.domain.ChatUser;
import com.chat.domain.User;

/**
 *
 * @author gdimitrova
 */
public class ParticipantDto extends AbstractDto{

    private User user;

    private ChatUser userType;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ChatUser getUserType() {
        return userType;
    }

    public void setUserType(ChatUser userType) {
        this.userType = userType;
    }
    
}

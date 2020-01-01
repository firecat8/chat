package com.chat.domain;

/**
 *
 * @author gdimitrova
 */
public class Participant extends Entity {

    private User user;

    private ChatUser userType;

    private Chat chat;

    public Participant() {
    }

    public Participant(User user, ChatUser userType) {
        this.user = user;
        this.userType = userType;
    }
    public Participant(User user, ChatUser userType, Chat chat) {
        this.user = user;
        this.userType = userType;
        this.chat = chat;
    }

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

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }
}

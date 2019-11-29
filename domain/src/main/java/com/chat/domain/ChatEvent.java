package com.chat.domain;

/**
 *
 * @author gdimitrova
 */
public class ChatEvent extends Entity{

    private String message;

    private ChatEventType chatEventType;

    private Long eventTime;

    private User sender;

    private Chat chat;

    public ChatEvent(String message, ChatEventType chatEventType, Long eventTime, User sender, Chat chat) {
        this.message = message;
        this.chatEventType = chatEventType;
        this.eventTime = eventTime;
        this.sender = sender;
        this.chat = chat;
    }

    public Long getEventTime() {
        return eventTime;
    }

    public void setEventTime(Long sentTime) {
        this.eventTime = sentTime;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public String getMessage() {
        return message;
    }

    public ChatEventType getChatEventType() {
        return chatEventType;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setChatEventType(ChatEventType chatEventType) {
        this.chatEventType = chatEventType;
    }

    public String getStorageFileName() {
        return getId() + "_" + getMessage();
    }

}

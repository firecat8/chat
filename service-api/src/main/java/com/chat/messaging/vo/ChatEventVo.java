package com.chat.messaging.vo;

/**
 *
 * @author gdimitrova
 */
public class ChatEventVo extends EntityVo {

    private String message;

    private ChatEventTypeVo chatEventType;

    private Long eventTime;

    private UserVo sender;

    private ChatVo chat;

    public ChatEventVo(String message, ChatEventTypeVo chatEventType, Long eventTime, UserVo sender, ChatVo chat) {
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

    public UserVo getSender() {
        return sender;
    }

    public void setSender(UserVo sender) {
        this.sender = sender;
    }

    public ChatVo getChat() {
        return chat;
    }

    public void setChat(ChatVo chat) {
        this.chat = chat;
    }

    public String getMessage() {
        return message;
    }

    public ChatEventTypeVo getChatEventType() {
        return chatEventType;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setChatEventType(ChatEventTypeVo chatEventType) {
        this.chatEventType = chatEventType;
    }

    public String getStorageFileName() {
        return getId() + "_" + getMessage();
    }
}

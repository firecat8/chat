package com.chat.messaging.dto;

/**
 *
 * @author gdimitrova
 */
public class ChatEventMessageDto extends MessageDto {

    private String message;

    private ChatEventTypeMsgDto chatEventType;

    private Long eventTime;

    private UserMessageDto sender;

    private ChatMessageDto chat;

    public ChatEventMessageDto(String message, ChatEventTypeMsgDto chatEventType, Long eventTime, UserMessageDto sender, ChatMessageDto chat) {
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

    public UserMessageDto getSender() {
        return sender;
    }

    public void setSender(UserMessageDto sender) {
        this.sender = sender;
    }

    public ChatMessageDto getChat() {
        return chat;
    }

    public void setChat(ChatMessageDto chat) {
        this.chat = chat;
    }

    public String getMessage() {
        return message;
    }

    public ChatEventTypeMsgDto getChatEventType() {
        return chatEventType;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setChatEventType(ChatEventTypeMsgDto chatEventType) {
        this.chatEventType = chatEventType;
    }

    public String getStorageFileName() {
        return getId() + "_" + getMessage();
    }
}

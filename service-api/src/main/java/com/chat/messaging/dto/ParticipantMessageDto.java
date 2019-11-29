package com.chat.messaging.dto;

/**
 *
 * @author gdimitrova
 */
public class ParticipantMessageDto extends MessageDto {

    private UserMessageDto user;

    private ChatUserMsgDto userType;

    private ChatMessageDto chat;

    public UserMessageDto getUser() {
        return user;
    }

    public void setUser(UserMessageDto user) {
        this.user = user;
    }

    public ChatUserMsgDto getUserType() {
        return userType;
    }

    public void setUserType(ChatUserMsgDto userType) {
        this.userType = userType;
    }

    public ChatMessageDto getChat() {
        return chat;
    }

    public void setChat(ChatMessageDto chat) {
        this.chat = chat;
    }

}

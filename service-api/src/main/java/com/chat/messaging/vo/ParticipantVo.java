package com.chat.messaging.vo;

/**
 *
 * @author gdimitrova
 */
public class ParticipantVo extends EntityVo {

    private UserVo user;

    private ChatUserVo userType;

    private ChatVo chat;

    public ParticipantVo(UserVo user, ChatUserVo userType) {
        this.user = user;
        this.userType = userType;
    }

    public UserVo getUser() {
        return user;
    }

    public void setUser(UserVo user) {
        this.user = user;
    }

    public ChatUserVo getUserType() {
        return userType;
    }

    public void setUserType(ChatUserVo userType) {
        this.userType = userType;
    }

    public ChatVo getChat() {
        return chat;
    }

    public void setChat(ChatVo chat) {
        this.chat = chat;
    }

}

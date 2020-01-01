package com.chat.messaging.vo;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author gdimitrova
 */
public class ChatVo extends EntityVo {

    private String name;

    private ChatTypeVo type;

    private Set<ParticipantVo> participants = new HashSet<>();

    public ChatVo(String name, ChatTypeVo type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ChatTypeVo getChatType() {
        return type;
    }

    public Set<ParticipantVo> getParticipants() {
        return participants;
    }

    public void addFriend(ParticipantVo participant) {
        this.participants.add(participant);
    }

    public void removeParticipant(ParticipantVo participant) {
        this.participants.remove(participant);
    }

}

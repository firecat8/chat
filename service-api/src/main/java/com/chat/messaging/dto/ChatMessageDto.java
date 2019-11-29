package com.chat.messaging.dto;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author gdimitrova
 */
public class ChatMessageDto extends MessageDto {

    private String name;

    private ChatTypeMsgDto type;

    private Set<ParticipantMessageDto> participants = new HashSet<>();

    public ChatMessageDto(String name, ChatTypeMsgDto type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ChatTypeMsgDto getChatType() {
        return type;
    }

    public Set<ParticipantMessageDto> getParticipants() {
        return participants;
    }

    public void addParticipant(ParticipantMessageDto participant) {
        this.participants.add(participant);
    }

    public void removeParticipant(ParticipantMessageDto participant) {
        this.participants.remove(participant);
    }

}

package com.chat.domain;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author gdimitrova
 */
public class Chat extends Entity {

    private String name;

    private ChatType type;

    private final Set<Participant> participants = new HashSet<>();

    public Chat(String name, ChatType type) {
        this.name = name;
        this.type = type;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ChatType getChatType() {
        return type;
    }

    public Set<Participant> getParticipants() {
        return participants;
    }

    public void addParticipant(Participant participant) {
        this.participants.add(participant);
    }
    public void addParticipants(Set<Participant> participants) {
        this.participants.addAll(participants);
    }

    public void removeParticipant(Participant participant) {
        this.participants.remove(participant);
    }
}

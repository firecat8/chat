package com.chat.persistence.dto;

import com.chat.domain.ConversationType;
import com.chat.domain.User;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author gdimitrova
 */
public class GroupChat extends ConversationDto {

    private User owner;

    private Set<User> participants = new HashSet<>();

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Set<User> getParticipants() {
        return participants;
    }

    public void setParticipants(Set<User> participants) {
        this.participants = participants;
    }

    public void addParticipant(User user) {
        participants.add(user);
    }

    public void removeParticipant(User user) {
        participants.remove(user);
    }

    @Override
    public ConversationType getChatType() {
        return ConversationType.GROUP;
    }

}

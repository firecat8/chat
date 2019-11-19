package com.chat.domain;

import java.util.Set;

/**
 *
 * @author gdimitrova
 */
public interface Chat extends Entity {

    public String getName();

    public void setName(String name);

    public ChatType getChatType();

    public Set<Participant> getParticipants();

    public void addParticipant(Participant participant);

    public void removeParticipant(Participant participant);
}

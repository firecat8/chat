package com.chat.persistence.dto;

import com.chat.domain.Chat;
import com.chat.domain.ChatType;
import com.chat.domain.Participant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author gdimitrova
 */
@Entity(name = "chats")
@Table(name = "chats")
public class ChatDto extends AbstractDto implements Chat {

    public final static String NAME = "name";

    public final static String TYPE = "type";

    @Column
    private String name;

    @Enumerated(EnumType.STRING)
    @Column
    private ChatType type;

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, targetEntity = ParticipantDto.class, mappedBy = "chat")
    private Set<Participant> participants = new HashSet<>();

    public ChatDto() {
        //Hibernate
    }

    public ChatDto(String name, ChatType type) {
        this.name = name;
        this.type = type;
    }
    

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public ChatType getChatType() {
        return type;
    }

    @Override
    public Set<Participant> getParticipants() {
        return participants;
    }

    @Override
    public void addParticipant(Participant participant) {
        this.participants.add(participant);
    }

    @Override
    public void removeParticipant(Participant participant) {
        this.participants.remove(participant);
    }

}

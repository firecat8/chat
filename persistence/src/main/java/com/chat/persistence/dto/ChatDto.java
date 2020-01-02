package com.chat.persistence.dto;

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
public class ChatDto extends AbstractDto {

    public final static String NAME = "name";

    public final static String TYPE = "type";

    public final static String PARTICIPANTS = "participants";

    @Column
    private String name;

    @Enumerated(EnumType.STRING)
    @Column
    private ChatTypeDto type;

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, mappedBy = "chat")
    private Set<ParticipantDto> participants = new HashSet<>();

    public ChatDto() {
        //Hibernate
    }

    public ChatDto(String name, ChatTypeDto type) {
        this.name = name;
        this.type = type;
    }
    
    public ChatDto(String name, ChatTypeDto type, ParticipantDto owner) {
        this.name = name;
        this.type = type;
        owner.setChat(this);
        this.participants.add(owner);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ChatTypeDto getChatType() {
        return type;
    }

    public Set<ParticipantDto> getParticipants() {
        return participants;
    }

    public void addParticipant(ParticipantDto participant) {
        this.participants.add(participant);
    }
    public void addParticipants( Set<ParticipantDto> participants) {
        this.participants.addAll(participants);
    }

    public void removeParticipant(ParticipantDto participant) {
        this.participants.remove(participant);
    }

}

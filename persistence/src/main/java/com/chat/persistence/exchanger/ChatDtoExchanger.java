package com.chat.persistence.exchanger;

import com.chat.domain.Chat;
import com.chat.domain.ChatType;
import com.chat.domain.ChatUser;
import com.chat.domain.Participant;
import com.chat.persistence.dto.ChatTypeDto;
import com.chat.persistence.dto.ChatDto;
import com.chat.persistence.dto.ChatUserDto;
import com.chat.persistence.dto.ParticipantDto;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.EntityManager;

/**
 *
 * @author gdimitrova
 */
public class ChatDtoExchanger extends DtoEntityExchanger<ChatDto, Chat> {
    
    public final static ChatDtoExchanger INSTANCE = new ChatDtoExchanger(null);
    
    private final UserDtoExchanger userDtoExchanger;
    
    public ChatDtoExchanger(EntityManager em) {
        userDtoExchanger = new UserDtoExchanger(em);
    }
    
    @Override
    public Chat exchangeFrom(ChatDto dto) {
        Chat chat = new Chat(dto.getName(), ChatType.valueOf(dto.getChatType().name()));
        chat.addParticipants(exchangeDtoListFrom(dto.getParticipants(), chat));
        return chat;
    }
    
    @Override
    public ChatDto exchangeFrom(Chat e) {
        ChatDto chatDto = new ChatDto(e.getName(), ChatTypeDto.valueOf(e.getChatType().name()));
        chatDto.addParticipants(exchangeEntityListFrom(e.getParticipants(), chatDto));
        return chatDto;
    }
    
    private Set<ParticipantDto> exchangeEntityListFrom(Set<Participant> participants, ChatDto chat) {
        Set<ParticipantDto> dtos = new HashSet<>();
        participants.stream().map((participant) -> exchangeFrom(participant)).map((participantDto) -> {
            participantDto.setChat(chat);
            return participantDto;
        }).forEachOrdered((participantDto) -> {
            dtos.add(participantDto);
        });
        return dtos;
    }
    
    private Set<Participant> exchangeDtoListFrom(Set<ParticipantDto> participants, Chat chat) {
        Set<Participant> entities = new HashSet<>();
        participants.stream().map((dto) -> exchangeFrom(dto)).map((p) -> {
            p.setChat(chat);
            return p;
        }).forEachOrdered((p) -> {
            entities.add(p);
        });
        return entities;
    }
    
    private Participant exchangeFrom(ParticipantDto dto) {
        Participant participant = new Participant(
                userDtoExchanger.exchange(dto.getUser()),
                ChatUser.valueOf(dto.getUserType().name())
        );
        participant.setId(dto.getId());
        return participant;
    }
    
    private ParticipantDto exchangeFrom(Participant e) {
        ParticipantDto participantDto = new ParticipantDto(
                userDtoExchanger.exchange(e.getUser()),
                ChatUserDto.valueOf(e.getUserType().name())
        );
        participantDto.setId(e.getId());
        return participantDto;
    }
    
}

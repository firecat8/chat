package com.chat.bl.service.messaging.exchanger;

import com.chat.domain.Chat;
import com.chat.domain.ChatType;
import com.chat.domain.ChatUser;
import com.chat.domain.Participant;
import com.chat.messaging.vo.ChatVo;
import com.chat.messaging.vo.ChatTypeVo;
import com.chat.messaging.vo.ChatUserVo;
import com.chat.messaging.vo.ParticipantVo;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author gdimitrova
 */
public class ChatVoExchanger extends VoEntityExchanger<ChatVo, Chat> {
    
    public final static ChatVoExchanger INSTANCE = new ChatVoExchanger();
    
    private ChatVoExchanger() {
    }
    
    @Override
    public Chat exchangeFrom(ChatVo vo) {
        Chat chat = new Chat(vo.getName(), ChatType.valueOf(vo.getChatType().name()));
        chat.addParticipants(exchangeVoListFrom(vo.getParticipants(), chat));
        return chat;
    }
    
    @Override
    public ChatVo exchangeFrom(Chat e) {
        ChatVo chatVo = new ChatVo(e.getName(), ChatTypeVo.valueOf(e.getChatType().name()));
        chatVo.addParticipants(exchangeEntityListFrom(e.getParticipants(), chatVo));
        return chatVo;
    }
    
    private Set<ParticipantVo> exchangeEntityListFrom(Set<Participant> participants, ChatVo chat) {
        Set<ParticipantVo> vos = new HashSet<>();
        participants.stream().map((participant) -> exchangeFrom(participant)).map((participantDto) -> {
            participantDto.setChat(chat);
            return participantDto;
        }).forEachOrdered((participantDto) -> {
            vos.add(participantDto);
        });
        return vos;
    }
    
    private Set<Participant> exchangeVoListFrom(Set<ParticipantVo> participants, Chat chat) {
        Set<Participant> entities = new HashSet<>();
        participants.stream().map((vo) -> exchangeFrom(vo)).map((p) -> {
            p.setChat(chat);
            return p;
        }).forEachOrdered((p) -> {
            entities.add(p);
        });
        return entities;
    }
    
    private Participant exchangeFrom(ParticipantVo vo) {
        Participant participant = new Participant(
                UserVoExchanger.INSTANCE.exchange(vo.getUser()),
                ChatUser.valueOf(vo.getUserType().name())
        );
        participant.setId(vo.getId());
        return participant;
    }
    
    private ParticipantVo exchangeFrom(Participant e) {
        ParticipantVo participantVo = new ParticipantVo(
                UserVoExchanger.INSTANCE.exchange(e.getUser()),
                ChatUserVo.valueOf(e.getUserType().name())
        );
        participantVo.setId(e.getId());
        return participantVo;
    }
}

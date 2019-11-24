package com.chat.persistence.dao;

import com.chat.dao.ChatEventDao;
import com.chat.domain.Chat;
import com.chat.domain.ChatEvent;
import com.chat.domain.ChatEventType;
import com.chat.domain.User;
import com.chat.persistence.dto.ChatEventDto;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;

/**
 *
 * @author gdimitrova
 */
public class ChatEventDaoImpl extends AbstractCrudDao<ChatEventDto> implements ChatEventDao {

    public ChatEventDaoImpl(EntityManager em) {
        super(ChatEventDto.class, em);
    }

    @Override
    public ChatEvent save(String message, ChatEventType chatEventType, Long eventTime, User sender, Chat chat) {
        ChatEventDto chatEventDto = new ChatEventDto(message, chatEventType, eventTime, sender, chat);
        super.save(chatEventDto);
        return chatEventDto;
    }

    @Override
    public List<ChatEvent> loadTheLastTenEvents(Chat chat) {
        return new ArrayList<>(getResults("id", chat.getId(), ChatEventDto.EVENT_TIME_COLUMN, 10));
    }

    @Override
    protected Map<String, Object> loadProperties(ChatEventDto newOne) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

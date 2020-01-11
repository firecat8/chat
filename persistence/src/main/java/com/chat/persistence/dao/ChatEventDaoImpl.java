package com.chat.persistence.dao;

import com.chat.dao.ChatEventDao;
import com.chat.domain.Chat;
import com.chat.domain.ChatEvent;
import com.chat.domain.ChatEventType;
import com.chat.domain.User;
import com.chat.persistence.dto.ChatDto;
import com.chat.persistence.dto.ChatEventTypeDto;
import com.chat.persistence.dto.UserDto;
import com.chat.persistence.dto.ChatEventDto;
import com.chat.persistence.exchanger.ChatDtoExchanger;
import com.chat.persistence.exchanger.ChatEventDtoExchanger;
import com.chat.persistence.exchanger.UserDtoExchanger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;

/**
 *
 * @author gdimitrova
 */
public class ChatEventDaoImpl extends AbstractCrudDao<ChatEventDto, ChatEvent> implements ChatEventDao {

    public ChatEventDaoImpl(EntityManager em) {
        super(ChatEventDto.class, em, ChatEventDtoExchanger.INSTANCE);
    }

    @Override
    public ChatEvent save(String message, ChatEventType eType, Long eventTime, Long senderId, Long chatId) {
        UserDto sender = em.find(UserDto.class, senderId);
        ChatDto chat = em.find(ChatDto.class, chatId);
        return save(new ChatEventDto(message, ChatEventTypeDto.valueOf(eType.name()), eventTime, sender, chat));
    }

    @Override
    public List<ChatEvent> loadTheLastTenEvents(Chat chat) {
        return getResults("id", chat.getId(), ChatEventDto.EVENT_TIME, 10);
    }

    @Override
    protected Map<String, Object> loadProperties(ChatEventDto newOne) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

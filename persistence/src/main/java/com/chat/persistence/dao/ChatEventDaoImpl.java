package com.chat.persistence.dao;

import com.chat.dao.ChatEventDao;
import com.chat.domain.Chat;
import com.chat.domain.ChatEventType;
import com.chat.domain.User;
import com.chat.persistence.dto.ChatEventDto;
import java.util.Map;
import javax.persistence.EntityManager;

/**
 *
 * @author gdimitrova
 */
public class ChatEventDaoImpl extends AbstractDao<ChatEventDto> implements ChatEventDao {

    public ChatEventDaoImpl(EntityManager em) {
        super(ChatEventDto.class, em);
    }

    @Override
    public void save(String message, ChatEventType chatEventType, Long eventTime, User sender, Chat chat) {
    }

    @Override
    protected Map<String, Object> loadProperties(ChatEventDto newOne) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

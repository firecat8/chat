package com.chat.persistence.dao;

import com.chat.dao.ChatDao;
import com.chat.domain.Chat;
import com.chat.domain.ChatType;
import com.chat.persistence.dto.ChatDto;
import com.chat.persistence.dto.ChatTypeDto;
import com.chat.persistence.exchanger.ChatDtoExchanger;
import java.util.Map;
import javax.persistence.EntityManager;

/**
 *
 * @author gdimitrova
 */
public class ChatDaoImpl extends AbstractCrudDao<ChatDto, Chat> implements ChatDao {

    public ChatDaoImpl(EntityManager em) {
        super(ChatDto.class, em, ChatDtoExchanger.INSTANCE);
    }

    @Override
    public Chat save(String name, ChatType type) {
        return save(new ChatDto(name, ChatTypeDto.valueOf(type.name())));
    }

    @Override
    protected Map<String, Object> loadProperties(ChatDto newOne) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

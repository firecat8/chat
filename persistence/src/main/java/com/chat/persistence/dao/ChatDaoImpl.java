package com.chat.persistence.dao;

import com.chat.dao.ChatDao;
import com.chat.domain.Chat;
import com.chat.domain.ChatType;
import com.chat.persistence.dto.ChatDto;
import java.util.Map;
import javax.persistence.EntityManager;

/**
 *
 * @author gdimitrova
 */
public class ChatDaoImpl extends AbstractCrudDao<ChatDto> implements ChatDao {

    public ChatDaoImpl(EntityManager em) {
        super(ChatDto.class, em);
    }

    @Override
    public Chat save(String name, ChatType type) {
        ChatDto chatDto = new ChatDto(name, type);
        super.save(chatDto);
        return chatDto;
    }

    @Override
    protected Map<String, Object> loadProperties(ChatDto newOne) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

package com.chat.persistence.dao;

import com.chat.dao.ChatDao;
import com.chat.persistence.dto.ChatDto;
import java.util.Map;
import javax.persistence.EntityManager;

/**
 *
 * @author gdimitrova
 */
public class ChatDaoImpl extends AbstractDao<ChatDto> implements ChatDao {

    public ChatDaoImpl(EntityManager em) {
        super(ChatDto.class, em);
    }

    @Override
    protected Map<String, Object> loadProperties(ChatDto newOne) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

package com.chat.bl.service.dao;

import com.chat.dao.DaoRegistry;
import com.chat.domain.Chat;
import com.chat.domain.ChatEventType;
import com.chat.domain.User;
import java.util.logging.Logger;
import javax.persistence.EntityManager;

/**
 *
 * @author gdimitrova
 */
public class ChatServiceProvider extends AbstractServiceProvider {

    private final static Logger LOGGER = Logger.getLogger(ChatServiceProvider.class.getName());

    public ChatServiceProvider(EntityManager em, DaoRegistry registry) {
        super(em, registry);
    }

    public void saveEvent(String message, ChatEventType chatEventType, Long eventTime, User sender, Chat chat) {
        registry.getChatEventDao().save(message, chatEventType, eventTime, sender, chat);
    }
}

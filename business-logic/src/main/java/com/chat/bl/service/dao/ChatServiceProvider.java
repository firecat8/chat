package com.chat.bl.service.dao;

import com.chat.dao.DaoRegistry;
import com.chat.domain.Chat;
import com.chat.domain.ChatEvent;
import com.chat.domain.ChatEventType;
import com.chat.domain.ChatType;
import com.chat.domain.User;
import java.util.List;
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

    public ChatEvent saveEvent(String message, ChatEventType chatEventType, Long eventTime, User sender, Chat chat) {
        beginTransaction();
        ChatEvent chatEvent = registry.getChatEventDao().save(message, chatEventType, eventTime, sender, chat);
        commit();
        return chatEvent;
    }

    public Chat createChat(String name, ChatType type) {
        beginTransaction();
        Chat chat = registry.getChatDao().save(name, type);
        commit();
        return chat;
    }

    public List<ChatEvent> loadTheLastTenEvents(Chat chat) {
        beginTransaction();
        List<ChatEvent> history = registry.getChatEventDao().loadTheLastTenEvents(chat);
        commit();
        return history;
    }
}

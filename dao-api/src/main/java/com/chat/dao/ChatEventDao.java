package com.chat.dao;

import com.chat.domain.Chat;
import com.chat.domain.ChatEvent;
import com.chat.domain.ChatEventType;
import com.chat.domain.User;
import java.util.List;

/**
 *
 * @author gdimitrova
 */
public interface ChatEventDao {

    public ChatEvent save(String message, ChatEventType chatEventType, Long eventTime, User sender, Chat chat);

    public List<ChatEvent> loadTheLastTenEvents(Chat chat);
}

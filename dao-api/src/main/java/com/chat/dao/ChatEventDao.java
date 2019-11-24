package com.chat.dao;

import com.chat.domain.Chat;
import com.chat.domain.ChatEventType;
import com.chat.domain.User;

/**
 *
 * @author gdimitrova
 */
public interface ChatEventDao {

    public void save(String message, ChatEventType chatEventType, Long eventTime, User sender, Chat chat);
}

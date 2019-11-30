package com.chat.dao;

import com.chat.domain.Chat;
import com.chat.domain.ChatType;

/**
 *
 * @author gdimitrova
 */
public interface ChatDao extends CrudDao<Chat> {

    public Chat save(String name, ChatType type);
}

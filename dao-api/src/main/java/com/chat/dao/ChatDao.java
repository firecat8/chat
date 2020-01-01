package com.chat.dao;

import com.chat.domain.Chat;
import com.chat.domain.ChatType;
import com.chat.domain.User;
import java.util.List;

/**
 *
 * @author gdimitrova
 */
public interface ChatDao extends CrudDao<Chat> {

    public Chat save(String name, ChatType type, User user);

    public List<Chat> loadChats(User user);

    public List<Chat> findChats(String name);
}

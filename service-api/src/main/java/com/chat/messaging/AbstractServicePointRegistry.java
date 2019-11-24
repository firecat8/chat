package com.chat.messaging;

import com.chat.bl.service.messaging.chat.ChatService;
import com.chat.bl.service.messaging.user.UserService;

/**
 *
 * @author gdimitrova
 */
public abstract class AbstractServicePointRegistry implements ServicePointRegistry {

    @Override
    public UserService getUserService() {
        return getServicePoint(UserServiceImpl.class);
    }
    @Override
    public ChatService getChatService() {
        return getServicePoint(ChatServiceImpl.class);
    }

}

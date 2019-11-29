package com.chat.server;

import com.chat.bl.service.dao.ChatServiceImpl;
import com.chat.bl.service.dao.UserServiceImpl;
import com.chat.dao.DaoRegistry;
import com.chat.messaging.services.ChatService;
import com.chat.messaging.services.UserService;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author gdimitrova
 */
public class ServiceProvider {

    private final Map<Class, Object> serviceByClazz = new HashMap<>();

    public ServiceProvider(DaoRegistry daoRegistry) {
        serviceByClazz.put(UserService.class, new UserServiceImpl(daoRegistry));
        serviceByClazz.put(ChatService.class, new ChatServiceImpl(daoRegistry));
    }

    public <T> T getService(Class<T> svcClazz) {
        return (T) serviceByClazz.get(svcClazz);
    }
}

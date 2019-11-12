package com.chat.mapper;

import com.chat.bl.service.messaging.user.UserEndpoint;
import com.chat.messaging.UserServiceImpl;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author gdimitrova
 */
public class ServiceEndpointMapper {

    private static Map<Class, Class> mapper = new ConcurrentHashMap<>() {
        {
            put(UserServiceImpl.class, UserEndpoint.class);
        }
    };

    public Class getEndpoint(Class service) {
        return mapper.get(service);
    }
}

package com.chat.mapper;

import com.chat.bl.service.messaging.EndPoint;
import com.chat.bl.service.messaging.user.UserEndpoint;
import com.chat.messaging.UserServiceImpl;
import com.chat.server.EndpointRegistry;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author gdimitrova
 */
public class ServiceEndpointMapper {

    private  Map<Class, EndPoint> mapper = new ConcurrentHashMap<>() ;

    public ServiceEndpointMapper(EndpointRegistry registry) {
        mapServiceEndpoint(registry);
    }
    

    public EndPoint getEndpoint(Class service) {
        return mapper.get(service);
    }

    private void mapServiceEndpoint(EndpointRegistry registry) {
        mapper.put(UserServiceImpl.class, registry.getUserEndpoint());
    }
}

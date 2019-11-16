package com.chat.mapper;

import com.chat.bl.service.messaging.Request;
import com.chat.bl.service.messaging.Response;
import com.chat.bl.service.messaging.user.RegistrationRequest;
import com.chat.bl.service.messaging.user.UserEndpoint;
import com.chat.server.EndpointRegistry;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 *
 * @author gdimitrova
 */
class ResourceMapper {

    private static Map<Class, Set<EndpointResource>> mapper = new ConcurrentHashMap<>() {
        {
            put(UserEndpoint.class, ConcurrentHashMap.newKeySet());
        }
    };

    public ResourceMapper(EndpointRegistry registry) {
        addResources(registry);
    }

    private void addResource(Set<EndpointResource> set, String name, Function<Request, Response> func) {
        set.add(new EndpointResource(name, func));
    }

    private void addResources(EndpointRegistry registry) {
        addUserEndpointResources(registry.getUserEndpoint());
    }

    private void addUserEndpointResources(UserEndpoint endpoint) {
        Set<EndpointResource> set = mapper.get(UserEndpoint.class);
        addResource(set, "register", (req) -> {
            return endpoint.register((RegistrationRequest) req);
        });
        //  addResource(set, "register", endpoint::register);
        // UserEndpoint::
        // set.add(new EndpointResource("login",UserEndpoint::re));
    }

}

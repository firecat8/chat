package com.chat.server.endpoint;

import com.chat.bl.service.dao.ServiceProviderRegistry;
import com.chat.bl.service.dao.ServiceProviderRegistryImpl;

/**
 *
 * @author gdimitrova
 */
public class EndpointRegistry {

    private final ServiceProviderRegistry providerRegistry = new ServiceProviderRegistryImpl();

    private final UserEndpoint userEndpoint = new UserEndpoint(providerRegistry);

    private final ChatEndpoint chatEndpoint = new ChatEndpoint(providerRegistry);

    public UserEndpoint getUserEndpoint() {
        return userEndpoint;
    }

    public ChatEndpoint getChatEndpoint() {
        return chatEndpoint;
    }

}

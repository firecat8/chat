package com.chat.server;

import com.chat.bl.service.dao.ServiceProviderRegistry;
import com.chat.bl.service.dao.ServiceProviderRegistryImpl;
import com.chat.bl.service.messaging.user.UserEndpoint;

/**
 *
 * @author gdimitrova
 */
public class EndpointRegistry {

    private ServiceProviderRegistry providerRegistry = new ServiceProviderRegistryImpl();

    private UserEndpoint userEndpoint = new UserEndpoint(providerRegistry);

    public UserEndpoint getUserEndpoint() {
        return userEndpoint;
    }
}

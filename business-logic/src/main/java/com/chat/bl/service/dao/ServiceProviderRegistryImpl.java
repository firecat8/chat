package com.chat.bl.service.dao;

import com.chat.bl.service.messaging.EndPoint;
import com.chat.bl.service.messaging.user.UserEndpoint;
import com.chat.dao.DaoRegistry;
import com.chat.persistence.dao.DaoImplRegistry;
import com.chat.persistence.dao.EntityManagerFactoryHolder;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author gdimitrova
 */
public class ServiceProviderRegistryImpl implements ServiceProviderRegistry {

    private final EntityManagerFactory factory = EntityManagerFactoryHolder.getFactory();

    private EntityManager em = factory.createEntityManager();

    protected final DaoRegistry registry = new DaoImplRegistry(em);

    private final Map<Class, ServiceProvider> svcProviders = new HashMap<>();

    public ServiceProviderRegistryImpl() {
        registerProviders();
    }

    private void registerProviders() {
        svcProviders.put(UserEndpoint.class, new UserServiceProvider(em, registry));
    }

    @Override
    public ServiceProvider getProvider(Class endPoint) {
        return svcProviders.get(endPoint);
    }

}

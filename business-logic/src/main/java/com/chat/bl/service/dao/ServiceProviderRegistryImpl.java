package com.chat.bl.service.dao;

import com.chat.dao.DaoRegistry;
import com.chat.persistence.dao.DaoImplRegistry;
import com.chat.persistence.dao.EntityManagerFactoryHolder;
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

    private UserServiceProvider userServiceProvider = new UserServiceProvider(em, registry);

    @Override
    public UserServiceProvider getUserServiceProvider() {
        return userServiceProvider;
    }

    @Override
    public void close() throws Exception {
        em.close();
        factory.close();
    }

}

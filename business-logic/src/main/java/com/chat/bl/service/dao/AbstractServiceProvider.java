package com.chat.bl.service.dao;

import com.chat.dao.DaoRegistry;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 *
 * @author gdimitrova
 */
public abstract class AbstractServiceProvider implements ServiceProvider {

    private EntityManager em;

    protected final DaoRegistry registry;

    public AbstractServiceProvider(EntityManager em, DaoRegistry registry) {
        this.em = em;
        this.registry = registry;
    }

    public void flush() {
        em.flush();
    }

    public void beginTransaction() {
        em.getTransaction().begin();
    }

    public EntityTransaction getTransaction() {
        return em.getTransaction();
    }

    public void rollback() {
        getTransaction().rollback();
    }

    public void commit() {
        em.getTransaction().commit();
    }

}

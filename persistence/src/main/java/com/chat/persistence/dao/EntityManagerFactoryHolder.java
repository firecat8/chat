/*
 * EuroRisk Systems (c) Ltd. All rights reserved.
 */
package com.chat.persistence.dao;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author gdimitrova
 */
public class EntityManagerFactoryHolder {

    public final static EntityManagerFactory FACTORY = createFactory();

    private static EntityManagerFactory createFactory() {
        EntityManagerFactory factory = null;
        factory = Persistence.createEntityManagerFactory("com.chat");
        return factory;
    }


}

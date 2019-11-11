/*
 * EuroRisk Systems (c) Ltd. All rights reserved.
 */
package com.chat.dao;

import java.util.List;

/**
 *
 * @author gdimitrova
 * @param <Entity>
 */
public interface CrudDao<Entity> {

    public void update(Entity oldOne);

    public void save(Entity entity);

    public void delete(Long id);

    public void deleteAll(List<Entity> list);

    public Entity loadById(Long id) ;

}

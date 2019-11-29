/*
 * EuroRisk Systems (c) Ltd. All rights reserved.
 */
package com.chat.dao;

import com.chat.domain.Entity;
import java.util.List;

/**
 *
 * @author gdimitrova
 */
public interface CrudDao<E extends Entity> {

    public void update(E oldOne);

    public void save(E entity);

    public void delete(Long id);

    public void deleteAll(List<E> list);

    public E loadById(Long id);

}

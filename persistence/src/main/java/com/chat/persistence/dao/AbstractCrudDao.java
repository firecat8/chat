package com.chat.persistence.dao;

import com.chat.dao.CrudDao;
import com.chat.domain.Entity;
import com.chat.domain.exchanger.DtoEntityExchanger;
import com.chat.persistence.dto.Dto;
import java.util.HashMap;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author gdimitrova
 * @param <E>
 */
public abstract class AbstractCrudDao<D extends Dto, E extends Entity> extends AbstractDao<D, E> implements CrudDao<E> {

    protected AbstractCrudDao(Class<D> dtoClass, EntityManager em, DtoEntityExchanger<D, E> exchanger) {
        super(dtoClass, em, exchanger);
    }

    @Override
    public void save(E entity) {
        saveOrUpdate(exchanger.exchange(entity));
    }

    @Override
    public void update(E e) {
        super.update(exchanger.exchange(e), new HashMap<>());
    }

    @Override
    public void delete(Long id) {
        CriteriaBuilder cb = getCriteriaBuilder();
        CriteriaDelete<D> delete = cb.createCriteriaDelete(dtoClassName);
        Root<D> root = delete.from(dtoClassName);
        delete.where(cb.equal(root.get("id"), id));
        em.createQuery(delete).executeUpdate();
    }

    @Override
    public void deleteAll(List<E> list) {
        list.forEach((e) -> {
            delete(e.getId());
//            em.remove(e);
        });
    }

    @Override
    public E loadById(Long id) {
        return getResult("id", id);
    }

    public List<E> loadAll() {
        CriteriaBuilder cb = getCriteriaBuilder();
        CriteriaQuery<D> query = cb.createQuery(dtoClassName);
        query.select(query.from(dtoClassName));
        return exchangeResults(em.createQuery(query).getResultList());
    }

    protected void persistEntity(D entity) {
        em.persist(entity);
    }

    protected void saveOrUpdateAll(List<D> entities) {
        entities.forEach((e) -> {
            saveOrUpdate(e);
        });
    }

    protected void mergeEntity(D entity) {
        em.merge(entity);
    }

    protected void saveOrUpdate(D entity) {
        if (entity.getId() == null || entity.getId() > 0) {
            mergeEntity(entity);
            return;
        }
        persistEntity(entity);
    }

    public E save(D entity) {
        saveOrUpdate(entity);
        return exchanger.exchange(entity);
    }
}

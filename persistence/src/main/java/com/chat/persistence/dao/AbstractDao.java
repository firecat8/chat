package com.chat.persistence.dao;

import com.chat.domain.Entity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.Table;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 *
 * @author gdimitrova
 */
public abstract class AbstractDao<E extends Entity> {

    protected final String table;

    protected final EntityManager em;

    protected final Class<E> dtoClassName;

    protected AbstractDao(Class<E> dtoClass, EntityManager em) {
        this.table = resolveTableName(dtoClass);
        this.dtoClassName = dtoClass;
        this.em = em;
    }

    public Class<E> getDtoClassName() {
        return dtoClassName;
    }

    public CriteriaBuilder getCriteriaBuilder() {
        return em.getCriteriaBuilder();
    }

    protected E getResult(String property, Object value) {
        List<E> results = getResults(property, value);
        if (results.isEmpty()) {
            //      throw new NotFoundResultsException();
        }
        return results.get(0);
    }

    protected List<E> getResults(String property, Object value) {
        CriteriaBuilder cb = getCriteriaBuilder();
        CriteriaQuery<E> query = cb.createQuery(dtoClassName);
        Expression<E> prop = query.from(dtoClassName).get(property);
        query.where(cb.equal(prop, value));
        return em.createQuery(query).getResultList();
    }

    protected List<E> getResults(Map<String, Object> properties) {
        CriteriaBuilder cb = getCriteriaBuilder();
        CriteriaQuery<E> query = cb.createQuery(dtoClassName);
        Root<E> entity = query.from(dtoClassName);
        List<Predicate> list = new ArrayList<>();
        properties.entrySet().forEach((entry) -> {
            list.add(cb.equal(entity.get(entry.getKey()), entry.getValue()));
        });
        query.where(cb.and(list.toArray(new Predicate[]{})));
        return em.createQuery(query).getResultList();
    }

    protected List<E> getResults(String property, Double value, ComparisonSign sign) {
        CriteriaBuilder cb = getCriteriaBuilder();
        CriteriaQuery<E> query = cb.createQuery(dtoClassName);
        Expression<Double> prop = query.from(dtoClassName).get(property);
        query.where(makeExpr(cb, prop, value, sign));
        return em.createQuery(query).getResultList();
    }

    protected List<E> getResults(String property, Long value) {
        CriteriaBuilder cb = getCriteriaBuilder();
        CriteriaQuery<E> query = cb.createQuery(dtoClassName);
        Expression<Long> prop = query.from(dtoClassName).get(property);
        query.where(cb.le(prop, value));
        return em.createQuery(query).getResultList();
    }

    protected List<E> getResults(String property, Long from, Long to) {
        CriteriaBuilder cb = getCriteriaBuilder();
        CriteriaQuery<E> query = cb.createQuery(dtoClassName);
        Expression<Long> prop = query.from(dtoClassName).get(property);
        query.where(cb.between(prop, from, to));
        return em.createQuery(query).getResultList();
    }

    protected int update(E oldOne, Map<String, Object> properties) {
        CriteriaBuilder cb = getCriteriaBuilder();
        CriteriaUpdate<E> updateCriteria = cb.createCriteriaUpdate(dtoClassName);
        Root<E> root = updateCriteria.from(dtoClassName);
        properties.entrySet().forEach((entry) -> {
            updateCriteria.set(entry.getKey(), entry.getValue());
        });
        updateCriteria.where(cb.equal(root.get("id"), oldOne.getId()));
        return em.createQuery(updateCriteria).executeUpdate();
    }

    private Expression<Boolean> makeExpr(CriteriaBuilder cb, Expression<Double> prop, Double val, ComparisonSign sign) {
        switch (sign) {
            case EQUAL:
                return cb.equal(prop, val);
            case LESS_THAN:
                return cb.lessThan(prop, val);
            default:
                throw new AssertionError(sign.name());
        }
    }

    private String resolveTableName(Class<E> dtoClass) {
        Table t = dtoClass.getAnnotation(Table.class);
        return t == null ? dtoClass.getSimpleName() : t.name();
    }

    protected abstract Map<String, Object> loadProperties(E newOne);
}

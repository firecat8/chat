package com.chat.persistence.dao;

import com.chat.domain.Entity;
import com.chat.domain.exchanger.DtoEntityExchanger;
import com.chat.persistence.dto.Dto;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
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
 * @param <D>
 * @param <E>
 */
public abstract class AbstractDao<D extends Dto, E extends Entity> {

    protected final DtoEntityExchanger<D, E> exchanger;

    protected final String table;

    protected final EntityManager em;

    protected final Class<D> dtoClassName;

    protected AbstractDao(Class<D> dtoClass, EntityManager em, DtoEntityExchanger<D, E> exchanger) {
        this.table = resolveTableName(dtoClass);
        this.dtoClassName = dtoClass;
        this.em = em;
        this.exchanger = exchanger;
    }

    public CriteriaBuilder getCriteriaBuilder() {
        return em.getCriteriaBuilder();
    }

    protected E getResult(String property, Object value) {
        List<E> results = getResults(property, value);
        if (results.isEmpty()) {
            return null;
        }
        return results.get(0);
    }

    protected List<E> getResultsLikeExpr(String property, String value) {
        CriteriaBuilder cb = getCriteriaBuilder();
        CriteriaQuery<D> query = cb.createQuery(dtoClassName);
        Root<D> root = query.from(dtoClassName);
        query.select(root).where(cb.like(root.get(property), "%" + value + "%"));
        return exchangeResults(em.createQuery(query).getResultList());
    }

    protected List<E> getResultsLikeExpr(String property, String value, String propId, Long id) {
        CriteriaBuilder cb = getCriteriaBuilder();
        CriteriaQuery<D> query = cb.createQuery(dtoClassName);
        Root<D> root = query.from(dtoClassName);
        query.select(root).where(cb.and(cb.like(root.get(property), "%" + value + "%"), cb.not(cb.equal(root.get(propId), id))));
        return exchangeResults(em.createQuery(query).getResultList());
    }

    protected List<E> getResults(String property, Object value) {
        CriteriaBuilder cb = getCriteriaBuilder();
        CriteriaQuery<D> query = cb.createQuery(dtoClassName);
        Expression<E> prop = query.from(dtoClassName).get(property);
        query.where(cb.equal(prop, value));
        return exchangeResults(em.createQuery(query).getResultList());
    }

    protected List<E> getResults(String property, Object value, String orderProperty, int limit) {
        CriteriaBuilder cb = getCriteriaBuilder();
        CriteriaQuery<D> query = cb.createQuery(dtoClassName);
        Expression<D> prop = query.from(dtoClassName).get(property);
        query.orderBy(cb.desc(query.from(dtoClassName).get(orderProperty)));
        query.where(cb.equal(prop, value));
        return exchangeResults(em.createQuery(query).setMaxResults(limit).getResultList());
    }

    protected List<E> getResults(Map<String, Object> properties) {
        CriteriaBuilder cb = getCriteriaBuilder();
        CriteriaQuery<D> query = cb.createQuery(dtoClassName);
        Root<D> entity = query.from(dtoClassName);
        List<Predicate> list = new ArrayList<>();
        properties.entrySet().forEach((entry) -> {
            list.add(cb.equal(entity.get(entry.getKey()), entry.getValue()));
        });
        query.where(cb.and(list.toArray(new Predicate[]{})));
        return exchangeResults(em.createQuery(query).getResultList());
    }

    protected List<E> getResults(String property, Double value, ComparisonSign sign) {
        CriteriaBuilder cb = getCriteriaBuilder();
        CriteriaQuery<D> query = cb.createQuery(dtoClassName);
        Expression<Double> prop = query.from(dtoClassName).get(property);
        query.where(makeExpr(cb, prop, value, sign));
        return exchangeResults(em.createQuery(query).getResultList());
    }

    protected List<E> getResults(String property, Long value) {
        CriteriaBuilder cb = getCriteriaBuilder();
        CriteriaQuery<D> query = cb.createQuery(dtoClassName);
        Expression<Long> prop = query.from(dtoClassName).get(property);
        query.where(cb.le(prop, value));
        return exchangeResults(em.createQuery(query).getResultList());
    }

    protected List<E> getResults(String property, Long from, Long to) {
        CriteriaBuilder cb = getCriteriaBuilder();
        CriteriaQuery<D> query = cb.createQuery(dtoClassName);
        Expression<Long> prop = query.from(dtoClassName).get(property);
        query.where(cb.between(prop, from, to));
        return exchangeResults(em.createQuery(query).getResultList());
    }

    protected int update(D oldOne, Map<String, Object> properties) {
        CriteriaBuilder cb = getCriteriaBuilder();
        CriteriaUpdate<D> updateCriteria = cb.createCriteriaUpdate(dtoClassName);
        Root<D> root = updateCriteria.from(dtoClassName);
        properties.entrySet().forEach((entry) -> {
            updateCriteria.set(entry.getKey(), entry.getValue());
        });
        updateCriteria.where(cb.equal(root.get("id"), oldOne.getId()));
        return em.createQuery(updateCriteria).executeUpdate();
    }

    protected List<E> exchangeResults(List<D> resultList) {
        if (resultList.isEmpty()) {
            return new ArrayList<>();
        }
        return resultList.stream().map(r -> exchanger.exchange(r)).collect(Collectors.toList());
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

    private String resolveTableName(Class<D> dtoClass) {
        Table t = dtoClass.getAnnotation(Table.class);
        return t == null ? dtoClass.getSimpleName() : t.name();
    }

    protected abstract Map<String, Object> loadProperties(D newOne);
}

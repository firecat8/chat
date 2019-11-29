package com.chat.domain.exchanger;

import com.chat.domain.Entity;

/**
 *
 * @author gdimitrova
 * @param <D>
 * @param <E>
 */
public abstract class AbstractDtoEntityExchanger< D, E extends Entity> implements DtoEntityExchanger<D, E> {

    @Override
    public E exchange(D dto) {
        return exchangeId(dto, exchangeFrom(dto));
    }

    @Override
    public D exchange(E e) {
        return exchangeId(e, exchangeFrom(e));
    }

    abstract protected E exchangeFrom(D dto);

    abstract protected D exchangeFrom(E e);

}

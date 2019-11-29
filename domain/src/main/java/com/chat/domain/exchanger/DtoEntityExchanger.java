package com.chat.domain.exchanger;

import com.chat.domain.Entity;

/**
 *
 * @author gdimitrova
 * @param <D>
 * @param <E>
 */
public interface DtoEntityExchanger< D, E extends Entity> {

    public E exchange(D dto);

    public D exchange(E e);

    public E exchangeId(D from, E to);

    public D exchangeId(E from, D to);
}

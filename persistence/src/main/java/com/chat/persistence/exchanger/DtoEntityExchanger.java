package com.chat.persistence.exchanger;

import com.chat.domain.Entity;
import com.chat.domain.exchanger.AbstractDtoEntityExchanger;
import com.chat.persistence.dto.Dto;

/**
 *
 * @author gdimitrova
 */
public abstract class DtoEntityExchanger<D extends Dto, E extends Entity> extends AbstractDtoEntityExchanger<D, E> {

    @Override
    public E exchangeId(D from, E to) {
        to.setId(from.getId());
        return to;
    }

    @Override
    public D exchangeId(E from, D to) {
        to.setId(from.getId());
        return to;
    }

}

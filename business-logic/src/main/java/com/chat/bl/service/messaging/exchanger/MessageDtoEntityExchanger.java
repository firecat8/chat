package com.chat.bl.service.messaging.exchanger;

import com.chat.domain.Entity;
import com.chat.domain.exchanger.AbstractDtoEntityExchanger;
import com.chat.messaging.dto.MessageDto;

/**
 *
 * @author gdimitrova
 * @param <D>
 * @param <E>
 */
public abstract class MessageDtoEntityExchanger<D extends MessageDto, E extends Entity> extends AbstractDtoEntityExchanger<D, E> {

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

package com.chat.bl.service.dao;

import com.chat.dao.DaoRegistry;
import com.chat.messaging.dto.ErrorMessageDto;
import com.chat.messaging.message.ResponseCode;
import com.chat.messaging.message.ResponseListener;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gdimitrova
 */
public class AbstractTransactionalService {

    private static final Logger LOGGER = Logger.getLogger(AbstractTransactionalService.class.getName());

    private final DaoRegistry daoRegistry;

    public AbstractTransactionalService(DaoRegistry daoRegistry) {
        this.daoRegistry = daoRegistry;
    }

    protected final <T> void doInTransaction(Function<DaoRegistry, T> work, ResponseListener<T> listener) {

        try {
            daoRegistry.beginTransaction();
            T result = work.apply(daoRegistry);
            daoRegistry.commitTransaction();
            listener.onSuccess(result);
        } catch (MessageException ex) {
            daoRegistry.rollbackTransaction();
            LOGGER.log(Level.SEVERE, ex.getMessage());
            listener.onError(new ErrorMessageDto(ResponseCode.ERROR, ex.getMessage()));
        } catch (Throwable ex) {

            daoRegistry.rollbackTransaction();
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            listener.onError(new ErrorMessageDto(ResponseCode.SERVER_ERROR, ex.getMessage()));
        }
    }
}

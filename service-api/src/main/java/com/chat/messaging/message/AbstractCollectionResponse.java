package com.chat.messaging.message;

import java.util.List;

/**
 *
 * @author gdimitrova
 * @param <T>
 */
public class AbstractCollectionResponse<T> extends AbstractResponse {
    private final List<T> entities;

    public AbstractCollectionResponse(List<T> entities) {
        this.entities = entities;
    }

    public List<T> getList() {
        return entities;
    }
    
}

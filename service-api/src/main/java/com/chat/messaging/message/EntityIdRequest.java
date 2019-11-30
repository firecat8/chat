package com.chat.messaging.message;

/**
 *
 * @author gdimitrova
 */
public class EntityIdRequest extends AbstractRequest {

    private final Long id;

    public EntityIdRequest(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

}

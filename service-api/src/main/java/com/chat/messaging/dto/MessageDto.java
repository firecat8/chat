package com.chat.messaging.dto;

import java.io.Serializable;

/**
 *
 * @author gdimitrova
 */
public abstract class MessageDto implements Serializable {

    protected Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}

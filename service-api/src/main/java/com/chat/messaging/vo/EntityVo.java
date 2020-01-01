package com.chat.messaging.vo;

import java.io.Serializable;

/**
 *
 * @author gdimitrova
 */
public abstract class EntityVo implements Serializable {

    protected Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}

package com.chat.persistence.dto;

import java.io.Serializable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 *
 * @author gdimitrova
 */
@MappedSuperclass
public abstract class AbstractDto implements Dto, Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    protected Long id;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

}

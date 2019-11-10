package com.chat.persistence.dto;

import com.chat.domain.Chat;
import com.chat.domain.ConversationType;

/**
 *
 * @author gdimitrova
 */
public abstract class ConversationDto extends AbstractDto implements Chat{

    private String name;
    
    protected ConversationType chatType;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
    
}

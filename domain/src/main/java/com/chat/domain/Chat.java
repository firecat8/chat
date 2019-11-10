package com.chat.domain;

/**
 *
 * @author gdimitrova
 */
public interface Chat extends Entity {

    public String getName();

    public void setName(String name);

    public ConversationType getChatType();

}

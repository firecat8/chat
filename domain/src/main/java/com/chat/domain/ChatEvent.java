package com.chat.domain;

/**
 *
 * @author gdimitrova
 */
public interface ChatEvent {

    public String getMessage();

    public Long getEventTime();

    public User getSender();

    public Chat getChat();

    public ChatEventType getChatEventType();
}

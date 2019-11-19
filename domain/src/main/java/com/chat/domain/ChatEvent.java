package com.chat.domain;

/**
 *
 * @author gdimitrova
 */
public interface ChatEvent {

    public Long getEventTime();

    public User getSender();

    public Chat getChat();
}

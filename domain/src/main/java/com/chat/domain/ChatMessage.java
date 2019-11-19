package com.chat.domain;

/**
 *
 * @author gdimitrova
 */
public interface ChatMessage {

    public String getMessage();

    public Long getSentTime();

    public User getSender();

    public Chat getChat();
}

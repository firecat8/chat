package com.chat.domain;

/**
 *
 * @author gdimitrova
 */
public interface Participant {

    public User getUser();

    public ChatUser getUserType();

    public Chat getChat();
}

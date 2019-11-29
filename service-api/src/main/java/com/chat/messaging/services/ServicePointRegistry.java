package com.chat.messaging.services;

/**
 *
 * @author gdimitrova
 */
public interface ServicePointRegistry extends AutoCloseable {

    public UserService getUserService();

    public ChatService getChatService();
}

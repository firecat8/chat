package com.chat.messaging;

import com.chat.bl.service.messaging.user.UserService;

/**
 *
 * @author gdimitrova
 */
public interface ServicePointRegistry extends AutoCloseable {

    public UserService getUserService();

    public <T extends ServicePoint> T getServicePoint(Class<T> sei);
}

package com.chat.bl.service.dao;

/**
 *
 * @author gdimitrova
 */
public interface ServiceProviderRegistry  extends AutoCloseable{

    public UserServiceProvider getUserServiceProvider();
}

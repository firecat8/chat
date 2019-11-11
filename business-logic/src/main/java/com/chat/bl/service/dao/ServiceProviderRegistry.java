package com.chat.bl.service.dao;

/**
 *
 * @author gdimitrova
 */
public interface ServiceProviderRegistry {

    public ServiceProvider getProvider(Class endPoint);

  //  public UserServiceProvider getUserServiceProvider();
}

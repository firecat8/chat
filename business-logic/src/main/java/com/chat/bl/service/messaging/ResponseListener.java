package com.chat.bl.service.messaging;

/**
 *
 * @author gdimitrova
 */
public interface ResponseListener<T> {

    public void onSuccess(T response);

    public void onError(String error);

}

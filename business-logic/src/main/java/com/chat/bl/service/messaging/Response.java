package com.chat.bl.service.messaging;

/**
 *
 * @author gdimitrova
 */
public interface Response {

    public ResponseCode getResponseCode();

    public Object getResponse();

    public Class getResponseClass();
}

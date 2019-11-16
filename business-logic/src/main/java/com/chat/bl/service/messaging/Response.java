package com.chat.bl.service.messaging;

import com.chat.bl.service.messaging.response.ResponseCode;

/**
 *
 * @author gdimitrova
 */
public interface Response {

    public ResponseCode getResponseCodeVo();

    public Object getResponse();

    public Class getResponseClass();
}

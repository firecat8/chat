package com.chat.bl.service.messaging.response;

import com.chat.bl.service.messaging.Response;

/**
 *
 * @author gdimitrova
 */
public class ResponseImpl implements Response {

    private ResponseCode responseCodeVo;

    private Object response;

    public ResponseImpl(ResponseCode responseCodeVo, Object response) {
        this.responseCodeVo = responseCodeVo;
        this.response = response;
    }

    public ResponseCode getResponseCodeVo() {
        return responseCodeVo;
    }

    public Object getResponse() {
        return response;
    }

    @Override
    public Class getResponseClass() {
        return response.getClass();
    }

}

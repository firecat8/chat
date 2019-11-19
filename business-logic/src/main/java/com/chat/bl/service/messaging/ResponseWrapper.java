package com.chat.bl.service.messaging;

import java.io.Serializable;

/**
 *
 * @author gdimitrova
 * @param <T> response type
 */
public class ResponseWrapper<T> implements Serializable {

    private final ResponseCode code;

    private final T response;

    private final String error;

    private ResponseWrapper(ResponseCode code, T response, String error) {
        this.code = code;
        this.response = response;
        this.error = error;
    }

    public ResponseWrapper(ResponseCode code, T response) {
        this(code, response, "");
    }

    public ResponseWrapper(ResponseCode code, String error) {
        this(code, null, error);
    }

    public ResponseCode getCode() {
        return code;
    }

    public T getResponse() {
        return response;
    }

    public String getError() {
        return code.name() + " " + error;
    }

}

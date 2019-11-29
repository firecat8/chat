package com.chat.messaging.message;

import java.io.Serializable;

/**
 *
 * @author gdimitrova
 */
public class RequestWrapper implements Serializable {

    private final Class serviceClass;

    private final String method;

    private final Class reqClass;

    private final Request req;

    public RequestWrapper(Class serviceClass, String method, Request req) {
        this.serviceClass = serviceClass;
        this.method = method;
        this.reqClass = req.getClass();
        this.req = req;
    }

    public Class getServiceClass() {
        return serviceClass;
    }

    public String getMethod() {
        return method;
    }

    public Class getReqClass() {
        return reqClass;
    }

    public Request getRequest() {
        return req;
    }

}

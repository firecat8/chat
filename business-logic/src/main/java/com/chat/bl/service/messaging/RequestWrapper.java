package com.chat.bl.service.messaging;

import java.io.Serializable;

/**
 *
 * @author gdimitrova
 */
public class RequestWrapper implements Serializable {

    private final Class serviceClass;

    private final String method;

    private final Class reqClass;

    private final Class respClass;

    private final Request req;

    public RequestWrapper(Class serviceClass, String method, Request req, Class respClass) {
        this.serviceClass = serviceClass;
        this.method = method;
        this.reqClass = req.getClass();
        this.req = req;
        this.respClass = respClass;
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

    public Class getRespClass() {
        return respClass;
    }
    

}

package com.chat.mapper;

import com.chat.bl.service.messaging.Request;
import com.chat.bl.service.messaging.Response;
import java.util.function.Function;

/**
 *
 * @author gdimitrova
 */
public class EndpointResource {

    private String name;

    private Function<? extends Request,? extends  Response> resource;

    public EndpointResource(String name, Function<? extends Request,? extends  Response> resource) {
        this.name = name;
        this.resource = resource;
    }
    
}

package com.chat.bl.service.messaging;

/**
 *
 * @author gdimitrova
 */
public class MessageWrapper {

    private final Class serviceClass;

    private final String resource;

    private final Class messageClass;

    private final Message message;

    public MessageWrapper(Class serviceClass, String resource, Class messageClass, Message message) {
        this.serviceClass = serviceClass;
        this.resource = resource;
        this.messageClass = messageClass;
        this.message = message;
    }

    public Class getServiceClass() {
        return serviceClass;
    }

    public String getResource() {
        return resource;
    }

    public Class getMessageClass() {
        return messageClass;
    }

    public Message getMessage() {
        return message;
    }
    
    
}

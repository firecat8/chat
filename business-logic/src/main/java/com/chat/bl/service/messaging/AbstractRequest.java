package com.chat.bl.service.messaging;

/**
 *
 * @author gdimitrova
 */
public class AbstractRequest implements Request{

    @Override
    public MessageType getMessageType() {
        return MessageType.REQUEST;
    }
    
}

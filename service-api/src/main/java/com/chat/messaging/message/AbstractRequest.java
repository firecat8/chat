package com.chat.messaging.message;

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

package com.chat.messaging.message;

/**
 *
 * @author gdimitrova
 */
public class AbstractResponse implements Response{
    
    @Override
    public MessageType getMessageType() {
        return MessageType.RESPONSE;
    }
}

package com.chat.bl.service.messaging;

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
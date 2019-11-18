package com.chat.bl.service.messaging;

import java.io.Serializable;

/**
 *
 * @author gdimitrova
 */
public interface Message extends Serializable{
    public MessageType getMessageType();
}

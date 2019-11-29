package com.chat.messaging.message;

import java.io.Serializable;

/**
 *
 * @author gdimitrova
 */
public interface Message extends Serializable{
    public MessageType getMessageType();
}

package com.chat.bl.service.messaging.chat;

import com.chat.bl.service.messaging.ResponseListener;

/**
 *
 * @author gdimitrova
 */
public interface ChatService {

    public void sendMessage(SendMessageRequest req, ResponseListener<Void> listener);

    public void sendLog(SendLogRequest req, ResponseListener<Void> listener);

    public void sendFile(SendFileRequest req, ResponseListener<Void> listener);
}

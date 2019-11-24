package com.chat.messaging;

import com.chat.bl.service.messaging.ResponseListener;
import com.chat.bl.service.messaging.chat.ChatService;
import com.chat.bl.service.messaging.chat.SendFileRequest;
import com.chat.bl.service.messaging.chat.SendLogRequest;
import com.chat.bl.service.messaging.chat.SendMessageRequest;

/**
 *
 * @author gdimitrova
 */
public class ChatServiceImpl implements ChatService, ServicePoint {

    private Client client;

    public ChatServiceImpl(Client client) {
        this.client = client;
    }

    @Override
    public void sendMessage(SendMessageRequest req, ResponseListener<Void> listener) {
        client.sendMessage(this.getClass(), "sendMessage", req, Void.class, listener);
    }

    @Override
    public void sendLog(SendLogRequest req, ResponseListener<Void> listener) {
        client.sendMessage(this.getClass(), "sendLog", req, Void.class, listener);
    }

    @Override
    public void sendFile(SendFileRequest req, ResponseListener<Void> listener) {
        client.sendMessage(this.getClass(), "sendFile", req, Void.class, listener);
    }

}

package com.chat.messaging;

import com.chat.bl.service.messaging.ResponseListener;
import com.chat.bl.service.messaging.chat.ChatHistory;
import com.chat.bl.service.messaging.chat.ChatService;
import com.chat.bl.service.messaging.chat.CreateChatRequest;
import com.chat.bl.service.messaging.chat.DownloadFile;
import com.chat.bl.service.messaging.chat.DownloadFileRequest;
import com.chat.bl.service.messaging.chat.LoadHistoryRequest;
import com.chat.bl.service.messaging.chat.SendFileRequest;
import com.chat.bl.service.messaging.chat.SendLogRequest;
import com.chat.bl.service.messaging.chat.SendMessageRequest;
import com.chat.domain.Chat;
import com.chat.domain.ChatEvent;

/**
 *
 * @author gdimitrova
 */
public class ChatServiceImpl implements ChatService, ServicePoint {

    private final Client client;

    public ChatServiceImpl(Client client) {
        this.client = client;
    }

    @Override
    public void sendMessage(SendMessageRequest req, ResponseListener<ChatEvent> listener) {
        client.sendMessage(this.getClass(), "sendMessage", req, ChatEvent.class, listener);
    }

    @Override
    public void sendLog(SendLogRequest req, ResponseListener<ChatEvent> listener) {
        client.sendMessage(this.getClass(), "sendLog", req, ChatEvent.class, listener);
    }

    @Override
    public void sendFile(SendFileRequest req, ResponseListener<ChatEvent> listener) {
        client.sendMessage(this.getClass(), "sendFile", req, ChatEvent.class, listener);
    }

    @Override
    public void createChat(CreateChatRequest req, ResponseListener<Chat> listener) {
        client.sendMessage(this.getClass(), "createChat", req, Chat.class, listener);
    }

    @Override
    public void loadLastTenEvents(LoadHistoryRequest req, ResponseListener<ChatHistory> listener) {
        client.sendMessage(this.getClass(), "loadLastTenEvents", req, ChatHistory.class, listener);
    }

    @Override
    public void getFile(DownloadFileRequest req, ResponseListener<DownloadFile> listener) {
        client.sendMessage(this.getClass(), "getFile", req, DownloadFile.class, listener);
    }

}

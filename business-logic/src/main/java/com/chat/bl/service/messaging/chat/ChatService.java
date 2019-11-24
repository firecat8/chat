package com.chat.bl.service.messaging.chat;

import com.chat.bl.service.messaging.ResponseListener;
import com.chat.domain.Chat;
import com.chat.domain.ChatEvent;

/**
 *
 * @author gdimitrova
 */
public interface ChatService {

    public void sendMessage(SendMessageRequest req, ResponseListener<ChatEvent> listener);

    public void sendLog(SendLogRequest req, ResponseListener<ChatEvent> listener);

    public void sendFile(SendFileRequest req, ResponseListener<ChatEvent> listener);

    public void getFile(DownloadFileRequest req, ResponseListener<DownloadFile> listener);

    public void createChat(CreateChatRequest req, ResponseListener<Chat> listener);

    public void loadLastTenEvents(LoadHistoryRequest req, ResponseListener<ChatHistory> listener);
}

package com.chat.messaging.services;

import com.chat.messaging.message.ResponseListener;
import com.chat.messaging.dto.ChatEventMessageDto;
import com.chat.messaging.dto.ChatHistoryMessageDto;
import com.chat.messaging.dto.ChatMessageDto;
import com.chat.messaging.dto.DownloadFile;
import com.chat.messaging.message.chat.AddParticipantRequest;
import com.chat.messaging.message.chat.CreateChatRequest;
import com.chat.messaging.message.chat.DownloadFileRequest;
import com.chat.messaging.message.chat.LeaveChatrRequest;
import com.chat.messaging.message.chat.LoadHistoryRequest;
import com.chat.messaging.message.chat.SendFileRequest;
import com.chat.messaging.message.chat.SendLogRequest;
import com.chat.messaging.message.chat.SendMessageRequest;

/**
 *
 * @author gdimitrova
 */
public interface ChatService {

    public void sendMessage(SendMessageRequest req, ResponseListener<ChatEventMessageDto> listener);

    public void sendLog(SendLogRequest req, ResponseListener<ChatEventMessageDto> listener);

    public void sendFile(SendFileRequest req, ResponseListener<ChatEventMessageDto> listener);

    public void getFile(DownloadFileRequest req, ResponseListener<DownloadFile> listener);

    public void createChat(CreateChatRequest req, ResponseListener<ChatMessageDto> listener);

    public void leaveChat(LeaveChatrRequest req, ResponseListener<Void> listener);

    public void addParticipant(AddParticipantRequest req, ResponseListener<Void> listener);

    public void loadLastTenEvents(LoadHistoryRequest req, ResponseListener<ChatHistoryMessageDto> listener);
}

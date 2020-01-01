package com.chat.messaging.services;

import com.chat.messaging.message.ResponseListener;
import com.chat.messaging.vo.DownloadedFile;
import com.chat.messaging.message.SuccessResponse;
import com.chat.messaging.message.chat.AddFriendRequest;
import com.chat.messaging.message.chat.ChatEventResponse;
import com.chat.messaging.message.chat.ChatHistoryResponse;
import com.chat.messaging.message.chat.ChatResponse;
import com.chat.messaging.message.chat.ChatsResponse;
import com.chat.messaging.message.chat.CreateChatRequest;
import com.chat.messaging.message.chat.DownloadFileRequest;
import com.chat.messaging.message.chat.FindChatRequest;
import com.chat.messaging.message.chat.LeaveChatRequest;
import com.chat.messaging.message.chat.LoadChatsRequest;
import com.chat.messaging.message.chat.LoadHistoryRequest;
import com.chat.messaging.message.chat.SendFileRequest;
import com.chat.messaging.message.chat.SendLogRequest;
import com.chat.messaging.message.chat.SendMessageRequest;

/**
 *
 * @author gdimitrova
 */
public interface ChatService {

    public void sendMessage(SendMessageRequest req, ResponseListener<ChatEventResponse> listener);

    public void sendLog(SendLogRequest req, ResponseListener<ChatEventResponse> listener);

    public void sendFile(SendFileRequest req, ResponseListener<ChatEventResponse> listener);

    public void downloadFile(DownloadFileRequest req, ResponseListener<DownloadedFile> listener);

    public void createChat(CreateChatRequest req, ResponseListener<ChatResponse> listener);

    public void loadChats(LoadChatsRequest req, ResponseListener<ChatsResponse> listener);

    public void findChats(FindChatRequest req, ResponseListener<ChatsResponse> listener);

    public void leaveChat(LeaveChatRequest req, ResponseListener<SuccessResponse> listener);

    public void addFriend(AddFriendRequest req, ResponseListener<SuccessResponse> listener);

    public void loadLastTenEvents(LoadHistoryRequest req, ResponseListener<ChatHistoryResponse> listener);
}

package com.chat.task;

import com.chat.app.ClientApp;
import com.chat.messaging.dto.ChatEventMessageDto;
import com.chat.messaging.dto.ChatMessageDto;
import com.chat.messaging.dto.DownloadedFile;
import com.chat.messaging.dto.ErrorMessageDto;
import com.chat.messaging.dto.UserMessageDto;
import com.chat.messaging.dto.UserStatusMsgDto;
import com.chat.messaging.message.Request;
import com.chat.messaging.message.Response;
import com.chat.messaging.message.ResponseListener;
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
import com.chat.messaging.message.chat.SendMessageRequest;
import com.chat.messaging.message.user.ChangeStatusRequest;
import com.chat.messaging.message.user.FindFriendRequest;
import com.chat.messaging.message.user.FriendRequestResponse;
import com.chat.messaging.message.user.FriendRequestsResponse;
import com.chat.messaging.message.user.LoadFriendRequests;
import com.chat.messaging.message.user.LoginRequest;
import com.chat.messaging.message.user.LogoutRequest;
import com.chat.messaging.message.user.RegistrationRequest;
import com.chat.messaging.message.user.SendFriendRequest;
import com.chat.messaging.message.user.UserResponse;
import com.chat.messaging.message.user.UsersResponse;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import javafx.application.Platform;

/**
 *
 * @author gdimitrova
 */
public class TaskFactory {

    public static Task createLoginTask(String username, String password, Consumer<UserResponse> onSuccess, Consumer<ErrorMessageDto> onError) {
        return createTask(ClientApp.getRegistry().getUserService()::login, new LoginRequest(username, password), onSuccess, onError);
    }

    public static Task createLogoutTask(UserMessageDto currentUser, Consumer<SuccessResponse> onSuccess, Consumer<ErrorMessageDto> onError) {
        return createTask(ClientApp.getRegistry().getUserService()::logout, new LogoutRequest(currentUser.getId()), onSuccess, onError);
    }

    public static Task createRegisterTask(String username, String password, String firstName, String lastName, Consumer<UserResponse> onSuccess, Consumer<ErrorMessageDto> onError) {
        return createTask(ClientApp.getRegistry().getUserService()::register, new RegistrationRequest(username, password, firstName, lastName), onSuccess, onError);
    }

    public static Task createChangeStatusTask(UserStatusMsgDto status, UserMessageDto currentUser, Consumer<SuccessResponse> onSuccess, Consumer<ErrorMessageDto> onError) {
        return createTask(ClientApp.getRegistry().getUserService()::changeStatus, new ChangeStatusRequest(status, currentUser.getUsername()), onSuccess, onError);
    }

    public static Task createFindFriendTask(String friendName, Consumer<UsersResponse> onSuccess, Consumer<ErrorMessageDto> onError) {
        return createTask(ClientApp.getRegistry().getUserService()::findFriend, new FindFriendRequest(friendName), onSuccess, onError);
    }
    //

    public static Task createLoadFriendRequestsTask(UserMessageDto currentUser, Consumer<FriendRequestsResponse> onSuccess, Consumer<ErrorMessageDto> onError) {
        return createTask(ClientApp.getRegistry().getUserService()::loadFriendRequests, new LoadFriendRequests(currentUser.getId()), onSuccess, onError);
    }

    public static Task createSendFriendRequestTask(Long userId, Long friendId, Consumer<FriendRequestResponse> onSuccess, Consumer<ErrorMessageDto> onError) {
        return createTask(ClientApp.getRegistry().getUserService()::sendFriendRequest, new SendFriendRequest(userId, friendId), onSuccess, onError);
    }

    // Chat service methods
    public static Task createAddFriendTask(Long chatId, Long userId, Long friendId, Consumer<SuccessResponse> onSuccess, Consumer<ErrorMessageDto> onError) {
        return createTask(ClientApp.getRegistry().getChatService()::addFriend, new AddFriendRequest(chatId, userId, friendId), onSuccess, onError);
    }

    public static Task createDownloadFileTask(ChatEventMessageDto event, Consumer<DownloadedFile> onSuccess, Consumer<ErrorMessageDto> onError) {
        return createTask(ClientApp.getRegistry().getChatService()::downloadFile, new DownloadFileRequest(event), onSuccess, onError);
    }

    public static Task createLeaveChatTask(Long chatId, Long userId, Consumer<SuccessResponse> onSuccess, Consumer<ErrorMessageDto> onError) {
        return createTask(ClientApp.getRegistry().getChatService()::leaveChat, new LeaveChatRequest(chatId, userId), onSuccess, onError);
    }

    public static Task createLoadLastTenEventsTask(ChatMessageDto chat, Consumer<ChatHistoryResponse> onSuccess, Consumer<ErrorMessageDto> onError) {
        return createTask(ClientApp.getRegistry().getChatService()::loadLastTenEvents, new LoadHistoryRequest(chat), onSuccess, onError);
    }
    //

    public static Task createSendFileTask(String filename, byte[] file, UserMessageDto sender, ChatMessageDto chat, Consumer<ChatEventResponse> onSuccess, Consumer<ErrorMessageDto> onError) {
        return createTask(ClientApp.getRegistry().getChatService()::sendFile, new SendFileRequest(filename, file, sender, chat), onSuccess, onError);
    }

    public static Task createSendMessageTask(String message, UserMessageDto sender, ChatMessageDto chat, Consumer<ChatEventResponse> onSuccess, Consumer<ErrorMessageDto> onError) {
        return createTask(ClientApp.getRegistry().getChatService()::sendMessage, new SendMessageRequest(message, sender, chat), onSuccess, onError);
    }

    public static Task createFindChatsTask(String chatName, Consumer<ChatsResponse> onSuccess, Consumer<ErrorMessageDto> onError) {
        return createTask(ClientApp.getRegistry().getChatService()::findChats, new FindChatRequest(chatName), onSuccess, onError);
    }

    public static Task createLoadChatsTask(UserMessageDto user, Consumer<ChatsResponse> onSuccess, Consumer<ErrorMessageDto> onError) {
        return createTask(ClientApp.getRegistry().getChatService()::loadChats, new LoadChatsRequest(user.getId()), onSuccess, onError);
    }

    public static Task createCreateChatTask(String name, Consumer<ChatResponse> onSuccess, Consumer<ErrorMessageDto> onError) {
        return createTask(ClientApp.getRegistry().getChatService()::createChat, new CreateChatRequest(name), onSuccess, onError);
    }

    private static <Req extends Request, Resp extends Response> Task createTask(
            BiConsumer< Req, ResponseListener<Resp>> doWork,
            Req request, Consumer<Resp> onSuccess, Consumer<ErrorMessageDto> onError) {
        return new Task(doWork, request, onSuccess, onError);
    }

}

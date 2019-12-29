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
public class TaskManager {

    private static final ExecutorService POOL = Executors.newFixedThreadPool(30);

    public static void login(String username, String password, Consumer<UserResponse> onSuccess, Consumer<ErrorMessageDto> onError) {
        executeTask(ClientApp.getRegistry().getUserService()::login, new LoginRequest(username, password), onSuccess, onError);
    }

    public static void logout(UserMessageDto currentUser, Consumer<SuccessResponse> onSuccess, Consumer<ErrorMessageDto> onError) {
        executeTask(ClientApp.getRegistry().getUserService()::logout, new LogoutRequest(currentUser.getId()), onSuccess, onError);
    }

    public static void register(String username, String password, String firstName, String lastName, Consumer<UserResponse> onSuccess, Consumer<ErrorMessageDto> onError) {
        executeTask(ClientApp.getRegistry().getUserService()::register, new RegistrationRequest(username, password, firstName, lastName), onSuccess, onError);
    }

    public static void changeStatus(UserStatusMsgDto status, UserMessageDto currentUser, Consumer<SuccessResponse> onSuccess, Consumer<ErrorMessageDto> onError) {
        executeTask(ClientApp.getRegistry().getUserService()::changeStatus, new ChangeStatusRequest(status, currentUser.getUsername()), onSuccess, onError);
    }

    public static void findFriend(String friendName, Consumer<UsersResponse> onSuccess, Consumer<ErrorMessageDto> onError) {
        executeTask(ClientApp.getRegistry().getUserService()::findFriend, new FindFriendRequest(friendName), onSuccess, onError);
    }
    //

    public static void loadFriendRequests(UserMessageDto currentUser, Consumer<FriendRequestsResponse> onSuccess, Consumer<ErrorMessageDto> onError) {
        executeTask(ClientApp.getRegistry().getUserService()::loadFriendRequests, new LoadFriendRequests(currentUser.getId()), onSuccess, onError);
    }

    public static void sendFriendRequest(Long userId, Long friendId, Consumer<FriendRequestResponse> onSuccess, Consumer<ErrorMessageDto> onError) {
        executeTask(ClientApp.getRegistry().getUserService()::sendFriendRequest, new SendFriendRequest(userId, friendId), onSuccess, onError);
    }

    // Chat service methods
    public static void addFriend(Long chatId, Long userId, Long friendId, Consumer<SuccessResponse> onSuccess, Consumer<ErrorMessageDto> onError) {
        executeTask(ClientApp.getRegistry().getChatService()::addFriend, new AddFriendRequest(chatId, userId, friendId), onSuccess, onError);
    }

    public static void downloadFile(ChatEventMessageDto event, Consumer<DownloadedFile> onSuccess, Consumer<ErrorMessageDto> onError) {
        executeTask(ClientApp.getRegistry().getChatService()::downloadFile, new DownloadFileRequest(event), onSuccess, onError);
    }

    public static void leaveChat(Long chatId, Long userId, Consumer<SuccessResponse> onSuccess, Consumer<ErrorMessageDto> onError) {
        executeTask(ClientApp.getRegistry().getChatService()::leaveChat, new LeaveChatRequest(chatId, userId), onSuccess, onError);
    }

    public static void loadLastTenEvents(ChatMessageDto chat, Consumer<ChatHistoryResponse> onSuccess, Consumer<ErrorMessageDto> onError) {
        executeTask(ClientApp.getRegistry().getChatService()::loadLastTenEvents, new LoadHistoryRequest(chat), onSuccess, onError);
    }
    //

    public static void sendFile(String filename, byte[] file, UserMessageDto sender, ChatMessageDto chat, Consumer<ChatEventResponse> onSuccess, Consumer<ErrorMessageDto> onError) {
        executeTask(ClientApp.getRegistry().getChatService()::sendFile, new SendFileRequest(filename, file, sender, chat), onSuccess, onError);
    }

    public static void sendMessage(String message, UserMessageDto sender, ChatMessageDto chat, Consumer<ChatEventResponse> onSuccess, Consumer<ErrorMessageDto> onError) {
        executeTask(ClientApp.getRegistry().getChatService()::sendMessage, new SendMessageRequest(message, sender, chat), onSuccess, onError);
    }

    public static void findChats(String chatName, Consumer<ChatsResponse> onSuccess, Consumer<ErrorMessageDto> onError) {
        executeTask(ClientApp.getRegistry().getChatService()::findChats, new FindChatRequest(chatName), onSuccess, onError);
    }

    public static void loadChats(UserMessageDto user, Consumer<ChatsResponse> onSuccess, Consumer<ErrorMessageDto> onError) {
        executeTask(ClientApp.getRegistry().getChatService()::loadChats, new LoadChatsRequest(user.getId()), onSuccess, onError);
    }

    public static void createChat(String name, Consumer<ChatResponse> onSuccess, Consumer<ErrorMessageDto> onError) {
        executeTask(ClientApp.getRegistry().getChatService()::createChat, new CreateChatRequest(name), onSuccess, onError);
    }

    private static <Req extends Request, Resp extends Response> void executeTask(
            BiConsumer< Req, ResponseListener<Resp>> doWork,
            Req request, Consumer<Resp> onSuccess, Consumer<ErrorMessageDto> onError) {
        POOL.execute(new TaskWorker(doWork, request, createListener(onSuccess, onError)));
    }

    private static <T> ResponseListener<T> createListener(Consumer<T> onSuccess, Consumer<ErrorMessageDto> onError) {
        return new ResponseListener<T>() {
            @Override
            public void onSuccess(T rsp) {
                Platform.runLater(() -> {
                    onSuccess.accept(rsp);
                });
            }

            @Override
            public void onError(ErrorMessageDto error) {
                Platform.runLater(() -> {
                    onError.accept(error);
                });
            }
        };
    }
}

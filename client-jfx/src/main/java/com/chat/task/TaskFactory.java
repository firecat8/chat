package com.chat.task;

import com.chat.app.ClientApp;
import com.chat.messaging.vo.ChatEventVo;
import com.chat.messaging.vo.ChatVo;
import com.chat.messaging.vo.DownloadedFile;
import com.chat.messaging.vo.ErrorVo;
import com.chat.messaging.vo.UserVo;
import com.chat.messaging.vo.UserStatusVo;
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
import com.chat.messaging.message.user.FriendRequestStatusRequest;
import com.chat.messaging.message.user.FriendRequestsResponse;
import com.chat.messaging.message.user.LoadFriendRequests;
import com.chat.messaging.message.user.LoginRequest;
import com.chat.messaging.message.user.LogoutRequest;
import com.chat.messaging.message.user.RegistrationRequest;
import com.chat.messaging.message.user.SendFriendRequest;
import com.chat.messaging.message.user.UserResponse;
import com.chat.messaging.message.user.UsersResponse;
import com.chat.messaging.vo.FriendRequestVo;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 *
 * @author gdimitrova
 */
public class TaskFactory {

    public static Task createLoginTask(String username, String password, Consumer<UserResponse> onSuccess, Consumer<ErrorVo> onError) {
        return createTask(ClientApp.getRegistry().getUserService()::login, new LoginRequest(username, password), onSuccess, onError);
    }

    public static Task createLogoutTask(UserVo currentUser, Consumer<SuccessResponse> onSuccess, Consumer<ErrorVo> onError) {
        return createTask(ClientApp.getRegistry().getUserService()::logout, new LogoutRequest(currentUser.getId()), onSuccess, onError);
    }

    public static Task createRegisterTask(String username, String password, String firstName, String lastName, Consumer<UserResponse> onSuccess, Consumer<ErrorVo> onError) {
        return createTask(ClientApp.getRegistry().getUserService()::register, new RegistrationRequest(username, password, firstName, lastName), onSuccess, onError);
    }

    public static Task createChangeStatusTask(UserStatusVo status, UserVo currentUser, Consumer<SuccessResponse> onSuccess, Consumer<ErrorVo> onError) {
        return createTask(ClientApp.getRegistry().getUserService()::changeStatus, new ChangeStatusRequest(status, currentUser.getUsername()), onSuccess, onError);
    }

    public static Task createFindFriendTask(String friendName, Long searcherId, Consumer<UsersResponse> onSuccess, Consumer<ErrorVo> onError) {
        return createTask(ClientApp.getRegistry().getUserService()::findFriend, new FindFriendRequest(friendName, searcherId), onSuccess, onError);
    }

    public static Task createSendFriendRequestTask(Long userId, Long friendId, Consumer<FriendRequestResponse> onSuccess, Consumer<ErrorVo> onError) {
        return createTask(ClientApp.getRegistry().getUserService()::sendFriendRequest, new SendFriendRequest(userId, friendId), onSuccess, onError);
    }

    public static Task createLoadFriendRequestsTask(UserVo currentUser, Consumer<FriendRequestsResponse> onSuccess, Consumer<ErrorVo> onError) {
        return createTask(ClientApp.getRegistry().getUserService()::loadFriendRequests, new LoadFriendRequests(currentUser.getId()), onSuccess, onError);
    }

    public static Task createAcceptFriendRequestTask(FriendRequestVo friendRequest, Consumer<ChatResponse> onSuccess, Consumer<ErrorVo> onError) {
        return createTask(ClientApp.getRegistry().getUserService()::acceptFriendRequest, new FriendRequestStatusRequest(friendRequest), onSuccess, onError);
    }

    public static Task createDeclineFriendRequestTask(FriendRequestVo friendRequest, Consumer<FriendRequestResponse> onSuccess, Consumer<ErrorVo> onError) {
        return createTask(ClientApp.getRegistry().getUserService()::declineFriendRequest, new FriendRequestStatusRequest(friendRequest), onSuccess, onError);
    }

    // Chat service methods
    public static Task createAddFriendTask(Long chatId, Long userId, Long friendId, Consumer<SuccessResponse> onSuccess, Consumer<ErrorVo> onError) {
        return createTask(ClientApp.getRegistry().getChatService()::addFriend, new AddFriendRequest(chatId, userId, friendId), onSuccess, onError);
    }

    public static Task createDownloadFileTask(ChatEventVo event, Consumer<DownloadedFile> onSuccess, Consumer<ErrorVo> onError) {
        return createTask(ClientApp.getRegistry().getChatService()::downloadFile, new DownloadFileRequest(event), onSuccess, onError);
    }

    public static Task createLeaveChatTask(Long chatId, Long userId, Consumer<SuccessResponse> onSuccess, Consumer<ErrorVo> onError) {
        return createTask(ClientApp.getRegistry().getChatService()::leaveChat, new LeaveChatRequest(chatId, userId), onSuccess, onError);
    }

    public static Task createLoadLastTenEventsTask(ChatVo chat, Consumer<ChatHistoryResponse> onSuccess, Consumer<ErrorVo> onError) {
        return createTask(ClientApp.getRegistry().getChatService()::loadLastTenEvents, new LoadHistoryRequest(chat), onSuccess, onError);
    }
    //

    public static Task createSendFileTask(String filename, byte[] file, UserVo sender, ChatVo chat, Consumer<ChatEventResponse> onSuccess, Consumer<ErrorVo> onError) {
        return createTask(ClientApp.getRegistry().getChatService()::sendFile, new SendFileRequest(filename, file, sender.getId(), chat.getId()), onSuccess, onError);
    }

    public static Task createSendMessageTask(String message, UserVo sender, ChatVo chat, Consumer<ChatEventResponse> onSuccess, Consumer<ErrorVo> onError) {
        return createTask(ClientApp.getRegistry().getChatService()::sendMessage, new SendMessageRequest(message, sender.getId(), chat.getId()), onSuccess, onError);
    }

    public static Task createFindChatsTask(String chatName, Consumer<ChatsResponse> onSuccess, Consumer<ErrorVo> onError) {
        return createTask(ClientApp.getRegistry().getChatService()::findChats, new FindChatRequest(chatName), onSuccess, onError);
    }

    public static Task createLoadChatsTask(UserVo user, Consumer<ChatsResponse> onSuccess, Consumer<ErrorVo> onError) {
        return createTask(ClientApp.getRegistry().getChatService()::loadChats, new LoadChatsRequest(user.getId()), onSuccess, onError);
    }

    public static Task createCreateGroupChatTask(String name, UserVo owner, Consumer<ChatResponse> onSuccess, Consumer<ErrorVo> onError) {
        return createTask(ClientApp.getRegistry().getChatService()::createChat, new CreateChatRequest(name, owner), onSuccess, onError);
    }

    private static <Req extends Request, Resp extends Response> Task createTask(
            BiConsumer< Req, ResponseListener<Resp>> doWork,
            Req request, Consumer<Resp> onSuccess, Consumer<ErrorVo> onError) {
        return new Task(doWork, request, onSuccess, onError);
    }

}

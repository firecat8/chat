package com.chat.messaging.services;

import com.chat.messaging.message.user.RegistrationRequest;
import com.chat.messaging.message.ResponseListener;
import com.chat.messaging.message.SuccessResponse;
import com.chat.messaging.message.chat.ChatResponse;
import com.chat.messaging.message.user.ChangeStatusRequest;
import com.chat.messaging.message.user.FindFriendRequest;
import com.chat.messaging.message.user.FriendRequestResponse;
import com.chat.messaging.message.user.FriendRequestStatusRequest;
import com.chat.messaging.message.user.LoadFriendRequests;
import com.chat.messaging.message.user.FriendRequestsResponse;
import com.chat.messaging.message.user.SendFriendRequest;
import com.chat.messaging.message.user.LoginRequest;
import com.chat.messaging.message.user.LogoutRequest;
import com.chat.messaging.message.user.UserResponse;
import com.chat.messaging.message.user.UsersResponse;

/**
 *
 * @author gdimitrova
 */
public interface UserService {

    public void login(LoginRequest req, ResponseListener<UserResponse> listener);

    public void logout(LogoutRequest req, ResponseListener<SuccessResponse> listener);

    public void changeStatus(ChangeStatusRequest req, ResponseListener<SuccessResponse> listener);

    public void register(RegistrationRequest request, ResponseListener<UserResponse> listener);

    public void findFriend(FindFriendRequest req, ResponseListener<UsersResponse> listener);

    public void sendFriendRequest(SendFriendRequest request, ResponseListener<FriendRequestResponse> listener);

    public void loadFriendRequests(LoadFriendRequests request, ResponseListener<FriendRequestsResponse> listener);

    public void loadReceiverRequests(LoadFriendRequests request, ResponseListener<FriendRequestsResponse> listener);

    public void loadSenderRequests(LoadFriendRequests request, ResponseListener<FriendRequestsResponse> listener);

    public void acceptFriendRequest(FriendRequestStatusRequest request, ResponseListener<ChatResponse> listener);

    public void declineFriendRequest(FriendRequestStatusRequest request, ResponseListener<FriendRequestResponse> listener);
}

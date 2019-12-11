package com.chat.messaging.services;

import com.chat.messaging.dto.FriendRequestMessageDto;
import com.chat.messaging.message.user.RegistrationRequest;
import com.chat.messaging.message.ResponseListener;
import com.chat.messaging.dto.UserMessageDto;
import com.chat.messaging.message.user.ChangeStatusRequest;
import com.chat.messaging.message.user.FriendRequestStatusRequest;
import com.chat.messaging.message.user.LoadFriendRequests;
import com.chat.messaging.message.user.LoadedFriendRequests;
import com.chat.messaging.message.user.SendFriendRequest;
import com.chat.messaging.message.user.LoginRequest;
import com.chat.messaging.message.user.LogoutRequest;

/**
 *
 * @author gdimitrova
 */
public interface UserService {

    public void login(LoginRequest req, ResponseListener<UserMessageDto> listener);

    public void logout(LogoutRequest req, ResponseListener<UserMessageDto> listener);

    public void changeStatus(ChangeStatusRequest req, ResponseListener<UserMessageDto> listener);

    public void register(RegistrationRequest request, ResponseListener<UserMessageDto> listener);

    public void sendFriendRequest(SendFriendRequest request, ResponseListener<FriendRequestMessageDto> listener);

    public void loadFriendRequests(LoadFriendRequests request, ResponseListener<LoadedFriendRequests> listener);

    public void loadReceiverRequests(LoadFriendRequests request, ResponseListener<LoadedFriendRequests> listener);

    public void loadSenderRequests(LoadFriendRequests request, ResponseListener<LoadedFriendRequests> listener);

    public void acceptFriendRequest(FriendRequestStatusRequest request, ResponseListener<FriendRequestMessageDto> listener);

    public void declineFriendRequest(FriendRequestStatusRequest request, ResponseListener<FriendRequestMessageDto> listener);
}

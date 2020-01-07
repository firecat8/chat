package com.chat.bl.service.dao;

import com.chat.bl.service.messaging.exchanger.ChatVoExchanger;
import com.chat.bl.service.messaging.exchanger.FriendRequestVoExchanger;
import com.chat.bl.service.messaging.exchanger.UserVoExchanger;
import com.chat.dao.DaoRegistry;
import com.chat.domain.Chat;
import com.chat.domain.ChatType;
import com.chat.domain.ChatUser;
import com.chat.domain.FriendRequest;
import com.chat.domain.FriendRequestStatus;
import com.chat.domain.Participant;
import com.chat.domain.User;
import com.chat.domain.UserInfo;
import com.chat.domain.UserStatus;
import com.chat.messaging.vo.FriendRequestVo;
import com.chat.messaging.vo.UserVo;
import com.chat.messaging.message.ResponseListener;
import com.chat.messaging.message.SuccessResponse;
import com.chat.messaging.message.chat.ChatResponse;
import com.chat.messaging.message.user.ChangeStatusRequest;
import com.chat.messaging.message.user.FindFriendRequest;
import com.chat.messaging.message.user.FriendRequestResponse;
import com.chat.messaging.message.user.FriendRequestStatusRequest;
import com.chat.messaging.message.user.LoadFriendRequests;
import com.chat.messaging.message.user.FriendRequestsResponse;
import com.chat.messaging.message.user.RegistrationRequest;
import com.chat.messaging.message.user.SendFriendRequest;
import com.chat.messaging.message.user.LoginRequest;
import com.chat.messaging.message.user.LogoutRequest;
import com.chat.messaging.message.user.UserResponse;
import com.chat.messaging.message.user.UsersResponse;
import com.chat.messaging.services.UserService;
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author gdimitrova
 */
public class UserServiceImpl extends AbstractTransactionalService implements UserService {

    public UserServiceImpl(DaoRegistry daoRegistry) {
        super(daoRegistry);
    }

    @Override
    public void login(LoginRequest req, ResponseListener<UserResponse> listener) {
        doInTransaction((DaoRegistry registry) -> {
            String username = req.getUsername();
            User user = registry.getUserDao().find(username);
            if (user == null) {
                throw new MessageException("Not exist user with username: " + username);
            }
            if (!user.getPassword().equals(req.getPassword())) {
                throw new MessageException("Not valid password!");
            }
            registry.getUserDao().updateStatus(user, UserStatus.ACTIVE);
            user.setStatus(UserStatus.ACTIVE);
            return new UserResponse(exchange(user));
        }, listener);
    }

    @Override
    public void logout(LogoutRequest req, ResponseListener<SuccessResponse> listener) {
        doInTransaction((DaoRegistry registry) -> {
            User user = registry.getUserDao().loadById(req.getId());
            registry.getUserDao().updateStatus(user, UserStatus.OFFLINE);
            return new SuccessResponse();
        }, listener);
    }

    @Override
    public void changeStatus(ChangeStatusRequest req, ResponseListener<SuccessResponse> listener) {
        doInTransaction((DaoRegistry registry) -> {
            User user = registry.getUserDao().find(req.getUsername());
            registry.getUserDao().updateStatus(user, UserStatus.valueOf(req.getStatus().name()));
            return new SuccessResponse();
        }, listener);
    }

    @Override
    public synchronized void register(RegistrationRequest req, ResponseListener<UserResponse> listener) {
        doInTransaction((DaoRegistry registry) -> {
            String username = req.getUsername();
            User user = registry.getUserDao().find(username);
            if (user != null) {
                throw new MessageException("Already exist user with username: " + username);
            }
            UserInfo userInfo = registry.getUserInfoDao().save(new User(username, req.getPassword(), UserStatus.ACTIVE, Calendar.getInstance().getTimeInMillis()),
                    req.getFirstname(), req.getLastname());
            return new UserResponse(exchange(userInfo.getUser()));
        }, listener);
    }

    @Override
    public void findFriend(FindFriendRequest req, ResponseListener<UsersResponse> listener) {
        doInTransaction((DaoRegistry registry) -> {
            return new UsersResponse(exchangeUserList(registry.getUserDao().findUsers(req.getFriendName(), req.getSearcherId())));
        }, listener);
    }

    @Override
    public void sendFriendRequest(SendFriendRequest req, ResponseListener<FriendRequestResponse> listener) {
        doInTransaction((DaoRegistry registry) -> {
            User sender = registry.getUserDao().loadById(req.getSenderId());
            User receiver = registry.getUserDao().loadById(req.getReceiverId());
            FriendRequest friendRequest = registry.getFriendRequestDao().find(sender, receiver);
            if (friendRequest == null) {
                friendRequest = new FriendRequest(sender, receiver, FriendRequestStatus.NEW);
            } else {
                friendRequest.setRequestStatus(FriendRequestStatus.NEW);
            }
            registry.getFriendRequestDao().save(friendRequest);
            return new FriendRequestResponse(exchange(friendRequest));
        }, listener);
    }

    @Override
    public void loadReceiverRequests(LoadFriendRequests req, ResponseListener<FriendRequestsResponse> listener) {
        doInTransaction((DaoRegistry registry) -> {
            User user = registry.getUserDao().loadById(req.getId());
            List<FriendRequest> loaded = registry.getFriendRequestDao().loadReceiverRequests(user);
            return new FriendRequestsResponse(exchangeList(loaded));
        }, listener);
    }

    @Override
    public void loadSenderRequests(LoadFriendRequests req, ResponseListener<FriendRequestsResponse> listener) {
        doInTransaction((DaoRegistry registry) -> {
            User user = registry.getUserDao().loadById(req.getId());
            List<FriendRequest> loaded = registry.getFriendRequestDao().loadSenderRequests(user);
            return new FriendRequestsResponse(exchangeList(loaded));
        }, listener);
    }

    @Override
    public void loadFriendRequests(LoadFriendRequests req, ResponseListener<FriendRequestsResponse> listener) {
        doInTransaction((DaoRegistry registry) -> {
            User user = registry.getUserDao().loadById(req.getId());
            List<FriendRequest> loaded = registry.getFriendRequestDao().loadAllRequests(user);
            return new FriendRequestsResponse(exchangeList(loaded));
        }, listener);
    }

    @Override
    public void acceptFriendRequest(FriendRequestStatusRequest req, ResponseListener<ChatResponse> listener) {
        doInTransaction((DaoRegistry registry) -> {
            FriendRequest friendRequest = FriendRequestVoExchanger.INSTANCE.exchange(req.getFriendRequest());
            User sender = registry.getUserDao().loadById(friendRequest.getSender().getId());
            User receiver = registry.getUserDao().loadById(friendRequest.getReceiver().getId());
            sender.addFriend(receiver.getId());
            receiver.addFriend(sender.getId());
            registry.getUserDao().save(sender);
            registry.getUserDao().save(receiver);
            Chat chat = new Chat("", ChatType.PRIVATE);
            chat.addParticipant(new Participant(sender, ChatUser.PARTICIPANT, chat));
            chat.addParticipant(new Participant(receiver, ChatUser.PARTICIPANT, chat));
            registry.getChatDao().save(chat);
            registry.getFriendRequestDao().delete(friendRequest.getId());
            return new ChatResponse(ChatVoExchanger.INSTANCE.exchange(chat));
        }, listener);
    }

    @Override
    public void declineFriendRequest(FriendRequestStatusRequest req, ResponseListener<FriendRequestResponse> listener) {
        doInTransaction((DaoRegistry registry) -> {
            FriendRequest friendRequest = FriendRequestVoExchanger.INSTANCE.exchange(req.getFriendRequest());
            friendRequest.setRequestStatus(FriendRequestStatus.DECLINED);
            registry.getFriendRequestDao().save(friendRequest);
            return new FriendRequestResponse(exchange(friendRequest));
        }, listener);
    }

    private User exchange(UserVo user) {
        return UserVoExchanger.INSTANCE.exchange(user);
    }

    private UserVo exchange(User user) {
        return UserVoExchanger.INSTANCE.exchange(user);
    }

    private List<UserVo> exchangeUserList(List<User> list) {
        return UserVoExchanger.INSTANCE.exchangeEntityList(list);
    }

    private FriendRequestVo exchange(FriendRequest friendRequest) {
        return FriendRequestVoExchanger.INSTANCE.exchange(friendRequest);
    }

    private List<FriendRequestVo> exchangeList(List<FriendRequest> list) {
        return FriendRequestVoExchanger.INSTANCE.exchangeEntityList(list);
    }
}

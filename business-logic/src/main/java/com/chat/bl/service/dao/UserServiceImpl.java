package com.chat.bl.service.dao;

import com.chat.bl.service.messaging.exchanger.FriendRequestMsgDtoExchanger;
import com.chat.bl.service.messaging.exchanger.UserMsgDtoExchanger;
import com.chat.dao.DaoRegistry;
import com.chat.domain.FriendRequest;
import com.chat.domain.FriendRequestStatus;
import com.chat.domain.User;
import com.chat.domain.UserInfo;
import com.chat.domain.UserStatus;
import com.chat.messaging.dto.FriendRequestMessageDto;
import com.chat.messaging.dto.UserMessageDto;
import com.chat.messaging.message.ResponseListener;
import com.chat.messaging.message.SuccessResponse;
import com.chat.messaging.message.user.ChangeStatusRequest;
import com.chat.messaging.message.user.FriendRequestResponse;
import com.chat.messaging.message.user.FriendRequestStatusRequest;
import com.chat.messaging.message.user.LoadFriendRequests;
import com.chat.messaging.message.user.FriendRequestsResponse;
import com.chat.messaging.message.user.RegistrationRequest;
import com.chat.messaging.message.user.SendFriendRequest;
import com.chat.messaging.message.user.LoginRequest;
import com.chat.messaging.message.user.LogoutRequest;
import com.chat.messaging.message.user.UserResponse;
import com.chat.messaging.services.UserService;
import java.util.Calendar;
import java.util.List;
import java.util.function.Function;

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
            return new UpdateStatusFunction(user.getUsername(), user.getPassword(), UserStatus.INACTIVE).apply(registry);
        }, listener);
    }

    @Override
    public void changeStatus(ChangeStatusRequest req, ResponseListener<SuccessResponse> listener) {
        doInTransaction((DaoRegistry registry) -> {
            User user = registry.getUserDao().find(req.getUsername());
            return new UpdateStatusFunction(user.getUsername(), user.getPassword(),
                    UserStatus.valueOf(req.getStatus().name())).apply(registry);
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
            UserInfo userInfo = registry.getUserInfoDao().save(new User(username, req.getPassword(), UserStatus.INACTIVE, Calendar.getInstance().getTimeInMillis()),
                    req.getFirstname(), req.getLastname());
            return new UserResponse(exchange(userInfo.getUser()));
        }, listener);
    }

    @Override
    public void sendFriendRequest(SendFriendRequest req, ResponseListener<FriendRequestResponse> listener) {
        doInTransaction((DaoRegistry registry) -> {
            User sender = registry.getUserDao().loadById(req.getSenderId());
            User receiver = registry.getUserDao().loadById(req.getReceiverId());
            FriendRequest friendRequest = registry.getFriendRequestDao().find(sender, receiver);
            if (friendRequest == null) {
                registry.getFriendRequestDao().save(new FriendRequest(sender, receiver, FriendRequestStatus.NEW));
            }
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
    public void acceptFriendRequest(FriendRequestStatusRequest req, ResponseListener<FriendRequestResponse> listener) {
        doInTransaction((DaoRegistry registry) -> {
            FriendRequest friendRequest = FriendRequestMsgDtoExchanger.INSTANCE.exchange(req.getFriendRequest());
            User sender = registry.getUserDao().loadById(friendRequest.getSender().getId());
            User receiver = registry.getUserDao().loadById(friendRequest.getReceiver().getId());
            sender.getFriends().add(receiver);
            receiver.getFriends().add(sender);
            registry.getUserDao().save(sender);
            registry.getUserDao().save(receiver);
            registry.getFriendRequestDao().delete(friendRequest.getId());
            return new FriendRequestResponse(exchange(friendRequest));
        }, listener);
    }

    @Override
    public void declineFriendRequest(FriendRequestStatusRequest req, ResponseListener<FriendRequestResponse> listener) {
        doInTransaction((DaoRegistry registry) -> {
            FriendRequest friendRequest = FriendRequestMsgDtoExchanger.INSTANCE.exchange(req.getFriendRequest());
            friendRequest.setRequestStatus(FriendRequestStatus.DECLINED);
            registry.getFriendRequestDao().save(friendRequest);
            return new FriendRequestResponse(exchange(friendRequest));
        }, listener);
    }

    private static class UpdateStatusFunction implements Function<DaoRegistry, SuccessResponse> {

        private final String username;

        private final String password;

        private final UserStatus newStatus;

        public UpdateStatusFunction(String username, String password, UserStatus newStatus) {
            this.username = username;
            this.password = password;
            this.newStatus = newStatus;
        }

        @Override
        public SuccessResponse apply(DaoRegistry daoReg) {
            User user = daoReg.getUserDao().find(username);
            if (user == null) {
                throw new MessageException("Not exist user with username: " + username);
            }
            if (!user.getPassword().equals(password)) {
                throw new MessageException("Not valid password!");
            }
            daoReg.getUserDao().updateStatus(user, newStatus);
            return new SuccessResponse();
        }

    }

    private User exchange(UserMessageDto user) {
        return UserMsgDtoExchanger.INSTANCE.exchange(user);
    }

    private UserMessageDto exchange(User user) {
        return UserMsgDtoExchanger.INSTANCE.exchange(user);
    }

    private FriendRequestMessageDto exchange(FriendRequest friendRequest) {
        return FriendRequestMsgDtoExchanger.INSTANCE.exchange(friendRequest);
    }

    private List<FriendRequestMessageDto> exchangeList(List<FriendRequest> list) {
        return FriendRequestMsgDtoExchanger.INSTANCE.exchangeEntityList(list);
    }
}

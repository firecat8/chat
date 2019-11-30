package com.chat.bl.service.dao;

import com.chat.bl.service.messaging.exchanger.UserMsgDtoExchanger;
import com.chat.dao.DaoRegistry;
import com.chat.domain.User;
import com.chat.domain.UserInfo;
import com.chat.domain.UserStatus;
import com.chat.messaging.dto.UserMessageDto;
import com.chat.messaging.message.ResponseListener;
import com.chat.messaging.message.user.ChangeStatusRequest;
import com.chat.messaging.message.user.RegistrationRequest;
import com.chat.messaging.message.user.UserRequest;
import com.chat.messaging.services.UserService;
import java.util.Calendar;
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
    public void login(UserRequest req, ResponseListener<UserMessageDto> listener) {
        changeStatus(req.getUsername(), req.getPassword(), UserStatus.ACTIVE, listener);
    }

    @Override
    public void logout(UserRequest req, ResponseListener<UserMessageDto> listener) {
        changeStatus(req.getUsername(), req.getPassword(), UserStatus.INACTIVE, listener);
    }

    @Override
    public void changeStatus(ChangeStatusRequest req, ResponseListener<UserMessageDto> listener) {
        changeStatus(req.getUsername(), req.getPassword(), UserStatus.valueOf(req.getStatus().name()), listener);
    }

    @Override
    public synchronized void register(RegistrationRequest req, ResponseListener<UserMessageDto> listener) {
        doInTransaction((DaoRegistry registry) -> {
            String username = req.getUsername();
            User user = registry.getUserDao().find(username);
            if (user != null) {
                throw new MessageException("Already exist user with username: " + username);
            }
            UserInfo userInfo = registry.getUserInfoDao().save(new User(username, req.getPassword(), UserStatus.INACTIVE, Calendar.getInstance().getTimeInMillis()),
                    req.getFirstname(), req.getLastname(), req.getEmail(), req.getPhone(), req.getCity());
            return UserMsgDtoExchanger.INSTANCE.exchange(userInfo.getUser());
        }, listener);
    }

    public synchronized void changeStatus(String username, String password, UserStatus newStatus, ResponseListener<UserMessageDto> listener) {
        doInTransaction(new UpdateStatusFunction(username, password, newStatus), listener);
    }

    private static class UpdateStatusFunction implements Function<DaoRegistry, UserMessageDto> {

        private final String username;

        private final String password;

        private final UserStatus newStatus;

        public UpdateStatusFunction(String username, String password, UserStatus newStatus) {
            this.username = username;
            this.password = password;
            this.newStatus = newStatus;
        }

        @Override
        public UserMessageDto apply(DaoRegistry daoReg) {
            User user = daoReg.getUserDao().find(username);
            if (user == null) {
                throw new MessageException("Not exist user with username: " + username);
            }
            if (!user.getPassword().equals(password)) {
                throw new MessageException("Not valid password!");
            }
            daoReg.getUserDao().updateStatus(user, newStatus);
            return UserMsgDtoExchanger.INSTANCE.exchange(user);
        }

    }
}

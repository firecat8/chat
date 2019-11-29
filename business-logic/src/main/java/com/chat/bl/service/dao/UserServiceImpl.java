package com.chat.bl.service.dao;

import com.chat.bl.service.messaging.exchanger.UserMsgDtoExchanger;
import com.chat.dao.DaoRegistry;
import com.chat.domain.User;
import com.chat.domain.UserInfo;
import com.chat.domain.UserStatus;
import com.chat.messaging.dto.UserMessageDto;
import com.chat.messaging.message.ResponseListener;
import com.chat.messaging.message.user.RegistrationRequest;
import com.chat.messaging.message.user.UserRequest;
import com.chat.messaging.services.UserService;
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
        doInTransaction(new UpdateStatusFunction(req, UserStatus.ACTIVE), listener);
    }

    @Override
    public void logout(UserRequest req, ResponseListener<UserMessageDto> listener) {
        doInTransaction(new UpdateStatusFunction(req, UserStatus.INACTIVE), listener);
    }

    @Override
    public void register(RegistrationRequest req, ResponseListener<UserMessageDto> listener) {
        doInTransaction((DaoRegistry registry) -> {
            String username = req.getUsername();
            User user = registry.getUserDao().find(username);
            if (user != null) {
                throw new MessageException("Already exist user with username: " + username);
            }
            UserInfo userInfo = registry.getUserInfoDao().save(username, req.getPassword(), req.getFirstname(), req.getLastname(), req.getEmail(), req.getPhone(), req.getCity());
            return UserMsgDtoExchanger.INSTANCE.exchange(userInfo.getUser());
        }, listener);
    }

    private static class UpdateStatusFunction implements Function<DaoRegistry, UserMessageDto> {

        private final UserRequest req;

        private final UserStatus newStatus;

        public UpdateStatusFunction(UserRequest req, UserStatus newStatus) {
            this.req = req;
            this.newStatus = newStatus;
        }

        @Override
        public UserMessageDto apply(DaoRegistry daoReg) {
            String username = req.getUsername();
            User user = daoReg.getUserDao().find(username);
            if (user == null) {
                throw new MessageException("Not exist user with username: " + username);
            }
            if (!user.getPassword().equals(req.getPassword())) {
                throw new MessageException("Not valid password!");
            }
            daoReg.getUserDao().updateStatus(user, newStatus);
            return UserMsgDtoExchanger.INSTANCE.exchange(user);
        }

    }
}

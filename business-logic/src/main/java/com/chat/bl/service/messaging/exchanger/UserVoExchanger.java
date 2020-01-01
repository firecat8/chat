package com.chat.bl.service.messaging.exchanger;

import com.chat.domain.User;
import com.chat.domain.UserStatus;
import com.chat.messaging.vo.UserVo;
import com.chat.messaging.vo.UserStatusVo;
import java.util.ArrayList;

/**
 *
 * @author gdimitrova
 */
public class UserVoExchanger extends VoEntityExchanger<UserVo, User> {

    public final static UserVoExchanger INSTANCE = new UserVoExchanger();

    private UserVoExchanger() {
    }

    @Override
    public User exchangeFrom(UserVo dto) {
        User user = new User(dto.getUsername(), dto.getPassword(), UserStatus.valueOf(dto.getStatus().name()), dto.getStatusTime());
        user.getFriends().addAll(exchangeDtoList(new ArrayList<>(dto.getFriends())));
        return user;
    }

    @Override
    public UserVo exchangeFrom(User entity) {
        UserVo user = new UserVo(entity.getUsername(), entity.getPassword(),
                UserStatusVo.valueOf(entity.getStatus().name()), entity.getStatusTime());
        user.getFriends().addAll(exchangeEntityList(new ArrayList<>(entity.getFriends())));
        return user;
    }

}

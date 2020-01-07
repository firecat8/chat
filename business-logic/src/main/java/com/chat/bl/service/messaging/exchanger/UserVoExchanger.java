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
    public User exchangeFrom(UserVo vo) {
        User user = exchangeFromUser(vo);
        vo.getFriends().forEach((friend) -> {
            user.addFriend(friend);
        });
        return user;
    }

    @Override
    public UserVo exchangeFrom(User entity) {
        UserVo user = exchangeFromUser(entity);
        entity.getFriends().forEach((friend) -> {
            user.addFriend(friend);
        });
        return user;
    }

    private UserVo exchangeFromUser(User entity) {
        return new UserVo(entity.getUsername(), entity.getPassword(),
                UserStatusVo.valueOf(entity.getStatus().name()), entity.getStatusTime());
    }

    private User exchangeFromUser(UserVo vo) {
        return new User(vo.getUsername(), vo.getPassword(), UserStatus.valueOf(vo.getStatus().name()), vo.getStatusTime());
    }
}

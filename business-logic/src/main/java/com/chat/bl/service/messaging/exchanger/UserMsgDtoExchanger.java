package com.chat.bl.service.messaging.exchanger;

import com.chat.domain.User;
import com.chat.domain.UserStatus;
import com.chat.messaging.dto.UserMessageDto;
import com.chat.messaging.dto.UserStatusMsgDto;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author gdimitrova
 */
public class UserMsgDtoExchanger extends MessageDtoEntityExchanger<UserMessageDto, User> {

    public final static UserMsgDtoExchanger INSTANCE = new UserMsgDtoExchanger();

    private UserMsgDtoExchanger() {
    }

    @Override
    public User exchangeFrom(UserMessageDto dto) {
        User user = new User(dto.getUsername(), dto.getPassword(), UserStatus.valueOf(dto.getStatus().name()));
        List<User> friends = new ArrayList<>();
        dto.getFriends().forEach((friend) -> {
            friends.add(exchangeFrom(friend));
        });
        user.getFriends().addAll(friends);
        return user;
    }

    @Override
    public UserMessageDto exchangeFrom(User entity) {
        UserMessageDto user = new UserMessageDto(entity.getUsername(), entity.getPassword(), UserStatusMsgDto.valueOf(entity.getStatus().name()));
        List<UserMessageDto> friends = new ArrayList<>();
        entity.getFriends().forEach((friend) -> {
            friends.add(exchangeFrom(friend));
        });
        user.getFriends().addAll(friends);
        return user;
    }

}

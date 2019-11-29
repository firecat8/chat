package com.chat.persistence.exchanger;

import com.chat.domain.User;
import com.chat.domain.UserStatus;
import com.chat.persistence.dto.UserStatusDto;
import com.chat.persistence.dto.UserDto;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author gdimitrova
 */
public class UserDtoExchanger extends DtoEntityExchanger<UserDto, User> {

    public final static UserDtoExchanger INSTANCE = new UserDtoExchanger();

    private UserDtoExchanger() {
    }

    @Override
    public User exchangeFrom(UserDto dto) {
        User user = new User(dto.getUsername(), dto.getPassword(), UserStatus.valueOf(dto.getStatus().name()));
        List<User> friends = new ArrayList<>();
        dto.getFriends().forEach((friend) -> {
            friends.add(exchangeFrom(friend));
        });
        user.getFriends().addAll(friends);
        return user;
    }

    @Override
    public UserDto exchangeFrom(User entity) {
        UserDto user = new UserDto(entity.getUsername(), entity.getPassword(), UserStatusDto.valueOf(entity.getStatus().name()));
        List<UserDto> friends = new ArrayList<>();
        entity.getFriends().forEach((friend) -> {
            friends.add(exchangeFrom(friend));
        });
        user.getFriends().addAll(friends);
        return user;
    }

}

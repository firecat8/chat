package com.chat.persistence.exchanger;

import com.chat.domain.User;
import com.chat.domain.UserStatus;
import com.chat.persistence.dto.UserStatusDto;
import com.chat.persistence.dto.UserDto;
import java.util.ArrayList;

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
        User user = new User(dto.getUsername(), dto.getPassword(), UserStatus.valueOf(dto.getStatus().name()), dto.getStatusTime());
        user.getFriends().addAll(exchangeDtoList(new ArrayList<>(dto.getFriends())));
        return user;
    }

    @Override
    public UserDto exchangeFrom(User entity) {
        UserDto user = new UserDto(entity.getUsername(), entity.getPassword(),
                UserStatusDto.valueOf(entity.getStatus().name()), entity.getStatusTime());
        user.getFriends().addAll(exchangeEntityList(new ArrayList<>(entity.getFriends())));
        return user;
    }

}

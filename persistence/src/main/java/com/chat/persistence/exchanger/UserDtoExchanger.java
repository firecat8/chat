package com.chat.persistence.exchanger;

import com.chat.domain.User;
import com.chat.domain.UserStatus;
import com.chat.persistence.dto.UserStatusDto;
import com.chat.persistence.dto.UserDto;
import javax.persistence.EntityManager;

/**
 *
 * @author gdimitrova
 */
public class UserDtoExchanger extends DtoEntityExchanger<UserDto, User> {

    public final static UserDtoExchanger INSTANCE = new UserDtoExchanger(null);

    private EntityManager em;

    public UserDtoExchanger(EntityManager em) {
        this.em = em;
    }

    @Override
    public User exchangeFrom(UserDto dto) {
        User user = exchangeFromUser(dto);
        dto.getFriends().forEach((friendId) -> {
            user.addFriend(friendId);
        });
        return user;
    }

    @Override
    public UserDto exchangeFrom(User entity) {
        UserDto user = exchangeFromUser(entity);
        entity.getFriends().forEach((friend) -> {
            user.addFriend(friend);
        });
        return user;
    }

    private UserDto exchangeFromUser(User entity) {
        return new UserDto(entity.getUsername(), entity.getPassword(),
                UserStatusDto.valueOf(entity.getStatus().name()), entity.getStatusTime());
    }

    private User exchangeFromUser(UserDto dto) {
        return new User(dto.getUsername(), dto.getPassword(), UserStatus.valueOf(dto.getStatus().name()), dto.getStatusTime());
    }

    private UserDto find(Long id) {
        return em.find(UserDto.class, id);
    }

}

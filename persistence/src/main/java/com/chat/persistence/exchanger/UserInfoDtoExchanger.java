package com.chat.persistence.exchanger;

import com.chat.domain.User;
import com.chat.domain.UserInfo;
import com.chat.persistence.dto.UserInfoDto;

/**
 *
 * @author gdimitrova
 */
public class UserInfoDtoExchanger extends DtoEntityExchanger<UserInfoDto, UserInfo> {

    public final static UserInfoDtoExchanger INSTANCE = new UserInfoDtoExchanger();

    private UserInfoDtoExchanger() {
    }

    @Override
    public UserInfo exchangeFrom(UserInfoDto dto) {
        return new UserInfo(
                UserDtoExchanger.INSTANCE.exchange(dto.getUser()),
                dto.getFirstname(), dto.getLastname(),
                dto.getEmail(), dto.getPhone(), dto.getCity()
        );
    }

    @Override
    public UserInfoDto exchangeFrom(UserInfo e) {
        return new UserInfoDto(
                UserDtoExchanger.INSTANCE.exchange(e.getUser()),
                e.getFirstname(), e.getLastname(), e.getEmail(), e.getPhone(), e.getCity()
        );
    }

}

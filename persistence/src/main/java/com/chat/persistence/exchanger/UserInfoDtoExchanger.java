package com.chat.persistence.exchanger;

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
        return new UserInfo(dto.getUser().getUsername(),
                dto.getUser().getPassword(),
                dto.getFirstname(), dto.getLastname(),
                dto.getEmail(), dto.getPhone(), dto.getCity());
    }

    @Override
    public UserInfoDto exchangeFrom(UserInfo e) {
        return new UserInfoDto(e.getUser().getUsername(), e.getUser().getPassword(),
                e.getFirstname(), e.getLastname(), e.getEmail(), e.getPhone(), e.getCity());
    }

}

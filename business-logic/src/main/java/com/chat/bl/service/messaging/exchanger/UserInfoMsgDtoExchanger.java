package com.chat.bl.service.messaging.exchanger;

import com.chat.domain.UserInfo;
import com.chat.messaging.dto.UserInfoMessageDto;

/**
 *
 * @author gdimitrova
 */
public class UserInfoMsgDtoExchanger extends MessageDtoEntityExchanger<UserInfoMessageDto, UserInfo> {

    public final static UserInfoMsgDtoExchanger INSTANCE = new UserInfoMsgDtoExchanger();

    private UserInfoMsgDtoExchanger() {
    }

    @Override
    public UserInfo exchangeFrom(UserInfoMessageDto dto) {
        return new UserInfo(
                UserMsgDtoExchanger.INSTANCE.exchangeFrom(dto.getUser()),
                dto.getFirstname(), dto.getLastname(),
                dto.getEmail(), dto.getPhone(), dto.getCity());
    }

    @Override
    public UserInfoMessageDto exchangeFrom(UserInfo e) {
        return new UserInfoMessageDto(
                UserMsgDtoExchanger.INSTANCE.exchangeFrom(e.getUser()),
                e.getFirstname(), e.getLastname(), e.getEmail(), e.getPhone(), e.getCity()
        );
    }

}

package com.chat.bl.service.messaging.exchanger;

import com.chat.domain.UserInfo;
import com.chat.messaging.vo.UserInfoVo;

/**
 *
 * @author gdimitrova
 */
public class UserInfoVoExchanger extends VoEntityExchanger<UserInfoVo, UserInfo> {

    public final static UserInfoVoExchanger INSTANCE = new UserInfoVoExchanger();

    private UserInfoVoExchanger() {
    }

    @Override
    public UserInfo exchangeFrom(UserInfoVo dto) {
        return new UserInfo(
                UserVoExchanger.INSTANCE.exchangeFrom(dto.getUser()),
                dto.getFirstname(), dto.getLastname());
    }

    @Override
    public UserInfoVo exchangeFrom(UserInfo e) {
        return new UserInfoVo(
                UserVoExchanger.INSTANCE.exchangeFrom(e.getUser()),
                e.getFirstname(), e.getLastname()
        );
    }

}

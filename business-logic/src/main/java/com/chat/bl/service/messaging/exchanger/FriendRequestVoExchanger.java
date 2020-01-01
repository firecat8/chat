package com.chat.bl.service.messaging.exchanger;

import com.chat.domain.FriendRequest;
import com.chat.domain.FriendRequestStatus;
import com.chat.domain.User;
import com.chat.messaging.vo.FriendRequestVo;
import com.chat.messaging.vo.FriendRequestStatusVo;
import com.chat.messaging.vo.UserVo;

/**
 *
 * @author gdimitrova
 */
public class FriendRequestVoExchanger extends VoEntityExchanger<FriendRequestVo, FriendRequest> {

    public final static FriendRequestVoExchanger INSTANCE = new FriendRequestVoExchanger();

    private FriendRequestVoExchanger() {
    }

    @Override
    protected FriendRequest exchangeFrom(FriendRequestVo dto) {
        return new FriendRequest(exchangeUser(dto.getSender()), exchangeUser(dto.getReceiver()),
                FriendRequestStatus.valueOf(dto.getRequestStatus().name()));
    }

    @Override
    protected FriendRequestVo exchangeFrom(FriendRequest e) {
        return new FriendRequestVo(exchangeUser(e.getSender()), exchangeUser(e.getReceiver()),
                FriendRequestStatusVo.valueOf(e.getRequestStatus().name()));
    }

    private User exchangeUser(UserVo dto) {
        return UserVoExchanger.INSTANCE.exchange(dto);
    }

    private UserVo exchangeUser(User e) {
        return UserVoExchanger.INSTANCE.exchange(e);
    }

}

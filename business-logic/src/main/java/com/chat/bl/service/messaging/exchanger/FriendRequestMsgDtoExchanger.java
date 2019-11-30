package com.chat.bl.service.messaging.exchanger;

import com.chat.domain.FriendRequest;
import com.chat.domain.FriendRequestStatus;
import com.chat.domain.User;
import com.chat.messaging.dto.FriendRequestMessageDto;
import com.chat.messaging.dto.FriendRequestStatusMsgDto;
import com.chat.messaging.dto.UserMessageDto;

/**
 *
 * @author gdimitrova
 */
public class FriendRequestMsgDtoExchanger extends MessageDtoEntityExchanger<FriendRequestMessageDto, FriendRequest> {

    public final static FriendRequestMsgDtoExchanger INSTANCE = new FriendRequestMsgDtoExchanger();

    private FriendRequestMsgDtoExchanger() {
    }

    @Override
    protected FriendRequest exchangeFrom(FriendRequestMessageDto dto) {
        return new FriendRequest(exchangeUser(dto.getSender()), exchangeUser(dto.getReceiver()),
                FriendRequestStatus.valueOf(dto.getRequestStatus().name()));
    }

    @Override
    protected FriendRequestMessageDto exchangeFrom(FriendRequest e) {
        return new FriendRequestMessageDto(exchangeUser(e.getSender()), exchangeUser(e.getReceiver()),
                FriendRequestStatusMsgDto.valueOf(e.getRequestStatus().name()));
    }

    private User exchangeUser(UserMessageDto dto) {
        return UserMsgDtoExchanger.INSTANCE.exchange(dto);
    }

    private UserMessageDto exchangeUser(User e) {
        return UserMsgDtoExchanger.INSTANCE.exchange(e);
    }

}

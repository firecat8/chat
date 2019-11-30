package com.chat.persistence.exchanger;

import com.chat.domain.FriendRequest;
import com.chat.domain.FriendRequestStatus;
import com.chat.domain.User;
import com.chat.persistence.dto.FriendRequestDto;
import com.chat.persistence.dto.FriendRequestStatusDto;
import com.chat.persistence.dto.UserDto;

/**
 *
 * @author gdimitrova
 */
public class FriendRequestDtoExchanger extends DtoEntityExchanger<FriendRequestDto, FriendRequest> {

    public final static FriendRequestDtoExchanger INSTANCE = new FriendRequestDtoExchanger();

    private FriendRequestDtoExchanger() {
    }

    @Override
    protected FriendRequest exchangeFrom(FriendRequestDto dto) {
        return new FriendRequest(getUser(dto.getSender()), getUser(dto.getReceiver()),
                FriendRequestStatus.valueOf(dto.getStatus().name()));
    }

    @Override
    protected FriendRequestDto exchangeFrom(FriendRequest e) {

        return new FriendRequestDto(getUser(e.getSender()), getUser(e.getReceiver()),
                FriendRequestStatusDto.valueOf(e.getRequestStatus().name()));
    }

    private User getUser(UserDto dto) {
        return UserDtoExchanger.INSTANCE.exchange(dto);
    }

    private UserDto getUser(User e) {
        return UserDtoExchanger.INSTANCE.exchange(e);
    }

}

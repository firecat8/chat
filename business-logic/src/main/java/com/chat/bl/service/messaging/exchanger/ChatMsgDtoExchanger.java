package com.chat.bl.service.messaging.exchanger;

import com.chat.domain.Chat;
import com.chat.domain.ChatType;
import com.chat.messaging.dto.ChatMessageDto;
import com.chat.messaging.dto.ChatTypeMsgDto;

/**
 *
 * @author gdimitrova
 */
public class ChatMsgDtoExchanger extends MessageDtoEntityExchanger<ChatMessageDto, Chat> {

    public final static ChatMsgDtoExchanger INSTANCE = new ChatMsgDtoExchanger();

    private ChatMsgDtoExchanger() {
    }

    @Override
    public Chat exchangeFrom(ChatMessageDto dto) {
        return new Chat(dto.getName(), ChatType.valueOf(dto.getChatType().name()));
    }

    @Override
    public ChatMessageDto exchangeFrom(Chat e) {
        return new ChatMessageDto(e.getName(), ChatTypeMsgDto.valueOf(e.getChatType().name()));
    }

}

package com.chat.persistence.exchanger;

import com.chat.domain.exchanger.AbstractDtoEntityExchanger;
import com.chat.domain.Chat;
import com.chat.domain.ChatType;
import com.chat.persistence.dto.ChatDto;
import com.chat.persistence.dto.ChatTypeDto;
import com.chat.persistence.dto.ChatDto;

/**
 *
 * @author gdimitrova
 */
public class ChatDtoExchanger extends DtoEntityExchanger<ChatDto, Chat> {

    public final static ChatDtoExchanger INSTANCE = new ChatDtoExchanger();

    private ChatDtoExchanger() {
    }

    @Override
    public Chat exchangeFrom(ChatDto dto) {
        return new Chat(dto.getName(), ChatType.valueOf(dto.getChatType().name()));
    }

    @Override
    public ChatDto exchangeFrom(Chat e) {
        return new ChatDto(e.getName(), ChatTypeDto.valueOf(e.getChatType().name()));
    }

}

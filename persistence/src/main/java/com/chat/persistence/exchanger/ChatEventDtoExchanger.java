package com.chat.persistence.exchanger;

import com.chat.domain.Chat;
import com.chat.domain.ChatEvent;
import com.chat.domain.ChatEventType;
import com.chat.domain.User;
import com.chat.persistence.dto.ChatDto;
import com.chat.persistence.dto.ChatEventTypeDto;
import com.chat.persistence.dto.UserDto;
import com.chat.persistence.dto.ChatEventDto;

/**
 *
 * @author gdimitrova
 */
public class ChatEventDtoExchanger extends DtoEntityExchanger<ChatEventDto, ChatEvent> {

    public final static ChatEventDtoExchanger INSTANCE = new ChatEventDtoExchanger();

    private ChatEventDtoExchanger() {
    }

    @Override
    public ChatEvent exchangeFrom(ChatEventDto dto) {
        User sender = UserDtoExchanger.INSTANCE.exchangeFrom(dto.getSender());
        Chat chat = ChatDtoExchanger.INSTANCE.exchangeFrom(dto.getChat());
        return new ChatEvent(dto.getMessage(), ChatEventType.valueOf(dto.getChatEventType().name()), dto.getEventTime(), sender, chat);
    }

    @Override
    public ChatEventDto exchangeFrom(ChatEvent e) {
        UserDto sender = UserDtoExchanger.INSTANCE.exchangeFrom(e.getSender());
        ChatDto chat = ChatDtoExchanger.INSTANCE.exchangeFrom(e.getChat());
        return new ChatEventDto(e.getMessage(), ChatEventTypeDto.valueOf(e.getChatEventType().name()),
                e.getEventTime(), sender, chat);
    }

}

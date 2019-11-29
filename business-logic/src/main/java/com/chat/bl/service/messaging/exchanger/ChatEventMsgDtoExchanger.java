package com.chat.bl.service.messaging.exchanger;

import com.chat.domain.Chat;
import com.chat.domain.ChatEvent;
import com.chat.domain.ChatEventType;
import com.chat.domain.User;
import com.chat.messaging.dto.ChatEventMessageDto;
import com.chat.messaging.dto.ChatEventTypeMsgDto;
import com.chat.messaging.dto.ChatMessageDto;
import com.chat.messaging.dto.UserMessageDto;

/**
 *
 * @author gdimitrova
 */
public class ChatEventMsgDtoExchanger extends MessageDtoEntityExchanger<ChatEventMessageDto, ChatEvent> {

    public final static ChatEventMsgDtoExchanger INSTANCE = new ChatEventMsgDtoExchanger();

    private ChatEventMsgDtoExchanger() {
    }

    @Override
    public ChatEvent exchangeFrom(ChatEventMessageDto dto) {
        User sender = UserMsgDtoExchanger.INSTANCE.exchangeFrom(dto.getSender());
        Chat chat = ChatMsgDtoExchanger.INSTANCE.exchangeFrom(dto.getChat());
        return new ChatEvent(dto.getMessage(), ChatEventType.valueOf(dto.getChatEventType().name()), dto.getEventTime(), sender, chat);
    }

    @Override
    public ChatEventMessageDto exchangeFrom(ChatEvent e) {
        UserMessageDto sender = UserMsgDtoExchanger.INSTANCE.exchangeFrom(e.getSender());
        ChatMessageDto chat = ChatMsgDtoExchanger.INSTANCE.exchangeFrom(e.getChat());
        return new ChatEventMessageDto(e.getMessage(), ChatEventTypeMsgDto.valueOf(e.getChatEventType().name()),
                e.getEventTime(), sender, chat);
    }

}

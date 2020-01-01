package com.chat.bl.service.messaging.exchanger;

import com.chat.domain.Chat;
import com.chat.domain.ChatEvent;
import com.chat.domain.ChatEventType;
import com.chat.domain.User;
import com.chat.messaging.vo.ChatEventVo;
import com.chat.messaging.vo.ChatEventTypeVo;
import com.chat.messaging.vo.ChatVo;
import com.chat.messaging.vo.UserVo;

/**
 *
 * @author gdimitrova
 */
public class ChatEventVoExchanger extends VoEntityExchanger<ChatEventVo, ChatEvent> {

    public final static ChatEventVoExchanger INSTANCE = new ChatEventVoExchanger();

    private ChatEventVoExchanger() {
    }

    @Override
    public ChatEvent exchangeFrom(ChatEventVo dto) {
        User sender = UserVoExchanger.INSTANCE.exchangeFrom(dto.getSender());
        Chat chat = ChatVoExchanger.INSTANCE.exchangeFrom(dto.getChat());
        return new ChatEvent(dto.getMessage(), ChatEventType.valueOf(dto.getChatEventType().name()), dto.getEventTime(), sender, chat);
    }

    @Override
    public ChatEventVo exchangeFrom(ChatEvent e) {
        UserVo sender = UserVoExchanger.INSTANCE.exchangeFrom(e.getSender());
        ChatVo chat = ChatVoExchanger.INSTANCE.exchangeFrom(e.getChat());
        return new ChatEventVo(e.getMessage(), ChatEventTypeVo.valueOf(e.getChatEventType().name()),
                e.getEventTime(), sender, chat);
    }

}

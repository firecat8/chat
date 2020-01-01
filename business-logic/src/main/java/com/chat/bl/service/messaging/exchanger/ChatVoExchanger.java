package com.chat.bl.service.messaging.exchanger;

import com.chat.domain.Chat;
import com.chat.domain.ChatType;
import com.chat.messaging.vo.ChatVo;
import com.chat.messaging.vo.ChatTypeVo;

/**
 *
 * @author gdimitrova
 */
public class ChatVoExchanger extends VoEntityExchanger<ChatVo, Chat> {

    public final static ChatVoExchanger INSTANCE = new ChatVoExchanger();

    private ChatVoExchanger() {
    }

    @Override
    public Chat exchangeFrom(ChatVo dto) {
        return new Chat(dto.getName(), ChatType.valueOf(dto.getChatType().name()));
    }

    @Override
    public ChatVo exchangeFrom(Chat e) {
        return new ChatVo(e.getName(), ChatTypeVo.valueOf(e.getChatType().name()));
    }

}

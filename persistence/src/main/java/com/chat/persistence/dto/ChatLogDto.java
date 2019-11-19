package com.chat.persistence.dto;

import com.chat.domain.Chat;
import com.chat.domain.ChatEventType;
import com.chat.domain.ChatLog;
import com.chat.domain.User;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author gdimitrova
 */
@Entity(name = "chat_logs")
@Table(name = "chat_logs")
public class ChatLogDto extends ChatEventDto implements ChatLog {

    public final static String LOG = "log";

    @Column()
    private String log;

    public ChatLogDto() {
        //Hibernate
    }

    public ChatLogDto(String log, Long eventTime, User sender, Chat chat) {
        super(eventTime, sender, chat);
        this.log = log;
    }

    @Override
    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    @Override
    public ChatEventType getChatEventType() {
        return ChatEventType.LOG;
    }

}

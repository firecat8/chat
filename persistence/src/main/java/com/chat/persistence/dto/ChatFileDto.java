package com.chat.persistence.dto;

import com.chat.domain.Chat;
import com.chat.domain.ChatFile;
import com.chat.domain.User;
import java.sql.Blob;
import javax.persistence.Column;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

/**
 *
 * @author gdimitrova
 */
public class ChatFileDto extends AbstractDto implements ChatFile {

    public final static String SENT_TIME_COLUMN = "sent_time";

    public final static String FILE_NAME_COLUMN = "file_name";

    public final static String FILE_NAME = "filename";

    public final static String FILE = "file";

    public final static String SENT_TIME = "sentTime";

    public final static String SENDER = "sender";

    public final static String CHAT = "chat";

    @Lob
    @Column
    private Blob file;

    @Column(name = FILE_NAME_COLUMN)
    private String filename;

    @Column(name = SENT_TIME_COLUMN)
    private Long sentTime;

    @ManyToOne(targetEntity = UserDto.class)
    @Column
    private User sender;

    @ManyToOne(targetEntity = ChatDto.class)
    @Column
    private Chat chat;

    public ChatFileDto() {
        //Hibernate
    }

    public ChatFileDto(Blob file, String filename, Long sentTime, User sender, Chat chat) {
        this.file = file;
        this.filename = filename;
        this.sentTime = sentTime;
        this.sender = sender;
        this.chat = chat;
    }

    @Override
    public Blob getFile() {
        return file;
    }

    public void setFile(Blob file) {
        this.file = file;
    }

    @Override
    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    @Override
    public Long getSentTime() {
        return sentTime;
    }

    public void setSentTime(Long sentTime) {
        this.sentTime = sentTime;
    }

    @Override
    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    @Override
    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

}

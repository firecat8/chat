package com.chat.persistence.dto;

import com.chat.domain.Chat;
import com.chat.domain.ChatEventType;
import com.chat.domain.ChatFile;
import com.chat.domain.User;
import java.sql.Blob;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 *
 * @author gdimitrova
 */
@Entity(name = "chat_files")
@Table(name = "chat_files")
public class ChatFileDto extends ChatEventDto implements ChatFile {

    public final static String FILE_NAME_COLUMN = "file_name";

    public final static String FILE_NAME = "filename";

    public final static String FILE = "file";

    @Lob
    @Column
    private Blob file;

    @Column(name = FILE_NAME_COLUMN)
    private String filename;

    public ChatFileDto() {
        //Hibernate
    }

    public ChatFileDto(Blob file, String filename, Long eventTime, User sender, Chat chat) {
        super(eventTime, sender, chat);
        this.file = file;
        this.filename = filename;
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
    public ChatEventType getChatEventType() {
        return ChatEventType.FILE_TRANSFER;
    }

}

package com.chat.domain;

import java.sql.Blob;

/**
 *
 * @author gdimitrova
 */
public interface ChatFile {

    public Blob getFile();

    public String getFilename();
    
    public Long getSentTime();

    public User getSender();

    public Chat getChat() ;
}

package com.chat.domain;

import java.sql.Blob;

/**
 *
 * @author gdimitrova
 */
public interface ChatFile extends ChatEvent {

    public Blob getFile();

    public String getFilename();
}

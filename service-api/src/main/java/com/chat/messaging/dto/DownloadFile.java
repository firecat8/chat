package com.chat.messaging.dto;

import com.chat.messaging.message.AbstractResponse;

/**
 *
 * @author gdimitrova
 */
public class DownloadFile extends AbstractResponse {

    private final byte[] file;

    public DownloadFile(byte[] file) {
        this.file = file;
    }

    public byte[] getFile() {
        return file;
    }

}

package com.chat.messaging.vo;

import com.chat.messaging.message.AbstractResponse;

/**
 *
 * @author gdimitrova
 */
public class DownloadedFile extends AbstractResponse {

    private final byte[] file;

    public DownloadedFile(byte[] file) {
        this.file = file;
    }

    public byte[] getFile() {
        return file;
    }

}

package com.chat.messaging.dto;

/**
 *
 * @author gdimitrova
 */
public class DownloadFile extends MessageDto {

    private final byte[] file;

    public DownloadFile(byte[] file) {
        this.file = file;
    }

    public byte[] getFile() {
        return file;
    }

}

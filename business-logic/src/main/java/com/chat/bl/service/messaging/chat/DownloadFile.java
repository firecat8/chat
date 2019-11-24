package com.chat.bl.service.messaging.chat;

/**
 *
 * @author gdimitrova
 */
public class DownloadFile {

    private final byte[] file;

    public DownloadFile(byte[] file) {
        this.file = file;
    }

    public byte[] getFile() {
        return file;
    }

}

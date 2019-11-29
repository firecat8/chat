package com.chat.messaging.dto;

import com.chat.messaging.message.ResponseCode;
import java.io.Serializable;

/**
 *
 * @author gdimitrova
 */
public class ErrorMessageDto implements Serializable {

    private ResponseCode errorCode;

    private String message;

    public ErrorMessageDto() {
    }

    public ErrorMessageDto(ResponseCode errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public ResponseCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ResponseCode errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}

package com.chat.messaging.message.user;

/**
 *
 * @author gdimitrova
 */
public class LogoutRequest extends UserIdRequest {

    public LogoutRequest(Long userId) {
        super(userId);
    }
}

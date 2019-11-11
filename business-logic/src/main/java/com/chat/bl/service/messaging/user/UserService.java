package com.chat.bl.service.messaging.user;

import com.chat.bl.service.messaging.response.ResponseVo;

/**
 *
 * @author gdimitrova
 */
public interface UserService {

    public ResponseVo login(String username, String password);

    public ResponseVo logout(String username, String password);

    public ResponseVo register(RegistrationVo request);
}

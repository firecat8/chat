package com.chat.bl.service.messaging.user;

import com.chat.bl.service.messaging.response.ResponseImpl;

/**
 *
 * @author gdimitrova
 */
public interface UserService {

    public ResponseImpl login(UserRequest req);

    public ResponseImpl logout(UserRequest req);

    public ResponseImpl register(RegistrationRequest request);
}

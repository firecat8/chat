package com.chat.server.endpoint;

import com.chat.bl.service.dao.ChatServiceProvider;
import com.chat.bl.service.dao.MessageException;
import com.chat.bl.service.dao.ServiceProviderRegistry;
import com.chat.bl.service.messaging.EndPoint;
import com.chat.bl.service.messaging.ResponseCode;
import com.chat.bl.service.messaging.ResponseWrapper;
import com.chat.bl.service.messaging.chat.SendFileRequest;
import com.chat.bl.service.messaging.chat.SendLogRequest;
import com.chat.bl.service.messaging.chat.SendMessageRequest;
import com.chat.domain.Chat;
import com.chat.domain.ChatEventType;
import com.chat.domain.User;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gdimitrova
 */
public class ChatEndpoint implements EndPoint {

    private static final Logger LOGGER = Logger.getLogger(ChatEndpoint.class.getName());

    private final ChatServiceProvider provider;

    public ChatEndpoint(ServiceProviderRegistry registry) {
        provider = registry.getChatServiceProvider();
    }

    public ResponseWrapper<Void> sendMessage(SendMessageRequest req) {
        return saveEvent(req.getMessage(), ChatEventType.MESSAGE, req.getEventTime(), req.getSender(), req.getChat());
    }

    public ResponseWrapper<Void> sendLog(SendLogRequest req) {
        return saveEvent(req.getMessage(), ChatEventType.LOG, req.getEventTime(), req.getSender(), req.getChat());
    }

    public ResponseWrapper<Void> sendFile(SendFileRequest req) {
        return saveEvent(req.getMessage(), ChatEventType.FILE_TRANSFER, req.getEventTime(), req.getSender(), req.getChat());
    }

    private synchronized ResponseWrapper<Void> saveEvent(String message, ChatEventType chatEventType, Long eventTime, User sender, Chat chat) {
        try {
            provider.saveEvent(message, chatEventType, eventTime, sender, chat);
            return new ResponseWrapper(ResponseCode.OK, null);
        } catch (MessageException ex) {
            if (provider.getTransaction() != null) {
                provider.rollback();
            }
            LOGGER.log(Level.SEVERE, ex.getMessage());
            return new ResponseWrapper(ResponseCode.ERROR, ex.getMessage());
        } catch (Throwable ex) {
            if (provider.getTransaction() != null) {
                provider.rollback();
            }
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            return new ResponseWrapper(ResponseCode.SERVER_ERROR, ex.getMessage());
        }
    }
}

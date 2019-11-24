package com.chat.server.endpoint;

import com.chat.bl.service.dao.ChatServiceProvider;
import com.chat.bl.service.dao.MessageException;
import com.chat.bl.service.dao.ServiceProviderRegistry;
import com.chat.bl.service.messaging.EndPoint;
import com.chat.bl.service.messaging.ResponseCode;
import com.chat.bl.service.messaging.ResponseWrapper;
import com.chat.bl.service.messaging.chat.CreateChatRequest;
import com.chat.bl.service.messaging.chat.DownloadFile;
import com.chat.bl.service.messaging.chat.DownloadFileRequest;
import com.chat.bl.service.messaging.chat.LoadHistoryRequest;
import com.chat.bl.service.messaging.chat.SendFileRequest;
import com.chat.bl.service.messaging.chat.SendLogRequest;
import com.chat.bl.service.messaging.chat.SendMessageRequest;
import com.chat.domain.Chat;
import com.chat.domain.ChatEvent;
import com.chat.domain.ChatEventType;
import com.chat.domain.User;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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

    public ResponseWrapper<ChatEvent> sendMessage(SendMessageRequest req) {
        return saveEvent(req.getMessage(), ChatEventType.MESSAGE, req.getEventTime(), req.getSender(), req.getChat());
    }

    public ResponseWrapper<ChatEvent> sendLog(SendLogRequest req) {
        return saveEvent(req.getMessage(), ChatEventType.LOG, req.getEventTime(), req.getSender(), req.getChat());
    }

    public ResponseWrapper<ChatEvent> sendFile(SendFileRequest req) throws FileNotFoundException {
        try {
            ResponseWrapper<ChatEvent> responseWrapper = saveEvent(req.getMessage(), ChatEventType.FILE_TRANSFER, req.getEventTime(), req.getSender(), req.getChat());
            saveFile(responseWrapper.getResponse(), req.getFile());
            return responseWrapper;
        } catch (MessageException ex) {
            if (provider.getTransaction() != null) {
                provider.rollback();
            }
            LOGGER.log(Level.SEVERE, ex.getMessage());
            return new ResponseWrapper(ResponseCode.ERROR, ex.getMessage());
        } catch (IOException ex) {
            if (provider.getTransaction() != null) {
                provider.rollback();
            }
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            return new ResponseWrapper(ResponseCode.SERVER_ERROR, ex.getMessage());
        }
    }

    public ResponseWrapper<ChatEvent> getFile(DownloadFileRequest req) {
        try {
            return new ResponseWrapper(ResponseCode.OK, new DownloadFile(Files.readAllBytes(Paths.get(req.getFileName()))));
        } catch (IOException ex) {
            if (provider.getTransaction() != null) {
                provider.rollback();
            }
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            return new ResponseWrapper(ResponseCode.SERVER_ERROR, ex.getMessage());
        }
    }

    public ResponseWrapper<Chat> loadTheLastTenEvents(LoadHistoryRequest req) {
        try {
            return new ResponseWrapper(ResponseCode.OK, provider.loadTheLastTenEvents(req.getChat()));
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

    public ResponseWrapper<Chat> createChat(CreateChatRequest req) {
        try {
            return new ResponseWrapper(ResponseCode.OK, provider.createChat(req.getName(), req.getType()));
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

    private synchronized ResponseWrapper<ChatEvent> saveEvent(String message, ChatEventType chatEventType, Long eventTime, User sender, Chat chat) {
        try {
            return new ResponseWrapper(ResponseCode.OK, provider.saveEvent(message, chatEventType, eventTime, sender, chat));
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

    private void saveFile(ChatEvent c, byte[] file) throws FileNotFoundException, IOException {
        Files.write(Paths.get(c.getStorageFileName()), file);
    }
}

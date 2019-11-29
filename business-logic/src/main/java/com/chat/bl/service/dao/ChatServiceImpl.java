package com.chat.bl.service.dao;

import com.chat.bl.service.messaging.exchanger.ChatEventMsgDtoExchanger;
import com.chat.bl.service.messaging.exchanger.ChatMsgDtoExchanger;
import com.chat.bl.service.messaging.exchanger.UserMsgDtoExchanger;
import com.chat.dao.DaoRegistry;
import com.chat.domain.Chat;
import com.chat.domain.ChatEvent;
import com.chat.domain.ChatEventType;
import com.chat.domain.ChatType;
import com.chat.domain.User;
import com.chat.messaging.dto.ChatEventMessageDto;
import com.chat.messaging.dto.ChatHistoryMessageDto;
import com.chat.messaging.dto.ChatMessageDto;
import com.chat.messaging.dto.DownloadFile;
import com.chat.messaging.dto.ErrorMessageDto;
import com.chat.messaging.dto.UserMessageDto;
import com.chat.messaging.message.ResponseCode;
import com.chat.messaging.message.ResponseListener;
import com.chat.messaging.message.chat.CreateChatRequest;
import com.chat.messaging.message.chat.DownloadFileRequest;
import com.chat.messaging.message.chat.LoadHistoryRequest;
import com.chat.messaging.message.chat.SendFileRequest;
import com.chat.messaging.message.chat.SendLogRequest;
import com.chat.messaging.message.chat.SendMessageRequest;
import com.chat.messaging.services.ChatService;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gdimitrova
 */
public class ChatServiceImpl extends AbstractTransactionalService implements ChatService {

    private static final Logger LOGGER = Logger.getLogger(ChatServiceImpl.class.getName());

    public ChatServiceImpl(DaoRegistry daoRegistry) {
        super(daoRegistry);
    }

    @Override
    public void sendMessage(SendMessageRequest req, ResponseListener<ChatEventMessageDto> listener) {
        doInTransaction((DaoRegistry registry) -> {
            return saveEvent(registry, req.getMessage(), ChatEventType.MESSAGE, req.getEventTime(), req.getSender(), req.getChat());
        }, listener);
    }

    @Override
    public void sendLog(SendLogRequest req, ResponseListener<ChatEventMessageDto> listener) {
        doInTransaction((DaoRegistry registry) -> {
            return saveEvent(registry, req.getMessage(), ChatEventType.LOG, req.getEventTime(), req.getSender(), req.getChat());
        }, listener);
    }

    @Override
    public void sendFile(SendFileRequest req, ResponseListener<ChatEventMessageDto> listener) {
        doInTransaction((DaoRegistry registry) -> {
            ChatEventMessageDto ce = saveEvent(registry, req.getMessage(), ChatEventType.FILE_TRANSFER, req.getEventTime(), req.getSender(), req.getChat());
            try {
                saveFile(ce, req.getFile());
            } catch (IOException ex) {
                LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
                listener.onError(new ErrorMessageDto(ResponseCode.SERVER_ERROR, ex.getMessage()));
            }
            return ce;
        }, listener);
    }

    @Override
    public void getFile(DownloadFileRequest req, ResponseListener<DownloadFile> listener) {
        try {
            DownloadFile downloadFile = new DownloadFile(Files.readAllBytes(Paths.get(req.getFileName())));
            listener.onSuccess(downloadFile);
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            listener.onError(new ErrorMessageDto(ResponseCode.SERVER_ERROR, ex.getMessage()));
        }
    }

    @Override
    public void createChat(CreateChatRequest req, ResponseListener<ChatMessageDto> listener) {
        doInTransaction((DaoRegistry registry) -> {
            return ChatMsgDtoExchanger.INSTANCE.exchange(
                    registry.getChatDao().save(req.getName(), ChatType.valueOf(req.getType().name()))
            );
        }, listener);
    }

    @Override
    public void loadLastTenEvents(LoadHistoryRequest req, ResponseListener<ChatHistoryMessageDto> listener) {

        doInTransaction((DaoRegistry registry) -> {
            Chat chat = ChatMsgDtoExchanger.INSTANCE.exchange(req.getChat());
            List<ChatEvent> history = registry.getChatEventDao().loadTheLastTenEvents(chat);
            return new ChatHistoryMessageDto(exchangeResults(history, ChatEventMsgDtoExchanger.INSTANCE));
        }, listener);
    }

    private ChatEventMessageDto saveEvent(DaoRegistry registry, String message, ChatEventType chatEventType, Long eventTime, UserMessageDto s, ChatMessageDto c) {
        User sender = UserMsgDtoExchanger.INSTANCE.exchangeFrom(s);
        Chat chat = ChatMsgDtoExchanger.INSTANCE.exchangeFrom(c);
        return ChatEventMsgDtoExchanger.INSTANCE.exchange(
                registry.getChatEventDao().save(message, chatEventType, eventTime, sender, chat)
        );
    }

    private void saveFile(ChatEventMessageDto c, byte[] file) throws FileNotFoundException, IOException {
        Files.write(Paths.get(c.getStorageFileName()), file);
    }

}

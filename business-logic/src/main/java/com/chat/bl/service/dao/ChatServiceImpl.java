package com.chat.bl.service.dao;

import com.chat.bl.service.messaging.exchanger.ChatEventMsgDtoExchanger;
import com.chat.bl.service.messaging.exchanger.ChatMsgDtoExchanger;
import com.chat.bl.service.messaging.exchanger.UserMsgDtoExchanger;
import com.chat.dao.DaoRegistry;
import com.chat.domain.Chat;
import com.chat.domain.ChatEvent;
import com.chat.domain.ChatEventType;
import com.chat.domain.ChatType;
import com.chat.domain.ChatUser;
import com.chat.domain.Participant;
import com.chat.domain.User;
import com.chat.messaging.dto.ChatEventMessageDto;
import com.chat.messaging.dto.ChatMessageDto;
import com.chat.messaging.dto.DownloadFile;
import com.chat.messaging.dto.ErrorMessageDto;
import com.chat.messaging.dto.UserMessageDto;
import com.chat.messaging.message.ResponseCode;
import com.chat.messaging.message.ResponseListener;
import com.chat.messaging.message.SuccessResponse;
import com.chat.messaging.message.chat.AddFriendRequest;
import com.chat.messaging.message.chat.ChatEventResponse;
import com.chat.messaging.message.chat.ChatHistoryResponse;
import com.chat.messaging.message.chat.ChatResponse;
import com.chat.messaging.message.chat.ChatsResponse;
import com.chat.messaging.message.chat.CreateChatRequest;
import com.chat.messaging.message.chat.DownloadFileRequest;
import com.chat.messaging.message.chat.FindChatRequest;
import com.chat.messaging.message.chat.LeaveChatRequest;
import com.chat.messaging.message.chat.LoadChatsRequest;
import com.chat.messaging.message.chat.LoadHistoryRequest;
import com.chat.messaging.message.chat.SendFileRequest;
import com.chat.messaging.message.chat.SendLogRequest;
import com.chat.messaging.message.chat.SendMessageRequest;
import com.chat.messaging.services.ChatService;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
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
    public void sendMessage(SendMessageRequest req, ResponseListener<ChatEventResponse> listener) {
        saveEvent(req.getMessage(), ChatEventType.MESSAGE, req.getEventTime(), req.getSender(), req.getChat(), listener);
    }

    @Override
    public void sendLog(SendLogRequest req, ResponseListener<ChatEventResponse> listener) {
        saveEvent(req.getMessage(), ChatEventType.LOG, req.getEventTime(), req.getSender(), req.getChat(), listener);
    }

    @Override
    public void sendFile(SendFileRequest req, ResponseListener<ChatEventResponse> listener) {
        doInTransaction((DaoRegistry registry) -> {
            ChatEventResponse ce = saveEvent(registry, req.getMessage(), ChatEventType.FILE_TRANSFER, req.getEventTime(), req.getSender(), req.getChat());
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
    public synchronized void downloadFile(DownloadFileRequest req, ResponseListener<DownloadFile> listener) {
        try {
            DownloadFile downloadFile = new DownloadFile(Files.readAllBytes(Paths.get(req.getFileName())));
            listener.onSuccess(downloadFile);
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            listener.onError(new ErrorMessageDto(ResponseCode.SERVER_ERROR, ex.getMessage()));
        }
    }

    @Override
    public synchronized void createChat(CreateChatRequest req, ResponseListener<ChatResponse> listener) {
        doInTransaction((DaoRegistry registry) -> {
            return new ChatResponse(ChatMsgDtoExchanger.INSTANCE.exchange(
                    registry.getChatDao().save(req.getName(), ChatType.valueOf(req.getType().name())))
            );
        }, listener);
    }

    @Override
    public void leaveChat(LeaveChatRequest req, ResponseListener<SuccessResponse> listener) {
        doInTransaction((DaoRegistry registry) -> {
            Chat chat = registry.getChatDao().loadById(req.getId());
            if (chat == null) {
                throw new MessageException("Not found chat!");
            }
            Set<Participant> participants = chat.getParticipants();
            if (!participants.isEmpty()) {
                Participant found = participants.stream().filter(p -> p.getUser().getId().equals(req.getId())).findFirst().get();
                if (found != null) {
                    participants.remove(found);
                    registry.getChatDao().update(chat);
                    saveEvent(registry, "User " + found.getUser().getUsername() + " leave chat.", ChatEventType.LOG, Calendar.getInstance().getTimeInMillis(), found.getUser(), chat);
                }
            }
            return null;
        }, listener);
    }

    @Override
    public void addFriend(AddFriendRequest req, ResponseListener<SuccessResponse> listener) {
        doInTransaction((DaoRegistry registry) -> {
            Chat chat = registry.getChatDao().loadById(req.getChatId());
            if (chat == null) {
                throw new MessageException("Not found chat!");
            }
            User adder = registry.getUserDao().loadById(req.getUserId());
            User friend = registry.getUserDao().loadById(req.getFriendId());
            chat.getParticipants().add(new Participant(friend, ChatUser.PARTICIPANT, chat));
            registry.getChatDao().update(chat);
            saveEvent(registry, "User " + adder.getUsername() + " add " + friend.getUsername() + ".", ChatEventType.LOG, Calendar.getInstance().getTimeInMillis(), adder, chat);
            return null;
        }, listener);
    }

    @Override
    public void loadLastTenEvents(LoadHistoryRequest req, ResponseListener<ChatHistoryResponse> listener) {
        doInTransaction((DaoRegistry registry) -> {
            Chat chat = ChatMsgDtoExchanger.INSTANCE.exchange(req.getChat());
            List<ChatEvent> history = registry.getChatEventDao().loadTheLastTenEvents(chat);
            return new ChatHistoryResponse(ChatEventMsgDtoExchanger.INSTANCE.exchangeEntityList(history));

        }, listener);
    }

    private void saveEvent(
            String message, ChatEventType chatEventType,
            Long eventTime, UserMessageDto s, ChatMessageDto c,
            ResponseListener<ChatEventResponse> listener) {
        doInTransaction((DaoRegistry registry) -> {
            return saveEvent(registry, message, chatEventType, eventTime, s, c);
        }, listener);
    }

    private ChatEventResponse saveEvent(DaoRegistry registry,
            String message, ChatEventType chatEventType,
            Long eventTime, UserMessageDto s, ChatMessageDto c) {
        User sender = UserMsgDtoExchanger.INSTANCE.exchangeFrom(s);
        Chat chat = ChatMsgDtoExchanger.INSTANCE.exchangeFrom(c);
        return saveEvent(registry, message, chatEventType, eventTime, sender, chat);
    }

    private ChatEventResponse saveEvent(DaoRegistry registry,
            String message, ChatEventType chatEventType,
            Long eventTime, User s, Chat c) {
        ChatEventMessageDto chatEvent = ChatEventMsgDtoExchanger.INSTANCE.exchange(
                registry.getChatEventDao().save(message, chatEventType, eventTime, s, c)
        );
        return new ChatEventResponse(chatEvent);
    }

    private void saveFile(ChatEventResponse c, byte[] file) throws FileNotFoundException, IOException {
        Files.write(Paths.get(c.getChatEvent().getStorageFileName()), file);
    }

    @Override
    public void loadChats(LoadChatsRequest req, ResponseListener<ChatsResponse> listener) {
        doInTransaction((DaoRegistry registry) -> {
            User user = registry.getUserDao().loadById(req.getUserId());
            if (user == null) {
                throw new MessageException("Not found chat!");
            }
            List<Chat> chats = registry.getChatDao().loadChats(user);
            return new ChatsResponse(ChatMsgDtoExchanger.INSTANCE.exchangeEntityList(chats));
        }, listener);
    }

    @Override
    public void findChats(FindChatRequest req, ResponseListener<ChatsResponse> listener) {
        doInTransaction((DaoRegistry registry) -> {
            List<Chat> chats = registry.getChatDao().findChats(req.getChatNmae());
            return new ChatsResponse(ChatMsgDtoExchanger.INSTANCE.exchangeEntityList(chats));
        }, listener);
    }

}

package com.chat.controller.chat;

import com.chat.app.GUIApp;
import com.chat.messaging.message.SuccessResponse;
import com.chat.messaging.vo.ChatVo;
import com.chat.messaging.vo.ChatTypeVo;
import com.chat.messaging.vo.ParticipantVo;
import com.chat.messaging.vo.UserVo;
import com.chat.messaging.vo.UserStatusVo;
import com.chat.messaging.message.chat.ChatEventResponse;
import com.chat.messaging.message.chat.ChatHistoryResponse;
import com.chat.messaging.message.chat.ChatResponse;
import com.chat.messaging.message.chat.ChatsResponse;
import com.chat.messaging.message.user.FriendRequestResponse;
import com.chat.messaging.message.user.FriendRequestsResponse;
import com.chat.messaging.message.user.UserResponse;
import com.chat.messaging.message.user.UsersResponse;
import com.chat.messaging.vo.ChatEventTypeVo;
import com.chat.messaging.vo.ChatEventVo;
import com.chat.messaging.vo.DownloadedFile;
import com.chat.messaging.vo.EntityVo;
import com.chat.messaging.vo.FriendRequestStatusVo;
import com.chat.messaging.vo.FriendRequestVo;
import com.chat.task.TaskFactory;
import com.chat.task.TaskManager;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.util.StringConverter;

/**
 * Controller for testing
 *
 * @author gdimitrova
 */
public class ChatController implements Initializable {

    private static final Logger LOGGER = Logger.getLogger(ChatController.class.getName());

    public static UserVo currentUser = null;

    @FXML
    private ChoiceBox<UserStatusSelectionItem> statusBar;

    @FXML
    private PasswordField passField;

    @FXML
    private Label error;

    @FXML
    private Label userName;

    @FXML
    private TextField usernameTextField;

    @FXML
    private AnchorPane loginPane;

    @FXML
    private AnchorPane chatPane;

    @FXML
    private ListView<ChatVo> groupChatsList;

    @FXML
    private ListView<ChatVo> chatSearchList;

    @FXML
    private ListView<UserVo> friendsList;

    @FXML
    private ListView<ParticipantVo> participantsList;

    @FXML
    private ListView<UserVo> userSearchList;

    @FXML
    private ListView<FriendRequestVo> myRequestsList;

    @FXML
    private ListView<FriendRequestVo> sendRequestsList;

    @FXML
    private ListView<HBox> chatPanel;

    @FXML
    private Group groupChatExtraControl;

    @FXML
    private TextField searchBar;

    @FXML
    private TextField groupChatName;

    @FXML
    private TextField addFriendTextField;

    @FXML
    private TextArea messageBox;

    private final DateFormat dateFormater = new SimpleDateFormat("HH:mm:ss");

    private final Map<Long, ChatVo> chats = new HashMap<>();

    private final Map<Long, ChatVo> privateChatsByFriendId = new HashMap<>();

    private final Map<String, UserVo> friendsMapByFriendName = new HashMap<>();

    private ChatVo currentChat;

    private final FileChooser fileChooser = new FileChooser();

    private final Map<UserStatusVo, Color> statusColorsMap = new HashMap<>() {
        {
            put(UserStatusVo.ACTIVE, Color.GREEN);
            put(UserStatusVo.AWAY, Color.YELLOW);
            put(UserStatusVo.DO_NOT_DISTURB, Color.RED);
            put(UserStatusVo.OFFLINE, Color.DARKGRAY);
        }

    };

    private final Map<FriendRequestStatusVo, Color> requestStatusColorsMap = new HashMap<>() {
        {
            put(FriendRequestStatusVo.NEW, Color.GREEN);
            put(FriendRequestStatusVo.DECLINED, Color.RED);
        }

    };

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initLists();
        //
        // USER STATUS
        statusBar.setItems(FXCollections.observableArrayList(UserStatusSelectionItemRegistry.INSTANCE.getAllItems()));
        statusBar.setConverter(new StringConverter<UserStatusSelectionItem>() {
            @Override
            public String toString(UserStatusSelectionItem t) {
                return t.getStatusLabel();
            }

            @Override
            public UserStatusSelectionItem fromString(String string) {
                return UserStatusSelectionItemRegistry.INSTANCE.getItem(string);
            }
        });
        statusBar.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends UserStatusSelectionItem> ov, UserStatusSelectionItem oldValue, UserStatusSelectionItem newValue) -> {
            changeStatus(newValue.getUserStatus());
        });

        groupChatExtraControl.setVisible(false);
        // TODO
    }

    @FXML
    private void onCreateGroupChatButtonClick() {
        createGroupChat();

    }

    @FXML
    private void onSearchButtonClick() {
        findFriend();
        findChat();
    }

    @FXML
    private void onSendMessageButtonClick() {
        sendMessage();
    }

    @FXML
    private void onSendFileButtonClick() {
        File file = fileChooser.showOpenDialog(GUIApp.stage);
        if (file != null) {
            sendFile(file);
        }
    }

    @FXML
    private void onLoginOrRegistryButtonClick() {
        login();
    }

    @FXML
    private void onLogoutButtonClick() {
        logout();
    }

    @FXML
    private void onLeaveChatButtonClick() {
        leaveChat();
    }

    @FXML
    private void onAddFriendButtonClick() {
        String friendName = addFriendTextField.getText();
        UserVo friend = friendsMapByFriendName.get(friendName);
        if (friend != null) {
            addFriendToChat(friend);
            return;
        }
        setMessage("Not found friend by that name " + friendName);
    }

    // Service task methods
    private void login() {
        TaskManager.executeTask(TaskFactory.createLoginTask(
                usernameTextField.getText(), passField.getText(),
                (UserResponse rsp) -> {
                    onLoadedUser(rsp);
                },
                (errorResponse) -> {
                    if (errorResponse.getErrorMessage().contains("Not exist user")) {
                        register();
                        return;
                    }
                    setMessage(errorResponse.getMessage());
                }));
    }

    private void logout() {
        TaskManager.executeTask(TaskFactory.createLogoutTask(currentUser,
                (success) -> {
                    setMessage("Sucessfully logout " + currentUser.getUsername());
                    currentUser = null;
                    setLoginPaneVisible();
                    clearLists();
                },
                (errorResponse) -> {
                    setMessage(errorResponse.getMessage());
                }));
    }

    private void register() {
        String username = usernameTextField.getText();
        TaskManager.executeTask(TaskFactory.createRegisterTask(
                username, passField.getText(), username, username,
                (UserResponse rsp) -> {
                    onLoadedUser(rsp);
                },
                (errorResponse) -> {
                    setMessage(errorResponse.getMessage());
                }));
    }

    private void changeStatus(UserStatusVo newStatus) {
        TaskManager.executeTask(TaskFactory.createChangeStatusTask(
                newStatus, currentUser,
                (success) -> {
                },
                (errorResponse) -> {
                    setMessage(errorResponse.getMessage());
                }));
    }

    private void loadChats() {
        TaskManager.executeTask(TaskFactory.createLoadChatsTask(
                currentUser,
                (ChatsResponse rsp) -> {
                    fillCacheAndLists(rsp.getList());
                },
                (errorResponse) -> {
                    setMessage(errorResponse.getMessage());
                }));
    }

    private void createGroupChat() {
        TaskManager.executeTask(TaskFactory.createCreateGroupChatTask(groupChatName.getText(), currentUser,
                (ChatResponse rsp) -> {
                    ChatVo chat = rsp.getChat();
                    chats.put(chat.getId(), chat);
                    groupChatsList.getItems().add(0, chat);
                },
                (errorResponse) -> {
                    setMessage(errorResponse.getMessage());
                }));
    }

    private void findFriend() {
        TaskManager.executeTask(TaskFactory.createFindFriendTask(searchBar.getText(), currentUser.getId(),
                (UsersResponse rsp) -> {
                    userSearchList.setItems(FXCollections.observableArrayList(rsp.getList()));
                },
                (errorResponse) -> {
                    setMessage(errorResponse.getMessage());
                }));
    }

    private void findChat() {
        TaskManager.executeTask(TaskFactory.createFindChatsTask(searchBar.getText(),
                (ChatsResponse rsp) -> {
                    chatSearchList.setItems(FXCollections.observableArrayList(rsp.getList()));
                },
                (errorResponse) -> {
                    setMessage(errorResponse.getMessage());
                }));
    }

    private void sendMessage() {
        TaskManager.executeTask(TaskFactory.createSendMessageTask(messageBox.getText(), currentUser, currentChat,
                (ChatEventResponse rsp) -> {
                    addChatEvent(rsp.getChatEvent());
                },
                (errorResponse) -> {
                    setMessage(errorResponse.getMessage());
                }));
    }

    private void sendFile(File file) {
        try {
            TaskManager.executeTask(TaskFactory.createSendFileTask(
                    file.getName(), Files.readAllBytes(file.toPath()), currentUser, currentChat,
                    (ChatEventResponse rsp) -> {
                        addChatEvent(rsp.getChatEvent());
                    },
                    (errorResponse) -> {
                        setMessage(errorResponse.getMessage());
                    }));
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    private void sendFriendRequest(UserVo friend) {
        sendFriendRequest(friend, (frRequest) -> myRequestsList.getItems().add(frRequest));
    }

    private void sendFriendRequest(UserVo friend, Consumer<FriendRequestVo> listener) {
        TaskManager.executeTask(TaskFactory.createSendFriendRequestTask(
                currentUser.getId(), friend.getId(),
                (FriendRequestResponse rsp) -> {
                    listener.accept(rsp.getFriendRequest());
                },
                (errorResponse) -> {
                    setMessage(errorResponse.getMessage());
                }));
    }

    private void loadFriendRequests() {
        TaskManager.executeTask(TaskFactory.createLoadFriendRequestsTask(
                currentUser,
                (FriendRequestsResponse rsp) -> {
                    myRequestsList.getItems().clear();
                    sendRequestsList.getItems().clear();
                    rsp.getList().forEach((friendRequestVo) -> {
                        if (friendRequestVo.getSender().getId().equals(currentUser.getId())) {
                            myRequestsList.getItems().add(friendRequestVo);
                        } else {
                            sendRequestsList.getItems().add(friendRequestVo);
                        }
                    });
                },
                (errorResponse) -> {
                    setMessage(errorResponse.getMessage());
                }));
    }

    private void acceptFriendRequest(FriendRequestVo friendRequest) {
        TaskManager.executeTask(TaskFactory.createAcceptFriendRequestTask(friendRequest,
                (ChatResponse rsp) -> {
                    UserVo sender = friendRequest.getSender();
                    privateChatsByFriendId.put(sender.getId(), rsp.getChat());
                    friendsMapByFriendName.put(sender.getUsername(), sender);
                    friendsList.getItems().add(sender);
                    sendRequestsList.getItems().remove(friendRequest);
                },
                (errorResponse) -> {
                    setMessage(errorResponse.getMessage());
                }));
    }

    private void declineFriendRequest(FriendRequestVo friendRequest, int cellIndex) {
        TaskManager.executeTask(TaskFactory.createDeclineFriendRequestTask(
                friendRequest,
                (FriendRequestResponse rsp) -> {
                    myRequestsList.getItems().remove(cellIndex);
                },
                (errorResponse) -> {
                    setMessage(errorResponse.getMessage());
                }));
    }

    private void loadLastTenEvents(ChatVo chat) {
        TaskManager.executeTask(TaskFactory.createLoadLastTenEventsTask(
                chat,
                (ChatHistoryResponse rsp) -> {
                    setChatHistory(rsp.getHistory());
                },
                (errorResponse) -> {
                    setMessage(errorResponse.getMessage());
                }));
    }

    private void downloadFile(ChatEventVo c) {
        TaskManager.executeTask(TaskFactory.createDownloadFileTask(
                c,
                (DownloadedFile rsp) -> {
                    saveFile(c, rsp.getFile());
                },
                (errorResponse) -> {
                    setMessage(errorResponse.getMessage());
                }));
    }

    private void leaveChat() {

        TaskManager.executeTask(TaskFactory.createLeaveChatTask(
                currentUser, currentChat,
                (SuccessResponse rsp) -> {

                },
                (errorResponse) -> {
                    setMessage(errorResponse.getMessage());
                }));
    }

    private void addFriendToChat(UserVo friend) {
        TaskManager.executeTask(TaskFactory.createAddFriendToChatTask(
                currentUser, friend, currentChat,
                (ChatResponse rsp) -> {
                    ChatVo chat = rsp.getChat();
                    chats.put(chat.getId(), chat);
                    selectChat(chat, true);
                },
                (errorResponse) -> {
                    setMessage(errorResponse.getMessage());
                }));
    }

    // Help methods
    private void saveFile(ChatEventVo c, byte[] fileBytes) {
        fileChooser.setInitialFileName(c.getMessage());
        File file = fileChooser.showSaveDialog(GUIApp.stage);
        if (file != null) {
            try {
                Files.write(file.toPath(), fileBytes);
            } catch (IOException ex) {
                setMessage(ex.getMessage());
                LOGGER.log(Level.SEVERE, null, ex);
            }
        }
    }

    private void setLoginPaneVisible() {
        setPaneVisibility(loginPane, true);
        setPaneVisibility(chatPane, false);
    }

    private void setChatPaneVisible() {
        setPaneVisibility(loginPane, false);
        setPaneVisibility(chatPane, true);
    }

    private void setPaneVisibility(AnchorPane pane, boolean visible) {
        pane.setVisible(visible);
        pane.setManaged(visible);
    }

    private void onLoadedUser(UserResponse rsp) {
        currentUser = rsp.getUser();
        loadChats();
        loadFriendRequests();
        setMessage("Sucessfully login " + currentUser.getUsername());
        userName.setText(currentUser.getUsername());
        statusBar.setValue(UserStatusSelectionItemRegistry.INSTANCE.getItem(currentUser.getStatus()));
        setChatPaneVisible();
    }

    private void fillCacheAndLists(List<ChatVo> list) {
        chats.clear();
        List<ChatVo> groupChats = new ArrayList<>();
        Long currentUserId = currentUser.getId();
        Set<UserVo> friends = new HashSet<>();
        for (ChatVo chat : list) {
            chats.put(chat.getId(), chat);
            if (chat.getChatType().equals(ChatTypeVo.GROUP)) {
                groupChats.add(chat);
                continue;
            }
            UserVo friend = findFriend(chat, currentUserId).getUser();
            privateChatsByFriendId.put(friend.getId(), chat);
            friendsMapByFriendName.put(friend.getUsername(), friend);
            friends.add(friend);
        }
        groupChatsList.setItems(FXCollections.observableArrayList(groupChats));
        friendsList.setItems(FXCollections.observableArrayList(friends));
    }

    public void setMessage(String msg) {
        error.setText(dateFormater.format(Calendar.getInstance().getTime()) + "  " + msg);
    }

    private ParticipantVo findFriend(ChatVo chat, Long currentUserId) {
        return chat.getParticipants().stream().filter(c -> !c.getUser().getId().equals(currentUserId)).findFirst().get();
    }

    private void selectChat(UserVo friend) {
        selectChat(privateChatsByFriendId.get(friend.getId()), false);
    }

    private void selectChat(ChatVo chat, boolean isLeaveChatBtnVisible) {
        currentChat = chat;
        participantsList.setItems(FXCollections.observableArrayList(chat.getParticipants()));
        loadLastTenEvents(chat);
        groupChatExtraControl.setVisible(isLeaveChatBtnVisible);
    }

    private void setChatHistory(List<ChatEventVo> history) {
        List<HBox> hboxes = new ArrayList<>();
        history.forEach((chatEvent) -> {
            createHboxMessage(hboxes, chatEvent);
        });
        chatPanel.setItems(FXCollections.observableArrayList(hboxes));
    }

    private void addChatEvent(ChatEventVo c) {
        chatPanel.getItems().addAll(createHboxMessage(new ArrayList<>(), c));
    }

    private List<HBox> createHboxMessage(List<HBox> hboxes, ChatEventVo c) {
        ChatEventTypeVo type = c.getChatEventType();
        String message = c.getMessage();
        String username = c.getSender().getUsername();
        String eventTime = dateFormater.format(new Date(c.getEventTime()));

        if (type.equals(ChatEventTypeVo.MESSAGE)) {
            hboxes.add(new HBox(createLabel(username + "," + eventTime, Color.BLUE, 10)));
            hboxes.add(new HBox(createLabel(message, Color.BLACK, 12)));
            return hboxes;
        }

        if (type.equals(ChatEventTypeVo.LOG)) {
            hboxes.add(new HBox(createLabel(eventTime + " " + message, Color.ALICEBLUE, 10)));
            return hboxes;
        }

        if (type.equals(ChatEventTypeVo.FILE_TRANSFER)) {
            Button btn = new Button("Download File");
            btn.setOnMousePressed((MouseEvent event) -> {
                downloadFile(c);
            });
            hboxes.add(new HBox(createLabel(username + "," + eventTime, Color.BLUE, 10)));
            hboxes.add(new HBox(createLabel(eventTime + " " + message, Color.BLACK, 12), btn));
        }
        return hboxes;

    }

    private Label createLabel(String labelText, Color color, int fontSize) {
        Label newLabel = new Label(labelText);
        newLabel.setAlignment(Pos.BOTTOM_LEFT);
        newLabel.setFont(new Font("Verdana", fontSize));
        newLabel.setTextAlignment(TextAlignment.CENTER);
        newLabel.setTextFill(color);
        return newLabel;
    }

    private <T extends EntityVo> void clearLists() {
        userSearchList.getItems().clear();
        friendsList.getItems().clear();
        groupChatsList.getItems().clear();
        chatSearchList.getItems().clear();
        participantsList.getItems().clear();
        myRequestsList.getItems().clear();
        sendRequestsList.getItems().clear();
    }

    private void initLists() {
        initUserSearchList();
        initFriendList();
        initGroupChatList();
        initChatSearchList();
        initParticipantsList();
        initSendFriendRequestList();
        initMyFriendRequestList();
    }

    private void initParticipantsList() {
        participantsList.setCellFactory(param -> {
            ListCell<ParticipantVo> cell = new ListCell<ParticipantVo>() {
                @Override
                protected void updateItem(ParticipantVo item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                        setContextMenu(null);
                        setGraphic(null);
                        return;
                    }
                    UserVo user = item.getUser();
                    setGraphic(
                            new HBox(createImage("avatar.png"), createLabel(user.getUsername() + " " + item.getUserType().name(),
                                    statusColorsMap.get(user.getStatus()), 16))
                    );
                }
            };
            cell.setAlignment(Pos.CENTER);
            return cell;
        });
    }

    private void initSendFriendRequestList() {
        sendRequestsList.setCellFactory(param -> {
            ListCell<FriendRequestVo> cell = new ListCell<FriendRequestVo>() {
                @Override
                protected void updateItem(FriendRequestVo item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                        setContextMenu(null);
                        setGraphic(null);
                        return;
                    }
                    setGraphic(
                            createFriendHboxControl(item)
                    );
                    if (item.getRequestStatus().name().equals(FriendRequestStatusVo.NEW.name())) {
                        setContextMenu(createContextMenu(item,
                                Arrays.asList("Accept request", "Decline request"),
                                (it) -> {
                                    acceptFriendRequest(item);
                                },
                                (it) -> {
                                    declineFriendRequest(item, getIndex());
                                }));
                        return;
                    }
                    setContextMenu(null);
                }

            };
            return cell;
        });
    }

    private HBox createFriendHboxControl(FriendRequestVo frRequest) {
        return new HBox(
                createImage("friend-request-icon.jpg"),
                createLabel(
                        frRequest.getSender().getUsername() + "(" + frRequest.getRequestStatus().name() + ")",
                        requestStatusColorsMap.get(frRequest.getRequestStatus()), 24)
        );
    }

    private void initMyFriendRequestList() {
        myRequestsList.setCellFactory(param -> {
            ListCell<FriendRequestVo> cell = new ListCell<FriendRequestVo>() {
                @Override
                protected void updateItem(FriendRequestVo item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                        setContextMenu(null);
                        setGraphic(null);
                        return;
                    }
                    setGraphic(
                            createFriendHboxControl(item)
                    );
                    if (item.getRequestStatus().name().equals(FriendRequestStatusVo.DECLINED.name())) {
                        setContextMenu(createContextMenu(item,
                                Arrays.asList("Send request again"),
                                (it) -> {
                                    sendFriendRequest(it.getReceiver(), (frRequest)
                                            -> setGraphic(
                                            createFriendHboxControl(frRequest)
                                    ));
                                }));
                        return;
                    }
                    setContextMenu(null);
                }

            };
            return cell;
        });
    }

    private void initUserSearchList() {
        userSearchList.setCellFactory(param -> {
            ListCell<UserVo> cell = new ListCell<UserVo>() {
                @Override
                protected void updateItem(UserVo item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                        setGraphic(null);
                        setContextMenu(null);
                        return;
                    }
                    setGraphic(
                            new HBox(createImage("avatar.png"), createLabel(item.getUsername(), statusColorsMap.get(item.getStatus()), 24))
                    );
                    if (!isMyFriend(item)) {
                        setContextMenu(createContextMenu(item,
                                Arrays.asList("Send friend request"),
                                (it) -> {
                                    sendFriendRequest(it);
                                }));
                        return;
                    }
                    setContextMenu(null);
                }
            };
            return cell;
        });
    }

    private void initFriendList() {
        friendsList.setCellFactory(param -> {
            ListCell<UserVo> cell = new ListCell<UserVo>() {
                @Override
                protected void updateItem(UserVo item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                        setGraphic(null);
                        setContextMenu(null);
                        return;
                    }
//                    setGraphic(
//                            new HboxControl("avatar.png", item.getUsername(), statusColorsMap.get(item.getStatus()), 24, Color.BISQUE, getWidth(), getHeight())
//                    );
                    setGraphic(
                            new HBox(createImage("avatar.png"), createLabel(item.getUsername(), statusColorsMap.get(item.getStatus()), 24))
                    );
                }
            };
            cell.setOnMousePressed((MouseEvent event) -> {
                if (!cell.isEmpty()) {
                    selectChat(cell.getItem());
                }
            }
            );
            cell.setAlignment(Pos.BASELINE_CENTER);
            return cell;
        });
    }

    private ImageView createImage(String imageName) {
        ImageView imgView = new ImageView();
        imgView.setImage(new Image(HboxControl.class.getResourceAsStream("../../image/" + imageName)));
        imgView.setFitHeight(50);
        imgView.setFitWidth(50);
        return imgView;
    }

    private boolean isMyFriend(UserVo item) {
        return privateChatsByFriendId.keySet().contains(item.getId());
    }

    private <T> ContextMenu createContextMenu(T value, List<String> menuItemsNames, final Consumer<T>... actions) {
        ContextMenu menu = new ContextMenu();
        for (int i = 0; i < actions.length; i++) {
            MenuItem item = new MenuItem(menuItemsNames.get(i));
            final Consumer<T> action = actions[i];
            item.setOnAction((ActionEvent e) -> {
                action.accept(value);
            });
            menu.getItems().add(item);
        }
        return menu;
    }

    public void initChatSearchList() {
        chatSearchList.setCellFactory(param -> new ListCell<ChatVo>() {
            @Override
            protected void updateItem(ChatVo item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                    setContextMenu(null);
                    return;
                }
                setGraphic(
                        new HBox(createImage("group-chat-icon.png"), createLabel(item.getName(), Color.BLACK, 24))
                );
                setContextMenu(createContextMenu(item,
                        Arrays.asList("Join to chat"),
                        (it) -> {
                            selectChat(it, empty);
                        }));
            }
        });
    }

    private void initGroupChatList() {
        groupChatsList.setCellFactory(param -> {
            ListCell<ChatVo> cell = new ListCell<ChatVo>() {
                @Override
                protected void updateItem(ChatVo item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                        setGraphic(null);
                        setContextMenu(null);
                        return;
                    }
                    setGraphic(
                            new HBox(createImage("group-chat-icon.png"), createLabel(item.getName(), Color.BLACK, 24))
                    );
                }
            };
            cell.setOnMousePressed((MouseEvent event) -> {
                if (!cell.isEmpty()) {
                    selectChat(cell.getItem(), true);
                }
            }
            );

            return cell;
        });
    }

}

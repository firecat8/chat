package com.chat.controller.chat;

import com.chat.app.GUIApp;
import com.chat.messaging.vo.ChatVo;
import com.chat.messaging.vo.ChatTypeVo;
import com.chat.messaging.vo.ParticipantVo;
import com.chat.messaging.vo.UserVo;
import com.chat.messaging.vo.UserStatusVo;
import com.chat.messaging.message.chat.ChatEventResponse;
import com.chat.messaging.message.chat.ChatResponse;
import com.chat.messaging.message.chat.ChatsResponse;
import com.chat.messaging.message.user.FriendRequestResponse;
import com.chat.messaging.message.user.FriendRequestsResponse;
import com.chat.messaging.message.user.UserResponse;
import com.chat.messaging.message.user.UsersResponse;
import com.chat.messaging.vo.ChatEventVo;
import com.chat.messaging.vo.EntityVo;
import com.chat.messaging.vo.FriendRelationshipVo;
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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
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
    private ListView<UserVo> userSearchList;

    @FXML
    private ListView<FriendRequestVo> myRequestsList;

    @FXML
    private ListView<FriendRequestVo> sendRequestsList;

    @FXML
    private TextField searchBar;

    @FXML
    private TextField groupChatName;

    @FXML
    private TextArea messageBox;

    private final DateFormat dateFormater = new SimpleDateFormat("HH:mm:ss");

    private final Map<Long, ChatVo> chats = new HashMap<>();

    private final Map<String, ChatVo> friendChats = new HashMap<>();

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
                    addChats(rsp.getList());
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
            ChatEventVo chatEvent = rsp.getChatEvent();
            // nothing for now
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
        TaskManager.executeTask(TaskFactory.createAcceptFriendRequestTask(
                friendRequest,
                (ChatResponse rsp) -> {
                    friendChats.put(friendRequest.getSender().getUsername(), rsp.getChat());
                    friendsList.getItems().add(friendRequest.getSender());
                },
                (errorResponse) -> {
                    setMessage(errorResponse.getMessage());
                }));
    }

    private void declineFriendRequest(FriendRequestVo friendRequest, Consumer<FriendRequestVo> listener) {
        TaskManager.executeTask(TaskFactory.createDeclineFriendRequestTask(
                friendRequest,
                (FriendRequestResponse rsp) -> {
                    listener.accept(rsp.getFriendRequest());
                },
                (errorResponse) -> {
                    setMessage(errorResponse.getMessage());
                }));
    }

    // Help methods
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
        addFriendList();
        loadFriendRequests();
        setMessage("Sucessfully login " + currentUser.getUsername());
        userName.setText(currentUser.getUsername());
        statusBar.setValue(UserStatusSelectionItemRegistry.INSTANCE.getItem(currentUser.getStatus()));
        setChatPaneVisible();
    }

    private void addChats(List<ChatVo> list) {
        chats.clear();
        list.forEach((chat) -> {
            chats.put(chat.getId(), chat);
        });
        List<ChatVo> groupChats = new ArrayList<>();
        Long currentUserId = currentUser.getId();
        for (ChatVo chat : chats.values()) {
            if (chat.getChatType().equals(ChatTypeVo.GROUP)) {
                groupChats.add(chat);
                continue;
            }
            friendChats.put(findFriend(chat, currentUserId).getUser().getUsername(), chat);
        }
        groupChatsList.setItems(FXCollections.observableArrayList(groupChats));
    }

    private void addFriendList() {
         Set<UserVo> friends=new HashSet<>();
         currentUser.getFriends().forEach((friend) -> {
           //  friends.add(friend.getFriend());
        });
        if (friends.isEmpty()) {
            friendsList.getItems().clear();
            LOGGER.info("NO FRIENDS ;(");
            return;
        }
        friendsList.setItems(FXCollections.observableArrayList(friends));
    }

    public void setMessage(String msg) {
        error.setText(dateFormater.format(Calendar.getInstance().getTime()) + "  " + msg);
    }

    private ParticipantVo findFriend(ChatVo chat, Long currentUserId) {
        return chat.getParticipants().stream().filter(c -> !c.getUser().getId().equals(currentUserId)).findFirst().get();
    }

    private <T extends EntityVo> void clearLists() {
        userSearchList.getItems().clear();
        friendsList.getItems().clear();
        groupChatsList.getItems().clear();
        chatSearchList.getItems().clear();
        myRequestsList.getItems().clear();
        sendRequestsList.getItems().clear();
    }

    private void initLists() {
        initUserSearchList();
        initFriendList();
        initChatList(groupChatsList, null);
        initChatList(chatSearchList,
                (chat) -> {
                    return null;
                });
        initSendFriendRequestList();
        initMyFriendRequestList();
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
                                    declineFriendRequest(item,
                                            (frRequest) -> {
                                                setGraphic(
                                                        createFriendHboxControl(frRequest)
                                                );
                                            }
                                    );
                                }));
                        return;
                    }
                    setContextMenu(null);
                }

            };
            return cell;
        });
    }

    private HboxControl createFriendHboxControl(FriendRequestVo frRequest) {
        return new HboxControl("friend-request-icon.jpg",
                frRequest.getSender().getUsername() + "(" + frRequest.getRequestStatus().name() + ")",
                requestStatusColorsMap.get(frRequest.getRequestStatus()), Color.BISQUE);
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
                            new HboxControl("avatar.png", item.getUsername(), statusColorsMap.get(item.getStatus()), Color.BISQUE)
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
                    setGraphic(
                            new HboxControl("avatar.png", item.getUsername(), statusColorsMap.get(item.getStatus()), Color.BISQUE)
                    );
                }
            };
            return cell;
        });
    }

    private boolean isMyFriend(UserVo item) {
        return friendChats.keySet().contains(item);
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

    public static void initChatList(ListView<ChatVo> chatList, Function<ChatVo, ContextMenu> createContextMenu) {
        chatList.setCellFactory(param -> new ListCell<ChatVo>() {
            @Override
            protected void updateItem(ChatVo item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                    return;
                }
                setGraphic(
                        new HboxControl("group-chat-icon.png", item.getName(), Color.BLACK, Color.BISQUE)
                );
                if (createContextMenu != null) {
                    setContextMenu(createContextMenu.apply(item));
                    return;
                }
                setContextMenu(null);
            }
        });
    }

}

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
import com.chat.messaging.message.user.UserResponse;
import com.chat.messaging.message.user.UsersResponse;
import com.chat.task.TaskFactory;
import com.chat.task.TaskManager;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
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
        TaskManager.executeTask(TaskFactory.createFindFriendTask(searchBar.getText(),
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
        Set<UserVo> friends = currentUser.getFriends();
        if (friends.isEmpty()) {
            LOGGER.info("NO FRIENDS ;(");
            return;
        }
        friendsList.getItems().addAll(friends);
    }

    public void setMessage(String msg) {
        error.setText(dateFormater.format(Calendar.getInstance().getTime()) + "  " + msg);
    }

    private ParticipantVo findFriend(ChatVo chat, Long currentUserId) {
        return chat.getParticipants().stream().filter(c -> !c.getUser().getId().equals(currentUserId)).findFirst().get();
    }

    private void initLists() {
        initUserList(userSearchList, new ArrayList<>());
        initUserList(friendsList, new ArrayList<>());
        initChatList(groupChatsList, chatSearchList);
    }

    private void initUserList(ListView<UserVo> userList, List<String> menuItemsNames, final Consumer<UserVo>... actions) {
        userList.setCellFactory(param -> {
            ListCell<UserVo> cell = new ListCell<UserVo>() {
                @Override
                protected void updateItem(UserVo item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                        setGraphic(null);
                        return;
                    }
                    //item.getStatus()) for color
                    setGraphic(
                            new HboxControl("avatar.png", item.getUsername(), Color.AZURE)
                    );
                }
            };
            ContextMenu menu = createContextMenu(cell, menuItemsNames, actions);
            cell.emptyProperty().addListener((obs, wasEmpty, isNowEmpty)
                    -> cell.setContextMenu(isNowEmpty ? null : menu));
            return cell;
        });
    }

    private <T> ContextMenu createContextMenu(ListCell< T> cell, List<String> menuItemsNames, final Consumer<T>... actions) {
        ContextMenu menu = new ContextMenu();
        for (int i = 0; i < actions.length; i++) {
            MenuItem item = new MenuItem(menuItemsNames.get(i));
            final Consumer<T> action = actions[i];
            item.setOnAction((ActionEvent e) -> {
                action.accept(cell.getItem());
            });
            menu.getItems().add(item);
        }
        return menu;
    }

    public static void initChatList(ListView<ChatVo>... chatLists) {
        for (ListView<ChatVo> chatList : chatLists) {
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
                            new HboxControl("group-chat-icon.png", item.getName(), Color.BLACK)
                    );
                }
            });
        }
    }

}

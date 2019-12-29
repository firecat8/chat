package com.chat.controller;

import com.chat.task.user.LogoutTask;
import com.chat.messaging.message.ResponseListener;
import com.chat.messaging.dto.ChatMessageDto;
import com.chat.messaging.dto.ChatTypeMsgDto;
import com.chat.messaging.dto.ErrorMessageDto;
import com.chat.messaging.dto.ParticipantMessageDto;
import com.chat.messaging.dto.UserMessageDto;
import com.chat.messaging.dto.UserStatusMsgDto;
import com.chat.messaging.message.SuccessResponse;
import com.chat.messaging.message.chat.ChatEventResponse;
import com.chat.messaging.message.chat.ChatResponse;
import com.chat.messaging.message.chat.ChatsResponse;
import com.chat.messaging.message.user.UserResponse;
import com.chat.messaging.message.user.UsersResponse;
import com.chat.task.TaskManager;
import com.chat.task.chat.CreateChatTask;
import com.chat.task.chat.FindChatTask;
import com.chat.task.chat.LoadChatsTask;
import com.chat.task.user.LoginTask;
import com.chat.task.user.RegisterTask;
import com.chat.task.chat.SendMessageTask;
import com.chat.task.user.ChangeStatusTask;
import com.chat.task.user.FindFriendTask;
import com.chat.utils.ListViewUtils;
import com.chat.utils.ResolveUtils;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * Controller for testing
 *
 * @author gdimitrova
 */
public class ChatController implements Initializable {

    public static UserMessageDto currentUser = null;

    @FXML
    private Button loginBtn, logoutBtn, createChatButton, searchFriendBtn, searchChatBtn;

    @FXML
    private TabPane tabPane;

    @FXML
    private Tab friendsTab, groupChatsTab, searchTab;

    @FXML
    private ChoiceBox statusBar;

    @FXML
    private PasswordField passField;

    @FXML
    private Label error, userName;

    @FXML
    private TextField usernameTextField;

    @FXML
    private AnchorPane loginPane, chatPane;

    @FXML
    private ListView<ChatMessageDto> groupChatsList, chatSearchList;

    @FXML
    private ListView<UserMessageDto> friendsList, userSearchList;

    @FXML
    private TextField searchBar, serverChat, groupChatName;

    @FXML
    private TextArea messageBox;

    public static ExecutorService pool = Executors.newFixedThreadPool(30);

    private final DateFormat dateFormater = new SimpleDateFormat("HH:mm:ss");

    private final Map<Long, ChatMessageDto> chats = new HashMap<>();

    private final Map<String, ChatMessageDto> friendChats = new HashMap<>();

    private final Map<String, UserStatusMsgDto> statuses = new HashMap<>();

    private ChatMessageDto currentChat;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ListViewUtils.initUserList(userSearchList, friendsList);
        ListViewUtils.initChatList(groupChatsList, chatSearchList);
        //
        // USER STATUS
        statusBar.setItems(FXCollections.observableArrayList(new ArrayList<>(ResolveUtils.STATUSES.keySet())));
        statusBar.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String oldValue, String newValue) {
                changeStatus();
            }
        });
        // tabs
        tabPane.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Tab> ov, Tab oldValue, Tab newValue) -> {
            if (newValue.equals(friendsTab)) {
                friendsTabVisibility();
                return;
            }
            if (newValue.equals(groupChatsTab)) {
                groupChatsTabVisibility();
                return;
            }
            searchTabVisibility();
        });
        friendsTabVisibility();
        // TODO
    }

    @FXML
    private void createGroupChat() {
        createChat();

    }

    @FXML
    private void searchFriend() {
        findFriend();
    }

    @FXML
    private void searchChat() {
        findChat();
    }

    @FXML
    private void sendMessage() {
        pool.execute(new SendMessageTask(messageBox.getText(), currentUser, currentChat, new ResponseListener<ChatEventResponse>() {
            @Override
            public void onSuccess(ChatEventResponse rsp) {
                //nothing
            }

            @Override
            public void onError(ErrorMessageDto error) {
                Platform.runLater(() -> {
                    setMessage(error.getMessage());
                });
            }
        }));
    }

    @FXML
    private void sendFile() {
        // TODO
    }

    @FXML
    private void login(Event e) {
        login();
    }

    @FXML
    private void logout(Event e) {
        logout();
    }

    // Service task methods
    private void login() {
        TaskManager.login(usernameTextField.getText(), passField.getText(),
                (UserResponse rsp) -> {
                    onLoadedUser(rsp);
                },
                (errorResponse) -> {
                    if (errorResponse.getErrorMessage().contains("Not exist user")) {
                        register();
                        return;
                    }
                    setMessage(errorResponse.getMessage());
                });
    }

    private void logout() {
        TaskManager.logout(currentUser,
                (success) -> {
                    setMessage("Sucessfully logout " + currentUser.getUsername());
                    currentUser = null;
                    setPanesVisibility(true, false);
                },
                (errorResponse) -> {
                    setMessage(errorResponse.getMessage());
                });
    }

    private void register() {
        String username = usernameTextField.getText();
        TaskManager.register(username, passField.getText(), username, username,
                (UserResponse rsp) -> {
                    onLoadedUser(rsp);
                },
                (errorResponse) -> {
                    setMessage(errorResponse.getMessage());
                });
    }

    private void changeStatus() {
        TaskManager.changeStatus(ResolveUtils.resolveStatus(statusBar.getValue().toString()), currentUser,
                (success) -> {
                },
                (errorResponse) -> {
                    setMessage(errorResponse.getMessage());
                });
    }

    private void loadChats() {
        TaskManager.loadChats(currentUser,
                (ChatsResponse rsp) -> {
                    addChats(rsp.getList());
                },
                (errorResponse) -> {
                    setMessage(errorResponse.getMessage());
                });
    }

    private void createChat() {
        TaskManager.createChat(groupChatName.getText(),
                (ChatResponse rsp) -> {
                    ChatMessageDto chat = rsp.getChat();
                    chats.put(chat.getId(), chat);
                    groupChatsList.getItems().add(0, chat);
                },
                (errorResponse) -> {
                    setMessage(errorResponse.getMessage());
                });
    }

    private void findFriend() {
        TaskManager.findFriend(searchBar.getText(),
                (UsersResponse rsp) -> {
                    userSearchList.setItems(FXCollections.observableArrayList(rsp.getList()));
                    userSearchList.setVisible(true);
                    chatSearchList.setVisible(false);
                },
                (errorResponse) -> {
                    setMessage(errorResponse.getMessage());
                });
    }

    private void findChat() {
        TaskManager.findChats(searchBar.getText(),
                (ChatsResponse rsp) -> {
                    chatSearchList.setItems(FXCollections.observableArrayList(rsp.getList()));
                    chatSearchList.setVisible(true);
                    userSearchList.setVisible(false);
                },
                (errorResponse) -> {
                    setMessage(errorResponse.getMessage());
                });
    }

    // Help methods
    private void setPanesVisibility(boolean isloginPane, boolean ischatPane) {
        setPaneVisibility(loginPane, isloginPane);
        setPaneVisibility(chatPane, ischatPane);
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
        statusBar.setValue(ResolveUtils.resolveStatus(currentUser.getStatus()));
        tabVisibility(false);
        setPanesVisibility(false, true);
    }

    private void addChats(List<ChatMessageDto> list) {
        chats.clear();
        list.forEach((chat) -> {
            chats.put(chat.getId(), chat);
        });
        List<ChatMessageDto> groupChats = new ArrayList<>();
        Long currentUserId = currentUser.getId();
        for (ChatMessageDto chat : chats.values()) {
            if (chat.getChatType().equals(ChatTypeMsgDto.GROUP)) {
                groupChats.add(chat);
                continue;
            }
            friendChats.put(findFriend(chat, currentUserId).getUser().getUsername(), chat);
        }
        groupChatsList.setItems(FXCollections.observableArrayList(groupChats));
    }

    private void addFriendList() {
        Set<UserMessageDto> friends = currentUser.getFriends();
        if (friends.isEmpty()) {
            System.out.println("NO FRIENDS");
            return;
        }
        friendsList.getItems().addAll(friends);
    }

    private void groupChatsTabVisibility() {
        tabVisibility(false);
    }

    private void friendsTabVisibility() {
        tabVisibility(false);
    }

    private void searchTabVisibility() {
        tabVisibility(true);
    }

    private void tabVisibility(boolean isSearchTab) {
        createChatButton.setVisible(!isSearchTab);
        groupChatName.setVisible(!isSearchTab);
        groupChatName.setText("");
        searchBar.setVisible(isSearchTab);
        searchBar.setText("");
        searchChatBtn.setVisible(isSearchTab);
        searchFriendBtn.setVisible(isSearchTab);
    }

    public void setMessage(String msg) {
        error.setText(dateFormater.format(Calendar.getInstance().getTime()) + "  " + msg);
    }

    private ParticipantMessageDto findFriend(ChatMessageDto chat, Long currentUserId) {
        return chat.getParticipants().stream().filter(c -> !c.getUser().getId().equals(currentUserId)).findFirst().get();
    }
}

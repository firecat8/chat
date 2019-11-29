package com.chat.controller;

import com.chat.task.LogoutTask;
import com.chat.messaging.message.ResponseListener;
import com.chat.messaging.dto.ChatEventMessageDto;
import com.chat.messaging.dto.ChatMessageDto;
import com.chat.messaging.dto.ErrorMessageDto;
import com.chat.messaging.dto.UserMessageDto;
import com.chat.task.LoginTask;
import com.chat.task.RegisterTask;
import com.chat.task.SendMessageTask;
import com.chat.utils.ListViewUtils;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author gdimitrova
 */
public class ChatController implements Initializable {

    public static UserMessageDto currentUser = null;

    @FXML
    private Button loginBtn, registerBtn, logoutBtn;

    @FXML
    private PasswordField passField, passField1;

    @FXML
    private Label error;

    @FXML
    private TextField usernameTxtF, usernameTxtF1;

    @FXML
    private AnchorPane loginPane, chatPane, regPane;

    @FXML
    private ListView chatPanel, chatSearchList;

    @FXML
    private ListView<UserMessageDto> friendsList, userSearchList;

    @FXML
    private TextField searchBar, serverChat;

    @FXML
    private TextArea messageBox;

    public static ExecutorService pool = Executors.newFixedThreadPool(30);

    private DateFormat date_formater = new SimpleDateFormat("HH:mm:ss");

    private Map<String, ChatMessageDto> chats = new HashMap<>();

    private ChatMessageDto currentChat;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ListViewUtils.initUserList(userSearchList, friendsList);

        // TODO
    }

    @FXML
    private void add() {
        // TODO

    }

    @FXML
    private void searchFriend() {
        // TODO

    }

    @FXML
    private void searchChat() {
        // TODO

    }

    @FXML
    private void sendMessage() {
        pool.execute(new SendMessageTask(messageBox.getText(), currentUser, currentChat, new ResponseListener<ChatEventMessageDto>() {
            @Override
            public void onSuccess(ChatEventMessageDto response) {
                //nothing
            }

            @Override
            public void onError(ErrorMessageDto error) {
                setMessage(error.getMessage());
            }
        }));
    }

    @FXML
    private void sendFile() {
        // TODO
    }

    @FXML
    private void backLoginPane() {
        setPanesVisibility(true, false, false);
    }

    @FXML
    private void onRegPane() {
        setPanesVisibility(false, true, false);
    }

    private void addFriendList() {
        Set<UserMessageDto> friends = currentUser.getFriends();
        if (friends.isEmpty()) {
            System.out.println("NO FRIENDS");
            return;
        }
        friendsList.getItems().addAll(friends);
    }

    @FXML
    private void login(Event e) {
        pool.execute(new LoginTask(usernameTxtF.getText(), passField.getText(), new ResponseListener<UserMessageDto>() {
            @Override
            public void onSuccess(UserMessageDto response) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        currentUser = response;
                        addFriendList();
                        setMessage("Sucessfully login " + response.getUsername());
                        setPanesVisibility(false, false, true);
                    }
                });
            }

            @Override
            public void onError(ErrorMessageDto error) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        setMessage(error.getMessage());
                    }
                });
            }
        }));
    }

    @FXML
    private void logout(Event e) {
        pool.execute(new LogoutTask(currentUser.getUsername(), currentUser.getPassword(), new ResponseListener<UserMessageDto>() {
            @Override
            public void onSuccess(UserMessageDto response) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        currentUser = null;
                        setMessage("Sucessfully logout " + response.getUsername());
                        setPanesVisibility(true, false, false);
                    }
                });
            }

            @Override
            public void onError(ErrorMessageDto error) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        setMessage(error.getMessage());
                    }
                });
            }
        }));
    }

    @FXML
    private void register(Event e) {
        String username = usernameTxtF1.getText();
        String pass = passField1.getText();
        // TODO
        pool.execute(new RegisterTask(null, null, null, null, null, null, null, new ResponseListener<UserMessageDto>() {
            @Override
            public void onSuccess(UserMessageDto response) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        setPanesVisibility(true, false, false);
                        setMessage("Sucessfully registered " + response.getUsername());
                    }
                });
            }

            @Override
            public void onError(ErrorMessageDto error) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        setMessage(error.getMessage());
                    }
                });
            }
        }));
    }

    private void setPanesVisibility(boolean isloginPane, boolean isregPane, boolean ischatPane) {
        setPaneVisibility(loginPane, isloginPane);
        setPaneVisibility(regPane, isregPane);
        setPaneVisibility(chatPane, ischatPane);
    }

    private void setPaneVisibility(AnchorPane pane, boolean visible) {
        pane.setVisible(visible);
        pane.setManaged(visible);
    }

    public void setMessage(String msg) {
        error.setText(date_formater.format(Calendar.getInstance().getTime()) + "  " + msg);
    }
}

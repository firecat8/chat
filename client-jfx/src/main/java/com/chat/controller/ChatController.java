package com.chat.controller;

import com.chat.task.LogoutTask;
import com.chat.bl.service.messaging.ResponseListener;
import com.chat.domain.User;
import com.chat.task.LoginTask;
import com.chat.task.RegisterTask;
import com.chat.utils.ListViewUtils;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author gdimitrova
 */
public class ChatController implements Initializable {

    public static User currentUser = null;

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
    private ListView<User> friendsList, userSearchList;

    @FXML
    private TextField searchBar, serverChat;

    private ExecutorService pool = Executors.newFixedThreadPool(30);

    private DateFormat date_formater = new SimpleDateFormat("HH:mm:ss");

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
    private void backLoginPane() {
        setViewVisibility(true, false, false);
    }

    @FXML
    private void onRegPane() {
        setViewVisibility(false, true, false);
    }

    private void addFriendList() {
        Set<User> friends = currentUser.getFriends();
        if (friends.isEmpty()) {
            System.out.println("NO FRIENDS");
            return;
        }
        friendsList.getItems().addAll(friends);
    }

    @FXML
    private void login(Event e) {
        pool.execute(new LoginTask(usernameTxtF.getText(), passField.getText(), new ResponseListener<User>() {
            @Override
            public void onSuccess(User response) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        currentUser = response;
                        addFriendList();
                        setMessage("Sucessfully login " + response.getUsername());
                        setViewVisibility(false, false, true);
                    }
                });
            }

            @Override
            public void onError(String error) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        setMessage(error);
                    }
                });
            }
        }));
    }

    @FXML
    private void logout(Event e) {
        pool.execute(new LogoutTask(currentUser.getUsername(), currentUser.getPassword(), new ResponseListener<User>() {
            @Override
            public void onSuccess(User response) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        currentUser = null;
                        setMessage("Sucessfully logout " + response.getUsername());
                        setViewVisibility(true, false, false);
                    }
                });
            }

            @Override
            public void onError(String error) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        setMessage(error);
                    }
                });
            }
        }));
    }

    @FXML
    private void register(Event e) {
        pool.execute(new RegisterTask(usernameTxtF1.getText(), passField1.getText(), null, null, null, null, null, new ResponseListener<User>() {
            @Override
            public void onSuccess(User response) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        setViewVisibility(true, false, false);
                        setMessage("Sucessfully registered " + response.getUsername());
                    }
                });
            }

            @Override
            public void onError(String error) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        setMessage(error);
                    }
                });
            }
        }));
    }

    private void setViewVisibility(boolean isloginPane, boolean isregPane, boolean ischatPane) {
        setViewVisibility(loginPane, isloginPane);
        setViewVisibility(regPane, isregPane);
        setViewVisibility(chatPane, ischatPane);
    }

    private void setViewVisibility(AnchorPane pane, boolean visible) {
        pane.setVisible(visible);
        pane.setManaged(visible);
    }

    public void setMessage(String msg) {
        error.setText(date_formater.format(Calendar.getInstance().getTime()) + "  " + msg);
    }
}

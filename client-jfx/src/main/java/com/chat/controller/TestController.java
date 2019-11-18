package com.chat.controller;

import com.chat.task.LogoutTask;
import com.chat.bl.service.messaging.ResponseListener;
import com.chat.domain.User;
import com.chat.task.LoginTask;
import com.chat.task.RegisterTask;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author gdimitrova
 */
public class TestController implements Initializable {

    public static User currentUser = null;

    @FXML
    private Button loginBtn, registerBtn, logoutBtn;

    @FXML
    private PasswordField passField;

    @FXML
    private Label error;

    @FXML
    private TextField usernameTxtF;

    @FXML
    private AnchorPane main, loginPane, chatPane;

    @FXML
    private ListView chatPanel;

    @FXML
    private TextField searchBar, serverChat;

    private ExecutorService pool = Executors.newFixedThreadPool(30);

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
                        setSuccess(response.getUsername());
                        setViewVisibility( false, true);
                    }
                });
            }

            @Override
            public void onError(String error) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        setError(error);
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
                        setSuccess(response.getUsername());
                        setViewVisibility( true, false);
                    }
                });
            }

            @Override
            public void onError(String error) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        setError(error);
                    }
                });
            }
        }));
    }

    @FXML
    private void register(Event e) {
        String username = usernameTxtF.getText();
        String password = passField.getText();
        pool.execute(new RegisterTask(username, password, username, username, username + "@abv.bg", "85528", "Varna", new ResponseListener<User>() {
            @Override
            public void onSuccess(User response) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        setSuccess(response.getUsername());
                    }
                });
            }

            @Override
            public void onError(String error) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        setError(error);
                    }
                });
            }
        }));
    }

    private void setViewVisibility( boolean isloginPane, boolean ischatPane) {
        setViewVisibility(loginPane, isloginPane);
        setViewVisibility(chatPane, ischatPane);
    }

    private void setViewVisibility(AnchorPane pane, boolean visible) {
        pane.setVisible(visible);
        pane.setManaged(visible);
    }

    /*  @FXML
    private void sendMessageOnEnter(KeyEvent event) {
        if (((Control) event.getSource()).getId().equals(serverChat.getId())) {
            if (event.getCode() == KeyCode.ENTER) {
                if (!serverChat.getText().isEmpty()) {
                    Image image = new Image(getClass().getClassLoader().getResource("com/sins/client/gui/images/account.png").toString());
                    ImageView profileImage = new ImageView(image);
                    profileImage.setFitHeight(32);
                    profileImage.setFitWidth(32);
                    BubbleLabel bl6 = new BubbleLabel();

                    bl6.setText(serverChat.getText());
                    bl6.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
                    HBox x = new HBox();
                    bl6.setBubbleSpec(BubbleSpec.FACE_LEFT_CENTER);
                    x.getChildren().addAll(profileImage, bl6);

                    chatPanel.getItems().add(x);

                    serverChat.setText("");
                }
            }
        }

        if (((Control) event.getSource()).getId().equals(messageBox.getId())) {
            if (event.getCode() == KeyCode.ENTER) {
                if (!messageBox.getText().isEmpty()) {
                    Image image = new Image(getClass().getClassLoader().getResource("com/sins/client/gui/images/home2.png").toString());
                    ImageView profileImage = new ImageView(image);
                    profileImage.setFitHeight(32);
                    profileImage.setFitWidth(32);
                    BubbleLabel bl6 = new BubbleLabel();

                    bl6.setText(messageBox.getText());
                    bl6.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN,
                            null, null)));
                    HBox x = new HBox();
                    x.setMaxWidth(chatPanel.getWidth() - 20);
                    x.setAlignment(Pos.TOP_RIGHT);
                    bl6.setBubbleSpec(BubbleSpec.FACE_RIGHT_CENTER);
                    x.getChildren().addAll(bl6, profileImage);

                    chatPanel.getItems().add(x);
                }
                messageBox.setText("");
            }
        }
    }

    @FXML
    private void sendFileButton() {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            userName.setText("File selected: " + selectedFile.getName());
        } else {
            userName.setText("File selection cancelled.");
        }
    }

    @FXML
    private void sendMessageButton() {
        if (!messageBox.getText().isEmpty()) {
            Image image = new Image(getClass().getClassLoader().getResource("com/sins/client/gui/images/home2.png").toString());
            ImageView profileImage = new ImageView(image);
            profileImage.setFitHeight(32);
            profileImage.setFitWidth(32);
            BubbleLabel bl6 = new BubbleLabel();

            bl6.setText(messageBox.getText());
            bl6.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN,
                    null, null)));
            HBox x = new HBox();
            x.setMaxWidth(chatPanel.getWidth() - 20);
            x.setAlignment(Pos.TOP_RIGHT);
            bl6.setBubbleSpec(BubbleSpec.FACE_RIGHT_CENTER);
            x.getChildren().addAll(bl6, profileImage);

            chatPanel.getItems().add(x);
        }
        messageBox.setText("");
        messageBox.requestFocus();
    }*/

    public void setError(String msg) {
        error.setText("ERROR: " + msg);
    }

    public void setSuccess(String msg) {
        error.setText("SUCCESS: " + msg);
    }
}

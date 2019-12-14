package com.chat.controller;

import com.chat.app.GUIApp;
import static com.chat.app.GUIApp.pool;
import com.chat.messaging.dto.ErrorMessageDto;
import com.chat.messaging.dto.UserMessageDto;
import com.chat.messaging.message.ResponseListener;
import com.chat.messaging.message.user.UserResponse;
import com.chat.task.user.LoginTask;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private Label lblSign;

    @FXML
    private TextField txtUserName;

    @FXML
    private TextField txtPass;

    public static UserMessageDto currentUser = null;

    public void Login(ActionEvent event) throws Exception {
        if (txtUserName.getText().equals("user") && txtPass.getText().equals("pass")) {
            lblSign.setText("Login success!");
            pool.execute(new LoginTask(txtUserName.getText(), txtPass.getText(), new ResponseListener<UserResponse>() {
                @Override
                public void onSuccess(UserResponse response) {
                    Platform.runLater(() -> {
                        try {
                            currentUser = response.getUser();
                            GUIApp.changeScene("main");
                        } catch (IOException ex) {
                            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });
                }

                @Override
                public void onError(ErrorMessageDto error) {
                    Platform.runLater(() -> {
                    });
                }
            }));
//		Stage primaryStage=new Stage();
//		Parent root=FXMLLoader.load(GUIApp.class.getResource("main.fxml"));
//		Scene scene = new Scene(root,800,700);
//		//scene.getStylesheets().add(getClass().getResource("main.fxml").toExternalForm());
//		primaryStage.setScene(scene);
//		primaryStage.show();
        } else {
            lblSign.setText("Login failed!");
        }
    }

    public void Register(ActionEvent event) throws Exception {

        GUIApp.changeScene("register");
//	Stage primaryStage=new Stage();
//	Parent root=FXMLLoader.load(GUIApp.class.getResource("register.fxml"));
//	Scene scene = new Scene(root,800,700);
//	//scene.getStylesheets().add(getClass().getResource("register.fxml").toExternalForm());
//	primaryStage.setScene(scene);
//	primaryStage.show();
    }

}
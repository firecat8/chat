package com.chat.controller;

import com.chat.app.GUIApp;
import static com.chat.app.GUIApp.pool;
import com.chat.messaging.dto.ErrorMessageDto;
import com.chat.messaging.message.ResponseListener;
import com.chat.messaging.message.user.UserResponse;
import com.chat.task.user.RegisterTask;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class RegisterController {

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtFamilyName;

    @FXML
    private TextField txtUserName;

    @FXML
    private TextField txtPass;

    @FXML
    private TextField txtConfPass;

    public void Create(ActionEvent event) throws Exception {
        pool.execute(new RegisterTask(txtUserName.getText(), txtPass.getText(), txtName.getText(), txtFamilyName.getText(), new ResponseListener<UserResponse>() {
            @Override
            public void onSuccess(UserResponse response) {
                Platform.runLater(() -> {
                    try {
                        GUIApp.changeScene("main");
                    } catch (IOException ex) {
                        Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
            }

            @Override
            public void onError(ErrorMessageDto error) {
                Platform.runLater(() -> {
                });
            }
        }));

    }

    public void Return(ActionEvent event) throws Exception {
        GUIApp.changeScene("login");
    }

}

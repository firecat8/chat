package com.chat.controller;

import com.chat.app.GUIApp;
import com.chat.messaging.message.user.UserResponse;
import com.chat.task.TaskManager;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        TaskManager.register(txtUserName.getText(), txtPass.getText(), txtName.getText(), txtFamilyName.getText(),
                (UserResponse rsp) -> {
                    try {
                        GUIApp.changeScene("main");
                    } catch (IOException ex) {
                        Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                },
                (errorResponse) -> {
                });

    }

    public void Return(ActionEvent event) throws Exception {
        GUIApp.changeScene("login");
    }

}

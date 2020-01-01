package com.chat.controller;

import com.chat.app.GUIApp;
import com.chat.messaging.vo.UserVo;
import com.chat.messaging.message.user.UserResponse;
import com.chat.task.TaskFactory;
import com.chat.task.TaskManager;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    public static UserVo currentUser = null;

    public void Login(ActionEvent event) throws Exception {
        if (txtUserName.getText().equals("user") && txtPass.getText().equals("pass")) {
            lblSign.setText("Login success!");
            TaskManager.executeTask(TaskFactory.createLoginTask(txtUserName.getText(), txtPass.getText(),
                    (UserResponse rsp) -> {
                        currentUser = rsp.getUser();
                        try {
                            GUIApp.changeScene("main");
                        } catch (IOException ex) {
                            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    },
                    (errorResponse) -> {
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

package com.chat.controller;

import com.chat.app.GUIApp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class MainController {
@FXML
private TextArea txtOtherUserType;
@FXML
private TextArea txtUserType;
@FXML
private TextField txtSearch;
@FXML
private Label txtWhoisType;
@FXML
private ImageView imageSent;
@FXML
private ImageView imageAttach;
@FXML
private ImageView imageCall;
@FXML
private ImageView imageVideoCall;

public void Logout(ActionEvent event) throws Exception {
	Stage primaryStage=new Stage();
	Parent root=FXMLLoader.load(GUIApp.class.getResource("login.fxml"));
	Scene scene = new Scene(root,800,700);
	//scene.getStylesheets().add(getClass().getResource("login.fxml").toExternalForm());
	primaryStage.setScene(scene);
	primaryStage.show();
}

public void SentMessages() {
	
}

public void attachFile() {
	
}
public void CallUser() {
	
}

}

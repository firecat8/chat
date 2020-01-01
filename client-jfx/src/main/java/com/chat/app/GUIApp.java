package com.chat.app;

import com.chat.controller.chat.ChatController;
import com.chat.controller.LoginController;
import com.chat.messaging.dto.UserMessageDto;
import com.chat.task.TaskFactory;
import com.chat.task.TaskManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.stage.WindowEvent;

/**
 * JavaFX App
 */
public class GUIApp extends Application {

    public static Stage stage;

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        GUIApp.stage = stage;
        // Main line
//        scene = new Scene(loadFXML("login"));
        // For testing purpose
        scene = new Scene(loadFXML("chat"));
        stage.setOnCloseRequest((WindowEvent event) -> {
            if (!ClientApp.isConnected()) {
                logout();
                return;
            }
            Platform.exit();
        });
        stage.setScene(scene);
        stage.show();
    }

    public static void changeScene(String controllerName) throws IOException {
        scene = new Scene(loadFXML(controllerName));
        stage.setScene(new Scene(loadFXML(controllerName)));
    }

    private void logout() {
        UserMessageDto currentUser = LoginController.currentUser != null
                ? LoginController.currentUser
                : ChatController.currentUser;
        if (currentUser == null) {
            closeApps();
            return;
        }
        TaskManager.executeTask(TaskFactory.createLogoutTask(currentUser,
                (success) -> {
                    closeApps();
                },
                (errorResponse) -> {
                    closeApps();
                }));
    }

    private void closeApps() {
        try {
            ClientApp.getRegistry().close();
            Platform.exit();
        } catch (Exception ex) {
            Logger.getLogger(GUIApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GUIApp.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void start() {
        System.out.println("Launch JFX App");
        launch();
    }

}

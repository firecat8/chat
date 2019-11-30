package com.chat.app;

import static com.chat.app.ChatApp.registry;
import com.chat.messaging.message.ResponseListener;
import com.chat.controller.ChatController;
import static com.chat.controller.ChatController.pool;
import com.chat.messaging.dto.ErrorMessageDto;
import com.chat.messaging.dto.UserMessageDto;
import com.chat.task.LogoutTask;
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

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("chat"));
        stage.setOnCloseRequest((WindowEvent event1) -> {
            pool.shutdownNow();
            if (registry != null) {
                logout();
                return;
            }
            Platform.exit();
        });
        stage.setScene(scene);
        stage.show();
    }

    private void logout() {
        new LogoutTask(ChatController.currentUser.getUsername(), ChatController.currentUser.getPassword(), new ResponseListener<UserMessageDto>() {
            @Override
            public void onSuccess(UserMessageDto response) {

                try {
                    registry.close();
                    Platform.exit();
                } catch (Exception ex) {
                    Logger.getLogger(GUIApp.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void onError(ErrorMessageDto error) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        }).run();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GUIApp.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void start(String[] args) {
        System.out.println("Launch JFX App");
        launch();
    }

}

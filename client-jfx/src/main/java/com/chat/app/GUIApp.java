package com.chat.app;

import static com.chat.app.ClientApp.registry;
import com.chat.controller.ChatController;
import com.chat.messaging.message.ResponseListener;
import com.chat.controller.LoginController;
import com.chat.messaging.dto.ErrorMessageDto;
import com.chat.messaging.message.SuccessResponse;
import com.chat.task.user.LogoutTask;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.stage.WindowEvent;

/**
 * JavaFX App
 */
public class GUIApp extends Application {

    public static ExecutorService pool = Executors.newFixedThreadPool(30);

    private static Stage stage;

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        GUIApp.stage = stage;
        // Main line
        // scene = new Scene(loadFXML("login"));
        // For testing purpose
        scene = new Scene(loadFXML("chat"));
        stage.setOnCloseRequest((WindowEvent event) -> {
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

    public static void changeScene(String controllerName) throws IOException {
        scene = new Scene(loadFXML(controllerName));
        stage.setScene(new Scene(loadFXML(controllerName)));
    }

    private void logout() {
        Long userId = LoginController.currentUser != null ? LoginController.currentUser.getId()
                : ChatController.currentUser.getId();
        if (userId == null) {
            Platform.exit();
            return;
        }
        new LogoutTask(userId, new ResponseListener<SuccessResponse>() {
            @Override
            public void onSuccess(SuccessResponse response) {
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

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GUIApp.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void start() {
        System.out.println("Launch JFX App");
        launch();
    }

}

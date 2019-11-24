package com.chat.app;

import static com.chat.app.ChatApp.registry;
import com.chat.bl.service.messaging.ResponseListener;
import com.chat.controller.ChatController;
import static com.chat.controller.ChatController.pool;
import com.chat.domain.User;
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
            new LogoutTask(ChatController.currentUser.getUsername(), ChatController.currentUser.getPassword(), new ResponseListener<User>() {
                @Override
                public void onSuccess(User response) {

                    try {
                        registry.close();
                        Platform.exit();
                    } catch (Exception ex) {
                        Logger.getLogger(GUIApp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                @Override
                public void onError(String error) {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            }).run();

        });
        stage.setScene(scene);
        stage.show();
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

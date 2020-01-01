package com.chat.utils;

import java.io.IOException;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;

/**
 *
 * @author gdimitrova
 */
public class FXMLUtils {

    public static Parent loadFXML(String fxml) throws IOException {
        return createFXMLLoader(fxml).load();
    }

    public static FXMLLoader createFXMLLoader(String fxml) {
        return new FXMLLoader(FXMLUtils.class.getResource("../app/"+fxml + ".fxml"));
    }
}

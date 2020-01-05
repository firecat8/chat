package com.chat.controller.chat;

import com.chat.utils.FXMLUtils;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

/**
 *
 * @author gdimitrova
 */
public class HboxControl extends HBox {

    @FXML
    private final ImageView imageView;

    @FXML
    private final Label label;

    public HboxControl(String imageName, String labelText, Color textColor, Color backgroundColor) {
        FXMLLoader fxmlLoader = FXMLUtils.createFXMLLoader("hboxControl");
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.imageView = createImage(imageName);
        this.label = createLabel(labelText, textColor);
        Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);
        setAlignment(Pos.CENTER_LEFT);
        setBackground(new Background(new BackgroundFill(backgroundColor, CornerRadii.EMPTY, Insets.EMPTY)));
        getChildren().addAll(imageView, label, region);
    }

    private ImageView createImage(String imageName) {
        ImageView imgView = new ImageView();
        imgView.setImage(new Image(HboxControl.class.getResourceAsStream("../../image/" + imageName)));
        imgView.setFitHeight(50);
        imgView.setFitWidth(50);
        return imgView;
    }

    private Label createLabel(String labelText, Color color) {
        Label newLabel = new Label(labelText);
        newLabel.setAlignment(Pos.CENTER_LEFT);
        newLabel.setFont(new Font("Verdana", 28));
        newLabel.setTextAlignment(TextAlignment.CENTER);
        newLabel.setTextFill(color);
        return newLabel;
    }
}

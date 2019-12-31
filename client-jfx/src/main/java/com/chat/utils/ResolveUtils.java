package com.chat.utils;

import com.chat.messaging.dto.UserStatusMsgDto;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 *
 * @author gdimitrova
 */
public class ResolveUtils {

    private static final Map<UserStatusMsgDto, Color> STATUS_COLORS = new HashMap<>() {
        {
            put(UserStatusMsgDto.ACTIVE, Color.GREEN);
            put(UserStatusMsgDto.AWAY, Color.YELLOW);
            put(UserStatusMsgDto.DO_NOT_DISTURB, Color.RED);
            put(UserStatusMsgDto.OFFLINE, Color.DARKGRAY);
        }

    };

    public static Color resolveStatusColor(UserStatusMsgDto status) {
        return STATUS_COLORS.get(status);
    }

    public static ImageView resolveImage(String imageName) {
        ImageView imageView = new ImageView();
        imageView.setImage(new Image(ResolveUtils.class.getResourceAsStream("../image/" + imageName)));
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);
        return imageView;
    }

}

package com.chat.utils;

import com.chat.messaging.vo.UserStatusVo;
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

    private static final Map<UserStatusVo, Color> STATUS_COLORS = new HashMap<>() {
        {
            put(UserStatusVo.ACTIVE, Color.GREEN);
            put(UserStatusVo.AWAY, Color.YELLOW);
            put(UserStatusVo.DO_NOT_DISTURB, Color.RED);
            put(UserStatusVo.OFFLINE, Color.DARKGRAY);
        }

    };

    public static Color resolveStatusColor(UserStatusVo status) {
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

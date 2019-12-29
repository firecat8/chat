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

    public static final Map<String, UserStatusMsgDto> STATUSES = new HashMap<>() {
        {
            put(modifiedStatusnName(UserStatusMsgDto.ACTIVE.name()), UserStatusMsgDto.ACTIVE);
            put(modifiedStatusnName(UserStatusMsgDto.AWAY.name()), UserStatusMsgDto.AWAY);
            put(modifiedStatusnName(UserStatusMsgDto.DO_NOT_DISTURB.name()), UserStatusMsgDto.DO_NOT_DISTURB);
            put(modifiedStatusnName(UserStatusMsgDto.OFFLINE.name()), UserStatusMsgDto.OFFLINE);
        }

    };

    private static final Map<UserStatusMsgDto, String> STRING_STATUSES = new HashMap<>() {
        {
            put(UserStatusMsgDto.ACTIVE, modifiedStatusnName(UserStatusMsgDto.ACTIVE.name()));
            put(UserStatusMsgDto.AWAY, modifiedStatusnName(UserStatusMsgDto.AWAY.name()));
            put(UserStatusMsgDto.DO_NOT_DISTURB, modifiedStatusnName(UserStatusMsgDto.DO_NOT_DISTURB.name()));
            put(UserStatusMsgDto.OFFLINE, modifiedStatusnName(UserStatusMsgDto.OFFLINE.name()));
        }

    };

    public static UserStatusMsgDto resolveStatus(String status) {
        return STATUSES.get(status);
    }

    public static String resolveStatus(UserStatusMsgDto status) {
        return STRING_STATUSES.get(status);
    }

    public static ImageView resolveImage(String imageName) {
        ImageView imageView = new ImageView();
        imageView.setImage(new Image(ResolveUtils.class.getResourceAsStream("../image/" + imageName)));
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);
        return imageView;
    }

    private static String modifiedStatusnName(String name) {
        String modified = name.substring(0, 1).concat(name.substring(1).toLowerCase());
        return modified.replace("_", " ");
    }
}

package com.chat.utils;

import com.chat.messaging.dto.ChatMessageDto;
import com.chat.messaging.dto.UserMessageDto;
import com.chat.messaging.dto.UserStatusMsgDto;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

/**
 *
 * @author gdimitrova
 */
public class ListViewUtils {

    private static ImageView groupChatIcon;

    private static ImageView userIcon;

    public static void initUserList(ListView<UserMessageDto>... userLists) {
        if (userIcon == null) {
            userIcon = ResolveUtils.resolveImage("avatar.png");
        }
        for (ListView<UserMessageDto> userList : userLists) {
            userList.setCellFactory(param -> new ListCell<UserMessageDto>() {
                @Override
                protected void updateItem(UserMessageDto item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                        setGraphic(null);
                        return;
                    }
                    setGraphic(createHbox(userIcon, item.getUsername(), item.getStatus()));
                }
            });
        }
    }

    public static void initChatList(ListView<ChatMessageDto>... chatLists) {
        if (groupChatIcon == null) {
            groupChatIcon = ResolveUtils.resolveImage("group-chat-icon.png");
        }
        for (ListView<ChatMessageDto> chatList : chatLists) {
            chatList.setCellFactory(param -> new ListCell<ChatMessageDto>() {
                @Override
                protected void updateItem(ChatMessageDto item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                        setGraphic(null);
                        return;
                    }
                    setGraphic(createHbox(groupChatIcon, item.getName(), Color.BLACK));
                }
            });
        }
    }

    private static HBox createHbox(ImageView icon, String labelText, UserStatusMsgDto status) {
        return createHbox(icon, labelText, ResolveUtils.resolveStatusColor(status));
    }

    private static HBox createHbox(ImageView icon, String labelText, Color color) {
        Label label = new Label(labelText);
        label.setAlignment(Pos.CENTER_LEFT);
        label.setFont(new Font("Verdana", 34));
        label.setTextAlignment(TextAlignment.CENTER);
        label.setTextFill(color);
//        label.set
     //   label.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(20), BorderWidths.FULL)));
        return new HBox(icon, label);
    }

    private static Background createBackground(UserStatusMsgDto status) {
        return new Background(new BackgroundFill(ResolveUtils.resolveStatusColor(status),
                CornerRadii.EMPTY, Insets.EMPTY));
    }
}

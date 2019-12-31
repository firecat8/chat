package com.chat.utils;

import com.chat.messaging.dto.ChatMessageDto;
import com.chat.messaging.dto.UserMessageDto;
import com.chat.messaging.dto.UserStatusMsgDto;
import java.util.List;
import java.util.function.Consumer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
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

    public static void initUserLists(ListView<UserMessageDto> userSearchList, ListView<UserMessageDto> friendsList) {
        if (userIcon == null) {
            userIcon = ResolveUtils.resolveImage("avatar.png");
        }
    }

    private void initUserList(ListView<UserMessageDto> userList, ContextMenu menu) {
        userList.setCellFactory(param -> {
            ListCell<UserMessageDto> cell = new ListCell<UserMessageDto>() {
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
            };
            cell.emptyProperty().addListener((obs, wasEmpty, isNowEmpty)
                    -> cell.setContextMenu(isNowEmpty ? null : menu));
            return cell;
        });
    }

    private <T> ContextMenu createContextMenu(ListCell< T> cell, List<String> menuItemsNames, final Consumer<T>... actions) {
        ContextMenu menu = new ContextMenu();
        for (int i = 0; i < actions.length; i++) {
            MenuItem item = new MenuItem(menuItemsNames.get(i));
            final Consumer<T> action = actions[i];
            item.setOnAction((ActionEvent e) -> {
                action.accept(cell.getItem());
            });
            menu.getItems().add(item);
        }
        return menu;
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
        return new HBox(icon, label);
    }

    private static Background createBackground(UserStatusMsgDto status) {
        return new Background(new BackgroundFill(ResolveUtils.resolveStatusColor(status),
                CornerRadii.EMPTY, Insets.EMPTY));
    }
}

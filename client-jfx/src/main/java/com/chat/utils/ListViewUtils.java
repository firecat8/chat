package com.chat.utils;

import com.chat.messaging.dto.UserMessageDto;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

/**
 *
 * @author gdimitrova
 */
public class ListViewUtils {

    public static void initUserList(ListView<UserMessageDto>... userLists) {
        for (ListView<UserMessageDto> userList : userLists) {
            userList.setCellFactory(param -> new ListCell<UserMessageDto>() {
                @Override
                protected void updateItem(UserMessageDto item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(item.getUsername() + " " + item.getStatus().name());
                    }
                }
            });
        }
    }
}

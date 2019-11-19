package com.chat.utils;

import com.chat.domain.User;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

/**
 *
 * @author gdimitrova
 */
public class ListViewUtils {

    public static void initUserList(ListView<User>... userLists) {
        for (ListView<User> userList : userLists) {
            userList.setCellFactory(param -> new ListCell<User>() {
                @Override
                protected void updateItem(User item, boolean empty) {
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

package com.chat.utils;

import com.chat.messaging.dto.UserMessageDto;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.util.Callback;

/**
 *
 * @author gdimitrova
 */
public class TableViewUtils {

    public static SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

    public static void initTables(ListView<UserMessageDto> friendsList) {

    }

    private static <T> void addMenuItems(ListView<T> table, TableView<T> t, List<String> menuItemsName, Consumer<T>... actions) {
        table.setCellFactory((ListView<T> list) -> null);
        t.setRowFactory((tv) -> {
            final TableRow<T> row = new TableRow<>();
            final T item = row.getItem();
            ContextMenu menu = new ContextMenu();
            List<MenuItem> menuItems = new ArrayList<>();
            int i = 0;
            menuItemsName.stream().map((name) -> new MenuItem(name)).map((menuItem) -> {
                menuItem.setOnAction(e -> {
                    actions[i].accept(item);
                });
                return menuItem;
            }).forEachOrdered((menuItem) -> {
                menuItems.add(menuItem);
            });

            menu.getItems().addAll(menuItems);

            row.emptyProperty().addListener((obs, wasEmpty, isNowEmpty)
                    -> row.setContextMenu(isNowEmpty ? null : menu));

            return row;
        });
    }

}

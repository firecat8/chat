package com.chat.controller.chat;

import com.chat.messaging.vo.UserStatusVo;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author gdimitrova
 */
class UserStatusSelectionItemRegistry {

    public static final UserStatusSelectionItemRegistry INSTANCE = new UserStatusSelectionItemRegistry();

    private final Map<UserStatusVo, UserStatusSelectionItem> itemByStatus = new HashMap<>();

    private final Map<String, UserStatusSelectionItem> itemByStatusLabel = new HashMap<>();

    private UserStatusSelectionItemRegistry() {
        for (UserStatusVo status : UserStatusVo.values()) {
            String statusLabel = createStatusLabel(status);
            UserStatusSelectionItem item = new UserStatusSelectionItem(status, statusLabel);
            itemByStatus.put(status, item);
            itemByStatusLabel.put(statusLabel, item);
        }
    }

    private String createStatusLabel(UserStatusVo status) {
        String name = status.name();
        String modified = name.substring(0, 1).concat(name.substring(1).toLowerCase());
        return modified.replace("_", " ");
    }

    public Collection<UserStatusSelectionItem> getAllItems() {
        return itemByStatus.values();
    }

    public UserStatusSelectionItem getItem(UserStatusVo status) {
        return itemByStatus.get(status);
    }

    public UserStatusSelectionItem getItem(String statusLabel) {
        return itemByStatusLabel.get(statusLabel);
    }
}

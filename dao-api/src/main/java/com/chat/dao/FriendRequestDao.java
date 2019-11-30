package com.chat.dao;

import com.chat.domain.FriendRequest;
import com.chat.domain.User;
import java.util.List;

/**
 *
 * @author gdimitrova
 */
public interface FriendRequestDao extends CrudDao<FriendRequest> {

    public List<FriendRequest> loadSenderRequests(User sender);

    public List<FriendRequest> loadReceiverRequests(User receiver);

    public List<FriendRequest> loadAllRequests(User user);

    public FriendRequest find(User sender, User receiver);
}

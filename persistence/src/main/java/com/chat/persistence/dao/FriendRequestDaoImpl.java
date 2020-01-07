package com.chat.persistence.dao;

import com.chat.dao.FriendRequestDao;
import com.chat.domain.FriendRequest;
import com.chat.domain.User;
import com.chat.persistence.dto.FriendRequestDto;
import com.chat.persistence.dto.UserDto;
import com.chat.persistence.exchanger.FriendRequestDtoExchanger;
import com.chat.persistence.exchanger.UserDtoExchanger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;

/**
 *
 * @author gdimitrova
 */
public class FriendRequestDaoImpl extends AbstractCrudDao<FriendRequestDto, FriendRequest> implements FriendRequestDao {

    public FriendRequestDaoImpl(EntityManager em) {
        super(FriendRequestDto.class, em, FriendRequestDtoExchanger.INSTANCE);
    }

    @Override
    protected Map<String, Object> loadProperties(FriendRequestDto newOne) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public FriendRequest find(User sender, User receiver) {
        Map<String, Object> properties = new HashMap<>();
        properties.put(FriendRequestDto.SENDER, exchangeUser(sender));
        properties.put(FriendRequestDto.RECEIVER, exchangeUser(receiver));
        List<FriendRequest> results = getResults(properties);
        if (results.isEmpty()) {
            return null;
        }
        return results.get(0);
    }

    @Override
    public List<FriendRequest> loadSenderRequests(User sender) {
        return getResults(FriendRequestDto.SENDER, exchangeUser(sender));
    }

    @Override
    public List<FriendRequest> loadReceiverRequests(User receiver) {
        return getResults(FriendRequestDto.RECEIVER, exchangeUser(receiver));
    }

    @Override
    public List<FriendRequest> loadAllRequests(User user) {
        List<FriendRequest> requests = loadSenderRequests(user);
        requests.addAll(loadReceiverRequests(user));
        return requests;
    }

    private UserDto exchangeUser(User user) {
        return new UserDtoExchanger(em).exchange(user);
    }
}

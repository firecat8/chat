package com.chat.persistence.dao;

import com.chat.dao.ChatDao;
import com.chat.domain.Chat;
import com.chat.domain.ChatType;
import com.chat.domain.User;
import com.chat.persistence.dto.ChatDto;
import com.chat.persistence.dto.ChatTypeDto;
import com.chat.persistence.dto.ParticipantDto;
import com.chat.persistence.exchanger.ChatDtoExchanger;
import com.chat.persistence.exchanger.UserDtoExchanger;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

/**
 *
 * @author gdimitrova
 */
public class ChatDaoImpl extends AbstractCrudDao<ChatDto, Chat> implements ChatDao {

    private final UserDtoExchanger userDtoExchanger;

    public ChatDaoImpl(EntityManager em) {
        super(ChatDto.class, em, new ChatDtoExchanger(em));
        userDtoExchanger = new UserDtoExchanger(em);
    }

    @Override
    public Chat save(String name, ChatType type, User user) {
        return save(new ChatDto(name, ChatTypeDto.valueOf(type.name()), new ParticipantDto(userDtoExchanger.exchange(user))));
    }

    @Override
    public List<Chat> findChats(String name) {
        return getResultsLikeExpr(ChatDto.NAME, name);
    }

    @Override
    public List<Chat> loadChats(User user) {
        /*  CriteriaBuilder cb = getCriteriaBuilder();
        CriteriaQuery<ChatDto> query = cb.createQuery(dtoClassName);
        Root<ParticipantDto> pRoot = query.from(ParticipantDto.class);
        query.select(pRoot.get(ParticipantDto.USER));
        query.where(cb.equal(pRoot.get(ParticipantDto.USER), user.getId()));
        return exchangeResults(em.createQuery(query).getResultList());*/

        CriteriaBuilder cb = getCriteriaBuilder();
        CriteriaQuery<ChatDto> query = cb.createQuery(dtoClassName);
        Root<ChatDto> cRoot = query.from(dtoClassName);
        Join join = cRoot.join("participants");
        query.where(cb.equal(join.get(ParticipantDto.USER), user.getId()));
        return exchangeResults(em.createQuery(query).getResultList());
        /*
        CriteriaBuilder cb = getCriteriaBuilder();
        CriteriaQuery<ChatDto> query = cb.createQuery(dtoClassName);
        Root<ChatDto> cRoot = query.from(dtoClassName);
        cRoot.fetch(ChatDto.PARTICIPANTS, JoinType.INNER);
        query.where(cb.equal(cRoot.get(ChatDto.PARTICIPANTS).get(ParticipantDto.USER), user.getId()));
        return exchangeResults(em.createQuery(query).getResultList());
         */

//        CriteriaBuilder cb = getCriteriaBuilder();
//        CriteriaQuery<ChatDto> query = cb.createQuery(dtoClassName);
//        EntityType<ChatDto> entity = em.getMetamodel().entity(dtoClassName);
//        Root<ChatDto> cRoot = query.from(dtoClassName);
//        Join<ChatDto, ParticipantDto> join = cRoot.join(entity.getSet(ChatDto.PARTICIPANTS, ParticipantDto.class));
//        query.where(cb.equal(join.get(ChatDto.PARTICIPANTS).get(ParticipantDto.USER), user.getId()));
//        return exchangeResults(em.createQuery(query).getResultList());
    }

    @Override
    protected Map<String, Object> loadProperties(ChatDto newOne) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

package ru.niyaz.websocket.repository;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.niyaz.websocket.entity.Author;
import ru.niyaz.websocket.entity.Message;

import java.util.Date;
import java.util.List;

/**
 * Created by user on 23.04.2016.
 */

@Repository
public class MessagesRepositoryImpl extends AbstractRepository implements MessagesRepository {

    @Transactional(propagation = Propagation.MANDATORY)
    public void saveMessage(Message message, Long authorID) {
        Session session = getSessionFactory().getCurrentSession();
        message.setAuthor((Author) session.load(Author.class, authorID));
        session.saveOrUpdate(message);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Message> getAllMessages() {
        Session session = getSessionFactory().getCurrentSession();
        return session.createCriteria(Message.class).list();
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Message> getMessagesBeforeDate(Date date) {
        Session session = getSessionFactory().getCurrentSession();
        Criteria criteria = session.createCriteria(Message.class);
        criteria.add(Restrictions.lt("date", date));
        return criteria.list();
    }

}

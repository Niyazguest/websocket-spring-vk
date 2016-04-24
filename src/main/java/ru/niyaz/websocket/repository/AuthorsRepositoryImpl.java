package ru.niyaz.websocket.repository;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.niyaz.websocket.entity.Author;


/**
 * Created by user on 23.04.2016.
 */

@Repository
public class AuthorsRepositoryImpl extends AbstractRepository implements AuthorsRepository {

    @Transactional(propagation = Propagation.MANDATORY)
    public Long saveAuthor(Author author) {
        Session session = getSessionFactory().getCurrentSession();
        session.saveOrUpdate(author);
        return author.getId();
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public Author getAuthorByID(Long id) {
        Session session = getSessionFactory().getCurrentSession();
        Criteria criteria = session.createCriteria(Author.class);
        criteria.add(Restrictions.idEq(id));
        return (Author) criteria.uniqueResult();
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public Author getAuthorByVkID(Integer vkUserID) {
        Session session = getSessionFactory().getCurrentSession();
        Criteria criteria = session.createCriteria(Author.class);
        criteria.add(Restrictions.eq("vkUserID", vkUserID));
        return (Author) criteria.uniqueResult();
    }

}

package eu.immontilla.currencyfair.markettradeprocessor.database.hibernate;

import javax.inject.Inject;

import org.hibernate.SessionFactory;

import com.google.common.base.Optional;

import eu.immontilla.currencyfair.markettradeprocessor.database.GenericDAO;
import eu.immontilla.currencyfair.markettradeprocessor.model.Message;

public class MessageHibernateDAO extends BaseHibernateDAO<Message> implements GenericDAO<Message> {
    @Inject
    public MessageHibernateDAO(SessionFactory factory) {
        super(factory);
    }

    @Override
    public Message create(Message obj) {
        return persist(obj);
    }

    @Override
    public Optional<Message> findById(Long id) {
        return Optional.fromNullable(get(id));
    }

    @Override
    public Message update(Message obj) {
        return persist(obj);
    }

    @Override
    public void delete(Message obj) {
        currentSession().delete(obj);
    }
}

package eu.immontilla.currencyfair.markettradeprocessor.database.hibernate;

import javax.inject.Inject;

import org.hibernate.SessionFactory;

import com.google.common.base.Optional;

import eu.immontilla.currencyfair.markettradeprocessor.database.GenericDAO;
import eu.immontilla.currencyfair.markettradeprocessor.model.User;

/**
 * Hibernate DAO for user model
 * @author immontilla
 *
 */

public class UserHibernateDAO extends BaseHibernateDAO<User> implements GenericDAO<User> {

    @Inject
    public UserHibernateDAO(SessionFactory factory) {
        super(factory);
    }

    @Override
    public User create(User obj) {
        return persist(obj);
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.fromNullable(get(id));
    }

    @Override
    public User update(User obj) {
        return persist(obj);
    }

    @Override
    public void delete(User obj) {
        currentSession().delete(obj);
    }
}

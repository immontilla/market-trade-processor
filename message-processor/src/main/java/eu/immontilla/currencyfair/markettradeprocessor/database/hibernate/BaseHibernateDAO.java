package eu.immontilla.currencyfair.markettradeprocessor.database.hibernate;

import io.dropwizard.hibernate.AbstractDAO;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;

/**
 * Abstract base class for Hibernate DAO classes.
 * @author immontilla
 *
 * @param <T>
 */

abstract public class BaseHibernateDAO<T> extends AbstractDAO<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseHibernateDAO.class);

    public BaseHibernateDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public abstract T create(T obj);

    public abstract Optional<T> findById(Long id);

    @SuppressWarnings("unchecked")
    public List<T> findByParams(Optional<Map<String, Object>> params) {
        Criteria criteria = criteria();
        LOGGER.info("Executing findByParams");
        if (params.isPresent()) {
            Map<String, Object> mapParams = params.get();
            for (Map.Entry<String, Object> entry : mapParams.entrySet()) {
                criteria.add(Restrictions.eq(entry.getKey(), entry.getValue()));
            }
        }
        return criteria.list();
    }

    public abstract T update(T obj);

    public abstract void delete(T obj);

    public long count() {
        return (Long) criteria().setProjection(Projections.rowCount()).uniqueResult();
    }

}

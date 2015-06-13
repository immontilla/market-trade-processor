package eu.immontilla.currencyfair.markettradeprocessor.facade;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import com.google.common.base.Optional;

import eu.immontilla.currencyfair.markettradeprocessor.database.GenericDAO;
import eu.immontilla.currencyfair.markettradeprocessor.model.User;

public class UserFacade implements BaseFacade<User> {
    private GenericDAO<User> dao;

    @Inject
    public UserFacade(GenericDAO<User> dao) {
        this.dao = dao;
    }

    public User create(User model) {
        return dao.create(model);
    }

    public Optional<User> findById(Long id) {
        return dao.findById(id);
    }

    public List<User> findByParams(Optional<Map<String, Object>> params) {
        return dao.findByParams(params);
    }

    public User update(User obj) {
        return dao.update(obj);
    }

    public void delete(User obj) {
        dao.delete(obj);
    }

    public long count() {
        return dao.count();
    }

}

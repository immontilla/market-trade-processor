package eu.immontilla.currencyfair.markettradeprocessor.facade;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import com.google.common.base.Optional;

import eu.immontilla.currencyfair.markettradeprocessor.database.GenericDAO;
import eu.immontilla.currencyfair.markettradeprocessor.model.Message;

public class MessageFacade implements BaseFacade<Message> {
    private GenericDAO<Message> dao;
    
    @Inject
    public MessageFacade(GenericDAO<Message> dao) {
        this.dao = dao;
    }
    
    public Message create(Message model) {
        return dao.create(model);
    }

    public Optional<Message> findById(Long id) {
        return dao.findById(id);
    }

    public List<Message> findByParams(Optional<Map<String, Object>> params) {
        return dao.findByParams(params);
    }

    public Message update(Message obj) {
        return dao.update(obj);
    }

    public void delete(Message obj) {
        dao.delete(obj);
    }

    public long count() {
        return dao.count();
    }
}

package eu.immontilla.currencyfair.markettradeprocessor.database;

import java.util.List;
import java.util.Map;

import com.google.common.base.Optional;

import eu.immontilla.currencyfair.markettradeprocessor.model.BaseModel;

/**
 * Data Access Object template
 * @author immontilla
 *
 * @param <T>
 */
public interface GenericDAO<T extends BaseModel> {
    public T create(T obj);

    public Optional<T> findById(Long id);

    public List<T> findByParams(Optional<Map<String, Object>> params);

    public T update(T obj);

    public void delete(T obj);

    public long count();
}

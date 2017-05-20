package abidien.services;

import abidien.models.IItem;

import java.util.Iterator;
import java.util.List;

/**
 * Created by ABIDIEN on 31/07/2016.
 */
public interface IDataService<K, T> {
    public Class<T> getModelClass();
    public T load(K id);
    public List<T> loadAll();
    public int saveOrUpdate(T model);
    public int update(T model);
    public int delete(T id);
    public void disable(K id);
    public void enable(K id);
}

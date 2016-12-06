package abidien.services;

import abidien.models.IItem;

import java.util.Iterator;
import java.util.List;

/**
 * Created by ABIDIEN on 31/07/2016.
 */
public interface IDataService<T> {
    public T load(int id);
    public List<T> loadAll();
    public int saveOrUpdate(T model);
    public int delete(T id);
}

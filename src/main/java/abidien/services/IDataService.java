package abidien.services;

import abidien.models.IItem;

import java.util.Iterator;
import java.util.List;

/**
 * Created by ABIDIEN on 31/07/2016.
 */
public interface IDataService<T extends IItem> {
    public T load(int id);
    public List<T> loadAll();
    public int save(T model);
    public int update(T model);
    public int delete(int id);
}

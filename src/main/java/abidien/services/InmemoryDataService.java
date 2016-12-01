package abidien.services;

import abidien.models.IItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ABIDIEN on 02/12/2016.
 */
public class InmemoryDataService<K, T extends IItem<K>> implements IDataService<T> {
    final IDataService db;

    public InmemoryDataService(IDataService<T> db) {
        this.db = db;
    }

    public HashMap<K, T> all;

    @Override
    public T load(int id) {
        return all.getOrDefault(id, null);
    }

    @Override
    public List<T> loadAll() {
        return new ArrayList<T>(all.values());
    }

    @Override
    public int saveOrUpdate(T model) {
        int id = db.saveOrUpdate(model);
        index(model);
        return id;
    }

    @Override
    public int delete(int id) {
        db.delete(id);
        all.remove(id);
        return 0;
    }

    public void index(T model) {
        all.put(model.getId(), model);
    }
}

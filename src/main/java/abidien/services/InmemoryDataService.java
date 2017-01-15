package abidien.services;

import abidien.models.IItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by ABIDIEN on 02/12/2016.
 */
public class InmemoryDataService<K, T extends IItem<K>> implements IDataService<K, T> {
    final IDataService db;

    public InmemoryDataService(IDataService<K, T> db) {
        this.db = db;
        all = db.loadAll().stream().collect(Collectors.toMap(T::getId, Function.identity()));
    }

    private Map<K, T> all;

    @Override
    public T load(K id) {
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
    public int delete(T model) {
        db.delete(model);
        all.remove(model.getId());
        return 0;
    }

    public void index(T model) {
        all.put(model.getId(), model);
    }
}

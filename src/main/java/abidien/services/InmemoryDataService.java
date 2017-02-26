package abidien.services;

import abidien.models.IItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by ABIDIEN on 02/12/2016.
 */
public class InmemoryDataService<K, T extends IItem<K>> implements IDataService<K, T> {
    final IDataService db;
    public final Object reference;

    public InmemoryDataService(IDataService<K, T> db) {
        this(db, null);
    }

    public InmemoryDataService(IDataService<K, T> db, Object reference) {
        this.db = db;
        this.reference = reference;
        all = new HashMap<K, T>();
        for (T model: db.loadAll())
            index(model);
        //all = db.loadAll().stream().collect(Collectors.toMap(T::getId, Function.identity()));
    }

    private Map<K, T> all;

    @Override
    public T load(K id) {
        return all.getOrDefault(id, null);
    }

    @Override
    public List<T> loadAll() {
        return all.values().stream().filter(v -> !v.getDisable()).collect(Collectors.toList());
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

    @Override
    public void disable(K id) {
        db.disable(id);
        load(id).setDisable(true);
    }

    @Override
    public void enable(K id) {
        db.enable(id);
        load(id).setDisable(false);
    }

    public void index(T model) {
        all.put(model.getId(), model);
    }

    @Override
    public Class<T> getModelClass() {
        return db.getModelClass();
    }
}

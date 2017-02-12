package abidien.services;

import abidien.models.IItem;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ABIDIEN on 31/07/2016.
 */
public class DatabaseService<K, T extends IItem<K>> implements IDataService<K, T> {
    private final Class<T> clazz;
    public HashMap<K, T> all;

//    Class<T> persistentClass = (Class<T>) ((ParameterizedType) getClass()
//    .getGenericSuperclass()).getActualTypeArguments()[0];

    public DatabaseService(Class<T> clazz) {
        this.clazz = clazz;
        all = new HashMap<>();
        for (T model: loadAllFromDB()) {
            index(model);
        }
    }

    public void index(T model) {
        all.put(model.getId(), model);
    }

    @Override
    public T load(K id) {
        return all.getOrDefault(id, null);
    }

    /*public T loadFromDB(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        T p = (T)session.get(clazz, id);
        session.close();
        return p;
    }*/

    @Override
    public List<T> loadAll() {
        return new ArrayList<T>(all.values());
    }

    public List<T> loadAllFromDB() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = String.format("from %s", clazz.getName());
        Query query = session.createQuery(hql);
        List<T> ds = query.list();
        session.close();
        return ds;
    }

    @Override
    public int saveOrUpdate(T model) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            session.saveOrUpdate(model);
            transaction.commit();
            index(model);
        } catch (HibernateException ex) {
            //Log the exception
            if (transaction != null)
                transaction.rollback();
            System.err.println(ex);
        } finally {
            session.close();
        }
        return 0;
    }

    @Override
    public int delete(T model) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            session.delete(model);
            transaction.commit();
        } catch (HibernateException ex) {
            //Log the exception
            transaction.rollback();
            System.err.println(ex);
        } finally {
            session.close();
        }
        return 0;
    }

    @Override
    public void disable(K id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            String hqlUpdate = "update " + clazz.getName() + " as c set c.disable = true where c.id = :id";
            int updatedEntities = session.createQuery(hqlUpdate)
                    .setParameter("id", id)
                    .executeUpdate();
            transaction.commit();
        } catch (HibernateException ex) {
            //Log the exception
            transaction.rollback();
            System.err.println(ex);
        } finally {
            session.close();
        }
        return;
    }

    @Override
    public void enable(K id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            String hql = String.format("from %s", clazz.getName());
            String hqlUpdate = "update " + clazz.getName() + " c set c.disable = false where c.id = :id";
            int updatedEntities = session.createQuery( hqlUpdate )
                    .setParameter("id", id)
                    .executeUpdate();
            transaction.commit();
        } catch (HibernateException ex) {
            //Log the exception
            transaction.rollback();
            System.err.println(ex);
        } finally {
            session.close();
        }
        return;
    }

    @Override
    public Class<T> getModelClass() {
        return clazz;
    }
}

package abidien.services;

import abidien.models.IItem;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.lang.reflect.ParameterizedType;
import java.util.Iterator;
import java.util.List;

/**
 * Created by ABIDIEN on 31/07/2016.
 */
public class DatabaseService<T> implements IDataService<T> {
    private final Class<T> clazz;
    public DatabaseService(Class<T> clazz) {
        this.clazz = clazz;
    }

//    Class<T> persistentClass = (Class<T>) ((ParameterizedType) getClass()
//    .getGenericSuperclass()).getActualTypeArguments()[0];

    @Override
    public T load(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        T p = (T)session.get(clazz, id);
        session.close();
        return p;
    }

    @Override
    public List<T> loadAll() {
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
    public int delete(int id) {
        return 0;
    }
}

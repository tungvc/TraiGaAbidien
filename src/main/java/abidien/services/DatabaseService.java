package abidien.services;

import abidien.models.IItem;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ABIDIEN on 31/07/2016.
 */
public class DatabaseService<K extends Serializable, T extends IItem<K>> implements IDataService<K, T> {
    private final Class<T> clazz;

    public DatabaseService(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public T load(K id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            T p = (T) session.get(clazz, id);
            return p;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            session.close();
        }
        return null;
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
            if (transaction != null)
                transaction.rollback();
            System.err.println(ex);
        } finally {
            session.close();
        }
        return 0;
    }

    @Override
    public int update(T model) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            session.update(model);
            transaction.commit();
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

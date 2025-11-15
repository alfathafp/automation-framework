package core.db;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public interface CrudDaoInterface<T> {
    SessionFactory getSessionFactory();
    Class<T> getEntityClass();

    default T save(T obj) {
        try (Session session = getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(obj);
            tx.commit();
            return obj;
        }
    }

    default T update(T obj) {
        try (Session session = getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            T merged = session.merge(obj);
            tx.commit();
            return merged;
        }
    }

    default void delete(T obj) {
        try (Session session = getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.remove(obj);
            tx.commit();
        }
    }

    default T findById(Object id) {
        try (Session session = getSessionFactory().openSession()) {
            return session.find(getEntityClass(), id);
        }
    }
}

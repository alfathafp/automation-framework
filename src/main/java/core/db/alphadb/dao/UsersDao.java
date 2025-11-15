package core.db.alphadb.dao;

import core.db.CrudDaoInterface;
import core.db.alphadb.entity.UsersEntity;
import core.db.utils.HibernateCoreUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class UsersDao implements CrudDaoInterface<UsersEntity> {
    private final String dbId = "alphadb";
    private final SessionFactory sessionFactory;
    public UsersDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public UsersEntity findUserById(Integer userId) {
        // Implementation to find user by ID
        try (Session session = HibernateCoreUtils.getSession(dbId)){
            String sql = "FROM UsersEntity WHERE userId = :userId";
            return session.createQuery(sql, UsersEntity.class)
                    .setParameter("userId", userId)
                    .uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Placeholder return
        }
    }

    @Override
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Override
    public Class<UsersEntity> getEntityClass() {
        return UsersEntity.class;
    }
}

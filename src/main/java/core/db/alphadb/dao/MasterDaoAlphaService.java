package core.db.alphadb.dao;

import com.mysql.cj.Session;
import core.db.utils.HibernateCoreUtils;
import lombok.Getter;
import org.hibernate.SessionFactory;

import java.io.IOException;

@Getter
public class MasterDaoAlphaService {
    private final String dbId = "alphadb";
    private final UsersDao usersDao;
    private static MasterDaoAlphaService masterDaoAlphaService;

    public MasterDaoAlphaService() throws IOException {
        SessionFactory sessionFactory = HibernateCoreUtils.getSessionFactory(dbId);
        this.usersDao = new UsersDao(sessionFactory);
    }
}

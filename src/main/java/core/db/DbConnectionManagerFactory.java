package core.db;

import core.db.alphadb.dao.MasterDaoAlphaService;
import lombok.SneakyThrows;

public class DbConnectionManagerFactory {
    private static MasterDaoAlphaService masterDaoAlphaService;

    public static synchronized MasterDaoAlphaService getMasterDaoAlphaService() throws Exception {
        if (masterDaoAlphaService == null) {
            masterDaoAlphaService = new MasterDaoAlphaService();
        }
        return masterDaoAlphaService;
    }
}

package core.db.utils;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import core.db.RootDbConfig;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import tools.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class HibernateCoreUtils {
    public static SessionFactory buildSessionFactory(String dbId) throws IOException {

        log.info("Building SessionFactory for DB ID: {}", dbId);

        ObjectMapper objectMapper = new ObjectMapper();
        RootDbConfig rootDbConfig = objectMapper.readValue(
                new File("src/main/resources/db-configuration.json"),
                RootDbConfig.class
        );

        // Ambil config sesuai dbId
        RootDbConfig.DbConfig dbConfig = rootDbConfig.getConnectionDetails().stream()
                .filter(config -> config.getDbId().equals(dbId))
                .findFirst()
                .orElse(null);

        if (dbConfig == null) {
            throw new RuntimeException("DB config not found for ID: " + dbId);
        }

        // --- Hibernate config ---
        Configuration configuration = new Configuration();

        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        configuration.setProperty("hibernate.show_sql", "false");
        configuration.setProperty("hibernate.format_sql", "false");

        // --- HikariCP Configuration ---
        HikariConfig hikari = new HikariConfig();
        hikari.setJdbcUrl(
                "jdbc:mysql://" +
                        dbConfig.getHostName() + ":" +
                        dbConfig.getPortNumber() + "/" +
                        dbConfig.getDbName()
        );

        hikari.setUsername(System.getenv(dbConfig.getUsername()));
        hikari.setPassword(System.getenv(dbConfig.getPassword()));

        System.out.println("=== DBCONFIG LOADED ===");
        System.out.println("dbId = " + dbConfig.getDbId());
        System.out.println("hostname = " + dbConfig.getHostName());
        System.out.println("port = " + dbConfig.getPortNumber());
        System.out.println("dbname = " + dbConfig.getDbName());
        System.out.println("username FIELD = " + dbConfig.getUsername());
        System.out.println("password FIELD = " + dbConfig.getPassword());



        hikari.setMaximumPoolSize(10);
        hikari.setMinimumIdle(2);
        hikari.setPoolName("Pool-" + dbId);

        HikariDataSource dataSource = new HikariDataSource(hikari);

        // Register entity secara manual / via scan
        configuration.addPackage("core.db.alphadb.entity");
        configuration.addAnnotatedClass(core.db.alphadb.entity.UsersEntity.class);

        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .applySetting("hibernate.connection.datasource", dataSource)
                .applySettings(configuration.getProperties())
                .build();

        return configuration.buildSessionFactory(registry);
    }

    private static Map<String, SessionFactory> sessionFactoryMap = new ConcurrentHashMap<>();
    public static synchronized void initializedDbUtils(String dbId) throws IOException {
        if (!sessionFactoryMap.containsKey(dbId)) {
            log.info("Initializing DB Utils for DB ID: {}", dbId);
            SessionFactory sessionFactory = buildSessionFactory(dbId);
            if (sessionFactory != null) {
                sessionFactoryMap.put(dbId, sessionFactory);
            } else {
                throw new RuntimeException("Failed to build SessionFactory for DB ID: " + dbId);
            }
        }
    }

    public static SessionFactory getSessionFactory(String dbId) throws IOException {
        if (!sessionFactoryMap.containsKey(dbId)) {
            log.info("SessionFactory for DB ID: {} not found. Initializing...", dbId);
            initializedDbUtils(dbId);
        } else {
            log.info("Retrieving existing SessionFactory for DB ID: {}", dbId);
        }
        return sessionFactoryMap.get(dbId);
    }

    public static Session getSession(String dbId) throws IOException {
        SessionFactory sessionFactory = getSessionFactory(dbId);
        if (sessionFactory != null) {
            return sessionFactory.openSession();
        } else {
            throw new RuntimeException("SessionFactory is not initialized for DB ID: " + dbId);
        }
    }

    public static void closeAllSessionFactories() {
        sessionFactoryMap.values().forEach(SessionFactory::close);
        sessionFactoryMap.clear();
        log.info("All SessionFactories have been closed.");
    }
}

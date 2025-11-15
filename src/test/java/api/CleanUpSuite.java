package api;

import org.testng.annotations.AfterMethod;

public class CleanUpSuite {
    @AfterMethod
    public void closeDbConnections() {
        // Implement logic to close database connections if needed
//        HibernateCoreUtils.closeAllSessionFactories();
        System.out.println("Cleaning up resources after test suite execution.");
    }
}

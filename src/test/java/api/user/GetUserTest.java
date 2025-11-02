package api.user;

import core.collection.UserApiCollection;
import core.config.ApiConfig;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GetUserTest {
    @Test
    public void testApiGetUserDetail(){
        UserApiCollection userApiCollection = new UserApiCollection(ApiConfig.requestSpecBase());
        Response userData = userApiCollection.getUserData();
        Assert.assertEquals(userData.getStatusCode(), 200, "status code expected to be 200");
    }
}

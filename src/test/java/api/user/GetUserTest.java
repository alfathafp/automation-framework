package api.user;

import com.google.gson.*;
import core.collection.UserApiCollection;
import core.config.ApiConfig;
import core.utils.JsonUtils;
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

    @Test
    public void validateLength(){
        UserApiCollection userApiCollection = new UserApiCollection(ApiConfig.requestSpecBase());
        Response userData = userApiCollection.getUserData();

        JsonArray jsonArray = JsonUtils.convertToJsonArray(userData.getBody().asString());
        jsonArray.forEach(arr -> {
            Integer userId = arr.getAsJsonObject().get("user_id").getAsInt();
            System.out.println(userId);
        });
    }
}

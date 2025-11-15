package api.user.tests;

import api.CleanUpSuite;
import com.google.gson.*;
import core.collection.UserApiCollection;
import core.config.ApiConfig;
import core.db.DbConnectionManagerFactory;
import core.db.alphadb.entity.UsersEntity;
import core.utils.JsonUtils;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GetUserTest extends CleanUpSuite {
    @Test
    public void testApiGetUserDetail() throws Exception {

        UserApiCollection userApiCollection = new UserApiCollection(ApiConfig.requestSpecBase());

        Response userData = userApiCollection.getUserData();

        JsonArray jsonArray = JsonUtils.convertToJsonArray(userData.getBody().asString());
        Assert.assertEquals(userData.getStatusCode(), 200, "status code expected to be 200");

        // assertion to validte api response with db data
        jsonArray.forEach(
                arr -> {
                    Integer userId = arr.getAsJsonObject().get("user_id").getAsInt();
                    UsersEntity usersEntity;
                    try {
                        usersEntity = DbConnectionManagerFactory.getMasterDaoAlphaService().getUsersDao().findUserById(userId);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    Assert.assertEquals(arr.getAsJsonObject().get("username").getAsString(), usersEntity.getUsername(), "username should match");
                    Assert.assertEquals(arr.getAsJsonObject().get("email").getAsString(), usersEntity.getEmail(), "email should match");
                }
        );
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

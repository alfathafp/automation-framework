package core.collection;

import core.api.ApiEndpoints;
import core.api.RestClient;
import core.config.ApiConfig;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class UserApiCollection extends RestClient {
    public UserApiCollection(RequestSpecification requestSpec) {
        super(requestSpec);
    }

    public Response getUserData() {
        return standarGet(ApiEndpoints.GET_USER);
    }

}

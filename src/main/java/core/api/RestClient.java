package core.api;

import core.config.ApiConfig;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class RestClient {
    private final RequestSpecification requestSpec;

    public RestClient(RequestSpecification requestSpec) {
        this.requestSpec = requestSpec;
    }

    public Response standarGet(String endpoint) {
        return given(requestSpec)
                .when()
                .get(endpoint)
                .then()
                .extract()
                .response();
    }
}

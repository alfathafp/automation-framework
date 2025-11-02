package core.config;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ApiConfig {
    static Properties props = new Properties();
    static {
        try (InputStream input = ApiConfig.class.getClassLoader().getResourceAsStream("api.properties")) {
            props.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load properties", e);
        }
    }

    public static RequestSpecification requestSpecBase() {
        return new RequestSpecBuilder()
                .setBaseUri(props.getProperty("base.url"))
                .addHeader("Content-Type", "application/json")
                .build();
    }

}

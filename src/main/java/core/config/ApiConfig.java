package core.config;

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
    // host
    public static String getBaseUrl() {
        return props.getProperty("base.url");
    }

    // 👇 tambahin main method cuma buat ngetes
    public static void main(String[] args) {
        System.out.println("Base URL: " + getBaseUrl());
    }
}

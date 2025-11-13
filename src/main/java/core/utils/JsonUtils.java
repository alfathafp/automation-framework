package core.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.restassured.response.Response;

public class JsonUtils {
    public static JsonObject convertToJsonObject(String response){
        return JsonParser.parseString(response).getAsJsonObject();
    }

    public static JsonArray convertToJsonArray(String response){
        return JsonParser.parseString(response).getAsJsonArray();
    }
}

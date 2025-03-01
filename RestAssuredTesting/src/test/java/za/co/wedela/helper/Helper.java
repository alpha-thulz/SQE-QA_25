package za.co.wedela.helper;

import com.google.gson.Gson;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class Helper {

    public static Response deleteResponse(String endpoint) {
        return deleteResponse(endpoint, null);
    }

    public static Response deleteResponse(String endpoint, String token) {
        return given()
                .contentType("application/json")
                .header("Authorization", token == null ? "" : "Bearer " + token)
                .delete(endpoint);
    }

    public static Response postResponse(Object body, String endpoint) {
        return postResponse(body, endpoint, null);
    }

    public static Response postResponse(Object body, String endpoint, String token) {
        return given()
                .contentType("application/json")
                .header("Authorization", token == null ? "" : "Bearer " + token)
                .body(new Gson().toJson(body))
                .post(endpoint);
    }

    public static Response getResponse(String endpoint) {
        return getResponse(endpoint, null);
    }

    public static Response getResponse(String endpoint, String token) {
        return given()
                .contentType("application/json")
                .header("Authorization", token == null ? "" : "Bearer " + token)
                .get(endpoint);
    }

    public static Response patchResponse(Object body, String endpoint) {
        return patchResponse(body, endpoint, null);
    }

    public static Response patchResponse(Object body, String endpoint, String token) {
        return given()
                .contentType("application/json")
                .header("Authorization", token == null ? "" : "Bearer " + token)
                .body(new Gson().toJson(body))
                .patch(endpoint);
    }
}

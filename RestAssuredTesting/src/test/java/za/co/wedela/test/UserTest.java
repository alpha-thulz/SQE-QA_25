package za.co.wedela.test;

import com.github.javafaker.Faker;
import com.google.gson.Gson;
import io.restassured.response.Response;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import za.co.wedela.base.BaseSet;
import za.co.wedela.helper.Helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserTest extends BaseSet {

    private final Faker faker = new Faker();
    private final Map<String, String> user1 = Map.of(
            "username", faker.name().username(),
            "password", faker.internet().password(),
            "email", faker.internet().emailAddress(),
            "role", "ADMIN"
    );

    @Order(1)
    @Test
    public void testRegisterUser() {
        Response response = Helper.postResponse(user1, "/api/v1/users/auth/register");

        response.then().assertThat().statusCode(201);
        assertEquals(201, response.statusCode(), "Wrong status code");
        assertEquals("application/json", response.header("Content-Type"), "Wrong content type");
        assertTrue(response.andReturn().as(Map.class).containsKey("access_token"));
        assertTrue(response.andReturn().as(Map.class).get("access_token") instanceof String);
    }

    @Order(2)
    @Test
    public void testLoginUser() {
        Response response = Helper.postResponse(user1, "/api/v1/users/auth/login");

        assertEquals(200, response.statusCode(), "Wrong status code");
        assertEquals("application/json", response.header("Content-Type"), "Wrong content type");
        assertTrue(response.andReturn().as(Map.class).containsKey("access_token"));
        assertTrue(response.andReturn().as(Map.class).get("access_token") instanceof String);
    }

    @Test
    public void testGetAllUsers() {
        Response response = Helper.getResponse("/api/v1/users", token);

        assertEquals(200, response.statusCode(), "Wrong status code");
        assertNotNull(response.getBody());
        assertTrue(response.getBody().as(ArrayList.class) instanceof List, "Wrong content type");
        Map<?, ?> user = (Map<?, ?>) response.getBody().as(ArrayList.class).get(0);
        assertTrue(!(user.get("id") == null && user.get("id").toString().isBlank()), "ID should not be null");
        assertNotNull(user.get("username"), "Username should not be null");
        assertTrue(response.getTime() < 200, "API took more than 200ms");
    }

    @Test
    public void testGetDeleteUser() {
        Response response = Helper.getResponse("/api/v1/users", token);
        ArrayList<?> users = (ArrayList<?>) response.getBody().as(ArrayList.class);
        assertFalse(users.isEmpty(), "Users should not be empty");

        Object user = users.get(new Random().nextInt(users.size()));
        Map<?, ?> userMap = (Map<?, ?>) new Gson().fromJson(new Gson().toJson(user), Map.class);

        String id = userMap.get("id").toString();
        response = Helper.deleteResponse("/api/v1/users/" + id, token);

        assertEquals(204, response.statusCode(), "Expected a 204 response");
        assertTrue(response.getTime() < 200, "API took more than 200ms");
    }
}
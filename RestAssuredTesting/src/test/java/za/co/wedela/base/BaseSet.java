package za.co.wedela.base;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import za.co.wedela.helper.Helper;

import java.util.Map;

public class BaseSet {

    public String token;
    private Faker faker = new Faker();

    @BeforeAll
    public static void beforeAll() {
        RestAssured.baseURI = "http://localhost:8080";
    }

    @BeforeEach
    public void beforeEach() {
        Response response = Helper.postResponse(Map.of(
                "username", faker.name().username(),
                "password", faker.internet().password(),
                "email", faker.internet().emailAddress(),
                "role", "GUEST"
        ), "/api/v1/users/auth/register");
        token = response.andReturn().as(Map.class).get("access_token").toString();
    }
}

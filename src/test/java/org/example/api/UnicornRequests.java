package org.example.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;
import org.example.api.models.Unicorn;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasKey;

public class UnicornRequests {
    public static Unicorn createUnicorn(Unicorn unicorn) {
        String unicornJson;
        try {
            unicornJson = new ObjectMapper().writeValueAsString(unicorn);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return given()
                .body(unicornJson)
                .contentType(ContentType.JSON)
                .when()
                .post("https://crudcrud.com/api/31ed61ebe0514fc2acc01e65f6d3efff/unicorn")
                .then()
                .assertThat()
                .statusCode(201)
                .body("$", hasKey("_id"))
                .extract().as(Unicorn.class, ObjectMapperType.GSON);
    }

    public static void updateTail(Unicorn unicorn) {
               String unicornJson;
        try {
            unicornJson = new ObjectMapper().writeValueAsString(unicorn);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }


        given()
                .body(unicornJson)
                .contentType(ContentType.JSON)
                .when()
                .put("/unicorn/" + unicorn.getId())
                .then()
                .assertThat()
                .statusCode(200);
    }

    public static void deleteUnicorn(String id) {
        given().delete("/unicorn/" + id)
                .then()
                .assertThat()
                .statusCode(200);
    }
}

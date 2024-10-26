package org.example;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;

public class UnicornTest {
    @BeforeAll
    public static void setupTests() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        RestAssured.baseURI = "https://crudcrud.com/api/80a1a72808fd4adbb27d100d13a5c3be";
    }

    @Test
    public void testunicorn() {
    //Шаг 1. Создание единорога
    String id = UnicornRequests.createUnicorn("{\n" + "\"name\": \"Uni\", \n" + "\"colortail\": \"pink\"}");

    //Шаг 2. Изменение цвета хвоста
    UnicornRequests.updateTail(id, "{\n" + "\"colortail\": \"violet\"}");

    //Щаг 3. Проверяем, что цвет хвоста изменился
        given()
                .get("/unicorn/" + id)
                .then()
                .assertThat()
                .body("colortail", equalTo("violet"))
                .statusCode(200);

    //Шаг 4. Удаление единорога
    UnicornRequests.deleteUnicorn(id);

    //Шаг 5. Проверяем, что единорога больше не существует(((
        given()
                .get("/unicorn/" + id)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_NOT_FOUND);
        }
}
package org.example;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.apache.http.HttpStatus;
import org.example.api.UnicornRequests;
import org.example.api.models.Unicorn;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


public class UnicornTest {
    @BeforeAll
    public static void setupTests() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        RestAssured.baseURI = "https://crudcrud.com/api/31ed61ebe0514fc2acc01e65f6d3efff";
    }

    @Test
    public void testunicorn() {
    //Шаг 1. Создание единорога
        Unicorn unicorn = Unicorn.builder().name("Uni").colorTail("pink").build();
        Unicorn createdUnicorn = UnicornRequests.createUnicorn(unicorn);

    //Шаг 2. Изменение цвета хвоста
       createdUnicorn.setColorTail("violet");
        UnicornRequests.updateTail(createdUnicorn);

    //Щаг 3. Проверяем, что цвет хвоста изменился
        given()
                .get("/unicorn/" + createdUnicorn.getId())
                .then()
                .assertThat()
                .body("colorTail", equalTo("violet"))
                .statusCode(200);

    //Шаг 4. Удаление единорога
    UnicornRequests.deleteUnicorn(createdUnicorn.getId());

    //Шаг 5. Проверяем, что единорога больше не существует(((
        given()
                .get("/unicorn/" + createdUnicorn.getId())
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_NOT_FOUND);
        }
}
package com.acme.dbo.restAssured;

import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.is;


public class ClientTest {

    private RequestSpecification request;

    @BeforeEach
    public void setRequest() {
        request = given()
                .baseUri("http://localhost")
                .port(8080)
                .basePath("/dbo/api/")
                .header("X-API-VERSION", 1)
                .filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

    @Test
    @DisplayName("Проверка GET client by id ")
    public void shouldGetClientByExistsId() {
        request
                .when()
                .get("/client/{id}", 1)
                .then()
                .statusCode(SC_OK)
                .body("id", is(1), "login", is("admin@acme.com"));
    }

    @Test
    @DisplayName("Проверка удаления информации")
    public void shouldDeleteClient() {
        request
                .when()
                .body("{" +
                        "\"login\": \"mmm@email.com\",\n" +
                        "\"salt\": \"somesalt\",\n" +
                        "\"secret\": \"749f09bade8aca7556749f09bade8aca7555\"\n" +
                        "}")
                .post("/client")
                .then().statusCode(SC_OK);}
/*
    }

    @Test
    @DisplayName("Проверка по ид, которого нет")
    public void shouldGetClientByNotExistsId() {
        request
                .when()
                .get(CLIENT_ID, 999)
                .then()
                .statusCode(SC_NOT_FOUND);
    }*/

    @Test
    @DisplayName("Удаление информации по ид, которого нет")
    public void negativeShouldDeleteClient() {
        request
                .when()
                .delete("/client/{id}", 7)
                .then()
                .statusCode(SC_INTERNAL_SERVER_ERROR);
    }
}


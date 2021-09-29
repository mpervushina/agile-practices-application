package com.acme.dbo.restAssured;

import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.is;

public class ClientTest {

    private RequestSpecification request;
    private final String BASE_URI = "http://localhost";
    private final String DBO_API = "/dbo/api/";
    private final int PORT = 8080;
    private final String X_API_VERSION = "X-API-VERSION";

    @BeforeEach
    public void getRequest() {
        request = given()
                .baseUri(BASE_URI)
                .port(PORT)
                .basePath(DBO_API)
                .header(X_API_VERSION, 1)
                .contentType(ContentType.JSON)
                .filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

    @Test
    @DisplayName("Проверка GET client by id ")
    public void shouldGetClientByExistsId() {
        request
                .when()
                .get("/client/{id}", 2)
                .then()
                .statusCode(SC_OK)
                .body("id", is(2), "login", is("account@acme.com"));
    }

    @Test
    @DisplayName("Проверка удаления информации")
    public void shouldDeleteClient() {
        request
                .when()
                .body("{ \n" +
                        " \"login\" : \"mmm111@email.com\", \n" +
                        " \"secret\" : \"6467877878764656565768785\", \n" +
                        " \"salt\" : \"somesalt\"" +
                        "}")
                .post("/client")
                .then().statusCode(SC_CREATED);
        request
                .when()
                .delete("/client/login/{clientLogin}", "mmm111@email.com")
                .then()
                .statusCode(SC_OK);
    }

    @Test
    @DisplayName("Проверка по ид, которого нет")
    public void shouldGetClientByNotExistsId() {
        request
                .when()
                .get("/client/{id}", 999)
                .then()
                .statusCode(SC_NOT_FOUND);
    }

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


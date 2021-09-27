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
import static com.acme.dbo.restAssured.ClientEndPoint.*;

public class ClientTest {

    private RequestSpecification request;

    @BeforeEach
    public void setRequest() {
        request = given()
                .baseUri(BASE_URL)
                .port(PORT)
                .basePath(DBO_API)
                .header(X_API_VERSION, 1)
                .filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

    @Test
    @DisplayName("Проверка GET client by id ")
    public void shouldGetClientByExistsId() {
        request
                .when()
                .get(CLIENT_ID, 2)
                .then()
                .statusCode(SC_OK)
                .body("id", is(2), "login", is("account@acme.com"));
    }

    @Test
    @DisplayName("Проверка удаления информации")
    public void shouldDeleteClient() {
        request
                .when()
                .delete(CLIENT_ID, 2)
                .then()
                .statusCode(SC_OK);
    }

    @Test
    @DisplayName("Проверка по ид, которого нет")
    public void shouldGetClientByNotExistsId() {
        request
                .when()
                .get(CLIENT_ID, 999)
                .then()
                .statusCode(SC_NOT_FOUND);
    }

    @Test
    @DisplayName("Удаление информации по ид, которого нет")
    public void negativeShouldDeleteClient() {
        request
                .when()
                .delete(CLIENT_ID, 7)
                .then()
                .statusCode(SC_INTERNAL_SERVER_ERROR);
    }
}


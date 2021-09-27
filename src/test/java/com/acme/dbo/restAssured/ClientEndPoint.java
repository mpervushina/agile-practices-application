package com.acme.dbo.restAssured;

public interface ClientEndPoint {
    String BASE_URL = "http://localhost";
    int PORT = 8080;
    String DBO_API = "/dbo/api/";
    String X_API_VERSION = "X-API-VERSION";
    String CLIENT_ID = "/client/{id}";
}

package com.acme.dbo.retrofit;

import okhttp3.logging.HttpLoggingInterceptor;
import org.junit.jupiter.api.*;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class RetrofitClientTest {
    ClientService service;

    @BeforeEach
    public void unit() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(JacksonConverterFactory.create())
                .baseUrl("http://localhost:8080/dbo/api/")
                .client(httpClient.build())
                .build();
        service = retrofit.create(ClientService.class);
    }

    @Test
    @DisplayName("Проверка, что клиенты возвращаются")
    public void shouldGetClient() throws IOException {
        service.getClients().execute().body().forEach(System.out::println);
    }

    @Test
    @DisplayName("Проверка создания нового клиента")
    public void shouldPostClient() throws IOException {
        String login = service.createClient(new Client("privettesttest1@mm.ru", "samesalt", "8794343465765645454544544"))
                .execute().body().getLogin();
        assertEquals(login, "privettesttest1@mm.ru");
        System.out.println(login);
        service.deleteClient(login);
    }
}

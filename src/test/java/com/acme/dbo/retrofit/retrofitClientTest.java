package com.acme.dbo.retrofit;

import okhttp3.logging.HttpLoggingInterceptor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class retrofitClientTest {
   ClientServise service;
    @BeforeEach
    public void unit(){
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
        Retrofit retrofit=new Retrofit.Builder()
                .addConverterFactory(JacksonConverterFactory.create())
                .baseUrl("http://localhost:8080/dbo/api/")
                .client(httpClient.build())
                .build();
        service =retrofit.create(ClientServise.class);
    }

    @Test
    public void shouldGetClient() throws IOException {
        service.getClients().execute().body().forEach(System.out::println);
    }

    @Test
   public void shouldPostClient() throws IOException {
       String secret= service.createClient(new Client("2mmm@mm.ru","samesalt","8794876765644544"))
               .execute().body().getSecret();
        assertEquals(secret,"8794876765644544");
    }
}

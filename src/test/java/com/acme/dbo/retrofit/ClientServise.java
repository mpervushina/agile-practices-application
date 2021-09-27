package com.acme.dbo.retrofit;

import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface ClientServise {
    @GET("client/{id}")
    @Headers("X-API-VERSION:1")
    Call<Client> getClient(@Path("id") Integer id);

    @GET("client")
    @Headers("X-API-VERSION:1")
    Call<List<Client>> getClients();

    @GET("client")
    @Headers("X-API-VERSION:1")
    Call<Client> createClient(@Body Client client);
}

package com.fx.folx.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface APIInterface {


    String BASE_URL = "https://developers.zomato.com/";
    //TODO: Change the apikey to company registered key
    String apiKey = "f5563e2d416bf5ff9a25dc6b249185b4";


    @GET("api/v2.1/search")
    Call<MultipleResource> getRestaurantBySearch(@Query("entity_id") String entity_id, @Query("entity_type") String entity_type, @Query("q") String query, @Header("user-key") String userkey);


    @GET("api/v2.1/search")
    Call<MultipleResource> getRestaurantBySearch(@Query("entity_id") String entity_id, @Query("entity_type") String entity_type, @Header("user-key") String userkey);

}
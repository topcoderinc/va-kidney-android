package com.topcoder.vakidney.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by afrisalyp on 16/03/2018.
 * * This class implements NDB API used by this app
 */

public interface NDBServiceAPI {

    @GET("/ndb/search?format=json&sort=r&offset=0")
    Call<String> searchFood(
            @Query("api_key") String apiKey,
            @Query("q") String query);

    @GET("/ndb/reports?type=b&format=json")
    Call<String> searchFoodNutrition(
            @Query("api_key") String apiKey,
            @Query("ndbno") String ndbno);

    @GET("/ndb/nutrients/?format=json&sort=c")
    Call<String> searchNutritionFoods(
            @Query("api_key") String apiKey,
            @Query("nutrients") String nutrientsId,
            @Query("max") String limit,
            @Query("offset") String offset

    );

}

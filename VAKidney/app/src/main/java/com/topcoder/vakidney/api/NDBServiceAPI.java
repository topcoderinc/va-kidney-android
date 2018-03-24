package com.topcoder.vakidney.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by afrisalyp on 16/03/2018.
 */

public interface NDBServiceAPI {

    @GET("/drug/event.json")
    Call<String> searchDrugInteractions(@Query("search") String query);

}

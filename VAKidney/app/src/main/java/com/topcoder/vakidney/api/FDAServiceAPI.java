package com.topcoder.vakidney.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by afrisalyp on 16/03/2018.
 * This class implements FDA API used by this app
 */

public interface FDAServiceAPI {

    @GET("/drug/event.json")
    Call<String> searchDrugInteractions(@Query("search") String query);

}

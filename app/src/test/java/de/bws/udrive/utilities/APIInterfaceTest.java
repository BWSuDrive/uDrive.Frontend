package de.bws.udrive.utilities;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;

public interface APIInterfaceTest {

    @GET("/api/RESTTest")
    Call<String> testAPIConnection();
}

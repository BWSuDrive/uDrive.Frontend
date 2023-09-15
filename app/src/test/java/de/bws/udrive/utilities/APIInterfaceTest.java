package de.bws.udrive.utilities;


import org.junit.runner.RunWith;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIInterfaceTest {
    @GET("/api/RESTTest")
    Call<String> testAPIConnection();
}

package de.bws.udrive.utilities;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClientTest {
    private static Retrofit rf;
    public static Retrofit getAPI()
    {
        if(rf == null)
        {
            rf = new Retrofit.Builder()
                    .baseUrl("https://bwsudriverestapi.azurewebsites.net/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return rf;
    }
}

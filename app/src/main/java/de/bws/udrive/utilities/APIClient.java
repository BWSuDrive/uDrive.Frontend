package de.bws.udrive.utilities;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/* Klasse, um API Calls zu machen */
public class APIClient {
    private static Retrofit retrofit;
    public static Retrofit getAPI() {
        if(retrofit == null)
        {
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://bwsudriverestapi.azurewebsites.net")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}

package de.bws.udrive.utilities;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Klasse, die benötigt wird, um API Calls vorzunehmen
 *
 * @author Lucas
 * */
public class APIClient {
    private static Retrofit retrofit;
    public static Retrofit getAPI() {
        if(retrofit == null)
        {
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://bws-udriveapi.azurewebsites.net")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}

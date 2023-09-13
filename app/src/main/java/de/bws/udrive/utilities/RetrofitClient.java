package de.bws.udrive.utilities;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit;
    public static Retrofit getRetrofit() {
        if(retrofit == null)
        {
            retrofit = new Retrofit.Builder()
                    .baseUrl("baseUrl")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}

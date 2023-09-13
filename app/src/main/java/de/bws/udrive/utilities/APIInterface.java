package de.bws.udrive.utilities;

import de.bws.udrive.utilities.model.uDriveLogin;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIInterface {
    @FormUrlEncoded
    @POST("/some/endpoint")
    Call<uDriveLogin>
        sendLoginRequest(@Field("name") String name, @Field("password") String password);
}

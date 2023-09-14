package de.bws.udrive.utilities;

import de.bws.udrive.utilities.model.uDriveLogin;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIInterface {

    /* Für Testzwecke, wird bald gelöscht */
    @FormUrlEncoded
    @POST("/some/endpoint")
    Call<uDriveLogin>
        sendLoginRequest(@Field("name") String name, @Field("password") String password);

    @GET("/api/RESTTest")
    Call<ResponseBody> testAPIEndpoint();

    /* Methode, um Verbindung mit der API zu testen */
    @POST("/api/RESTTest")
    Call<ResponseBody> testAPIEndpoint(@Body uDriveLogin login);
}

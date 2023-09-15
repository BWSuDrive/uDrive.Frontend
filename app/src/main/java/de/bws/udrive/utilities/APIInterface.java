package de.bws.udrive.utilities;

import de.bws.udrive.utilities.model.*;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIInterface {

    /* Test User - Admin123 */

    @POST("/some/endpoint")
    Call<ResponseBody> sendLoginRequest(@Body uDrive.Login login);

    @GET("/api/RESTTest")
    Call<ResponseBody> testAPIEndpoint();

    /* Methode, um Verbindung mit der API zu testen */
    @POST("/api/RESTTest")
    Call<ResponseBody> testAPIEndpoint(@Body uDrive.Login login);
}

package de.bws.udrive.utilities;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import de.bws.udrive.utilities.model.*;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Interface, um mit der API (dem Backend) zu kommunizieren
 */
public interface APIInterface {

    /* Test User - Admin123 */

    /**
     * Methode, um Login-Anfrage an API zu senden
     * @param login Objekt das User-Input enthält
     * @return Ein Objekt LoginResponse, enthält u.a. einen Bearer Token
     */
    @POST("/api/RESTLogin")
    Call<uDrive.LoginResponse> sendLoginRequest(@Body uDrive.Login login);

    /**
     * Methode, um Kommunikation nach dem Login zu testen
     * @param token Bearer Token, der für die Kommunikation benötigt wird <br>
     *              String muss so aussehen --> "Bearer {token}"
     * @return Eine leere Liste
     */
    @GET("/api/Weekdays")
    Call<List<String>> sendWeekdayRequest(@Header("Authorization") String token);

    @GET("/api/RESTTest")
    Call<ResponseBody> testAPIEndpoint();

    /* Methode, um Verbindung mit der API zu testen */
    @POST("/api/RESTTest")
    Call<ResponseBody> testAPIEndpoint(@Body uDrive.Login login);
}

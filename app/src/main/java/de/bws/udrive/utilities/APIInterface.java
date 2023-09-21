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

    /**
     * Methode, um Login-Anfrage an API zu senden
     * @param login Objekt das User-Input enthält
     * @return Ein Objekt LoginResponse, enthält u.a. einen Bearer Token
     */
    @POST("/Login")
    Call<uDrive.LoginResponse> sendLoginRequest(@Body uDrive.Login login);

    /**
     *
     */
    @POST("/TBD")
    Call<uDrive.SignUpResponse> sendSignUpRequest(@Body uDrive.SignUp signUp);
}

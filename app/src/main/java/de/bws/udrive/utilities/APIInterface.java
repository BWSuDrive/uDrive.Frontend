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
     * @return Ein Objekt der Klasse {@link de.bws.udrive.utilities.model.uDrive.LoginResponse} <br>
     *         das Informationen über den Benutzer (Name, Mail, ...) enthält. <br>
     *         Wird benutzt um einen {@link uDrive.SignedInUser} zu erstellen
     * @author Lucas
     */
    @POST("/Login")
    Call<uDrive.LoginResponse> sendLoginRequest(@Body uDrive.Login login);

    /**
     * Methode, um Registrierungs-Anfrage an API zu senden
     * @param signUp Objekt das User-Input enthält
     * @return Ein Objekt der Klasse {@link de.bws.udrive.utilities.model.uDrive.SignUpResponse} <br>
     *         das Informationen über den Benutzer (Name, Mail, ...) enthält. <br>
     *         Wird benutzt um einen {@link uDrive.SignedInUser} zu erstellen
     * @author Lucas
     */
    @POST("/Register")
    Call<uDrive.SignUpResponse> sendSignUpRequest(@Body uDrive.SignUp signUp);
}

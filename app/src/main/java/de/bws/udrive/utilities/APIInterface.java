package de.bws.udrive.utilities;

import java.util.List;

import de.bws.udrive.utilities.model.*;
import de.bws.udrive.utilities.response.DriveRequestResponse;
import de.bws.udrive.utilities.response.LoginResponse;
import de.bws.udrive.utilities.response.SignUpResponse;
import de.bws.udrive.utilities.response.TourPlanResponse;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;

/**
 * Interface, um mit der API (dem Backend) zu kommunizieren
 *
 * @author Lucas, Niko
 */
public interface APIInterface {

    /**
     * Methode, um Login-Anfrage an API zu senden
     * @param login Objekt das User-Input enthält
     * @return Ein Objekt der Klasse {@link LoginResponse} <br>
     *         das Informationen über den Benutzer (Name, Mail, ...) enthält. <br>
     *         Wird benutzt um einen {@link SignedInUser} zu erstellen
     * @author Lucas
     */
    @POST("/Login")
    Call<LoginResponse> sendLoginRequest(@Body Login login);

    /**
     * Methode, um Registrierungs-Anfrage an API zu senden
     * @param signUp Objekt das User-Input enthält
     * @return Ein Objekt der Klasse {@link SignUpResponse} <br>
     *         das Informationen über den Benutzer (Name, Mail, ...) enthält. <br>
     *         Wird benutzt um einen {@link SignedInUser} zu erstellen
     * @author Lucas
     */
    @POST("/Register")
    Call<SignUpResponse> sendSignUpRequest(@Body SignUp signUp);

    /**
     * Methode, um Fahrtenplaner-Anfragen an API zu senden
     * @param authHeader Bearer-Token für API Anfragen -> Kann mit {@link SignedInUser#getHTTPAuthHeader()} geholt werden
     * @param tourPlan Objekt, das User-Input enthält
     * @return Ein Objekt der Klasse {@link TourPlanResponse} <br>
     *         das Informationen enthält
     * @author Lucas
     */
    @POST("/TourPlans")
    Call<ResponseBody> postTourData(@Header("Authorization") String authHeader, @Body TourPlan tourPlan);

    /**
     * Methode, um verfügbare Fahrten von API zu bekommen
     * @param authHeader Bearer-Token für API Anfragen -> Kann mit {@link SignedInUser#getHTTPAuthHeader()} geholt werden
     * @author Lucas, Niko
     */
    @POST("/PassengerRequests/FilterDriversBy5kmRadius")
    Call<List<DriveRequestResponse>> getAvailableDrivers(@Header("Authorization") String authHeader, @Body DriveRequest driveRequest);

    /**
     * Methode, um aktuelle Anfragen von API zu bekommen
     * @param authHeader Bearer-Token für API Anfragen -> Kann mit {@link SignedInUser#getHTTPAuthHeader()} geholt werden
     * @return
     */
    @GET("/PassengerRequests/GetPassengerRequests")
    Call<List<PassengerRequest>> getCurrentRequests(@Header("Authorization") String authHeader);

    /**
     * Methode, um Anfrage zu einer Fahrt zu senden
     * @param authHeader Bearer-Token für API Anfragen -> Kann mit {@link SignedInUser#getHTTPAuthHeader()} geholt werden
     * @return
     */
    @POST("/PassengerRequests")
    Call<ResponseBody> sendPassengerRequest(@Header("Authorization") String authHeader, @Body RequestTakeAway takeAway);

    /**
     * Akzeptiert eine Anfrage
     * @param authHeader Bearer-Token für API Anfragen -> Kann mit {@link SignedInUser#getHTTPAuthHeader()} geholt werden
     * @return
     */
    @PUT("/PassengerRequests/AcceptRequest")
    Call<ResponseBody> acceptRequest(@Header("Authorization") String authHeader, @Body PassengerRequest request);

    /**
     * Lehnt eine Anfrage ab
     * @param authHeader Bearer-Token für API Anfragen -> Kann mit {@link SignedInUser#getHTTPAuthHeader()} geholt werden
     * @return
     */
    @PUT("/PassengerRequests/DenyRequest")
    Call<ResponseBody> denyRequest(@Header("Authorization") String authHeader, @Body PassengerRequest request);
}

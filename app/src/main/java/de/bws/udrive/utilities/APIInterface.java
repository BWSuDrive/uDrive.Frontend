package de.bws.udrive.utilities;

import de.bws.udrive.utilities.model.*;
import de.bws.udrive.utilities.response.DriveRequestResponse;
import de.bws.udrive.utilities.response.LoginResponse;
import de.bws.udrive.utilities.response.SignUpResponse;
import de.bws.udrive.utilities.response.TourPlanResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Interface, um mit der API (dem Backend) zu kommunizieren
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
    @POST("/Drivers/TourPlans")
    Call<TourPlanResponse> postTourData(@Header("Authorization") String authHeader, @Body TourPlan tourPlan);


    /**
     * Methode, um verfügbare Fahrten von API zu bekommen
     * @param authHeader Bearer-Token für API Anfragen -> Kann mit {@link SignedInUser#getHTTPAuthHeader()} geholt werden
     * @author Lucas, Niko
     */

    @POST("/ScheduleTour/FilterDriversBy5kmRadius")
    Call<DriveRequestResponse> getAvailableDrivers(@Header("Authorization") String authHeader, @Body DriveRequest driveRequest);
}

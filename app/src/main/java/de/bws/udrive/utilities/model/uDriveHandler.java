package de.bws.udrive.utilities.model;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import de.bws.udrive.utilities.APIClient;
import de.bws.udrive.utilities.APIInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * uDriveHandler ist eine Sammlung von Klassen, die zuständig für API Calls sind <br>
 * @author Lucas 
 */
public class uDriveHandler {

    /**
     * LoginHandler ist eine Klasse, die sich um den Login API Call kümmert <br>
     * @author Lucas
     */
    public static class LoginHandler
    {
        private String errorText = "";
        private MutableLiveData<Boolean> isFinished = new MutableLiveData<>(Boolean.FALSE);
        private boolean loginSuccessful = false;

        /**
         * Methode, die für das versenden von Daten an die API verantwortlich ist <br>
         * @param login Objekt, das Login-Informationen (User-Input aus {@link de.bws.udrive.MainActivity}) enthält
         * */
        public void handle(uDrive.Login login)
        {
            /* API Call vorbereiten */
            Call<uDrive.LoginResponse> loginRequest = APIClient.getAPI().create(APIInterface.class).sendLoginRequest(login);

            /* Asynchroner Aufruf an API */
            loginRequest.enqueue(this.loginCallback);
        }

        /**
         * Callback das durch {@link #handle(uDrive.Login)} benötigt wird <br>
         * Wertet die Antwort der API aus <br>
         * Überschreibt 2 Methoden aus dem Interface {@link Callback} <br>
         * onResponse() -» API hat geantwortet <br>
         * onFailure()  -» API hat nicht geantwortet <br>
         * @author Lucas
         */
        private Callback<uDrive.LoginResponse> loginCallback = new Callback<uDrive.LoginResponse>()
        {
            @Override
            public void onResponse(Call<uDrive.LoginResponse> call, Response<uDrive.LoginResponse> response)
            {
                /* Antwort von API OK */
                if(response.code() == 200)
                {
                    if(response.body() != null)
                    {
                        uDrive.LoginResponse loginResponse = response.body();

                        String id = loginResponse.getData().get("id").toString();
                        String bearerToken = loginResponse.getData().get("token").toString();
                        String signedInUserVorname = loginResponse.getData().get("firstname").toString();
                        String signedInUserNachname = loginResponse.getData().get("lastname").toString();
                        String signedInUserMail = loginResponse.getData().get("email").toString();
                        ArrayList<String> rollen = (ArrayList<String>) loginResponse.getData().get("roles");

                        uDrive.SignedInUser signedInUser = new uDrive.SignedInUser();

                        if(id != null)
                            signedInUser.setID(id);

                        if(bearerToken != null)
                            signedInUser.setToken(bearerToken);

                        if(signedInUserVorname != null)
                            signedInUser.setVorname(signedInUserVorname);

                        if(signedInUserNachname != null)
                            signedInUser.setNachname(signedInUserNachname);

                        if(signedInUserMail != null)
                            signedInUser.setMail(signedInUserMail);

                        rollen.forEach(rolle -> {
                           if(rolle != null)
                               signedInUser.addRole(rolle);
                        });

                        uDrive.General.setSignedInUser(signedInUser);

                        Log.i("uDrive.uDriveHandler.loginResponse.onResponse", "Token is: " + signedInUser.getToken());

                        loginSuccessful = true;
                    }
                    else /* Body is null */
                    {
                        errorText += "Responsebody was null.\n";
                        errorText += response.errorBody().toString() + "\n";
                    }
                }
                else /* Responsecode != 200 */
                {
                    errorText += "Beim Login ist ein Fehler aufgetreten!\n";
                    errorText += "Fehler-Code: " + response.code() + "\n";

                    try
                    {
                        String errorBodyStr = (response.errorBody() == null) ? null : response.errorBody().string();

                        errorText += (errorBodyStr == null) ?
                                "Unbekannter Fehler\n" :
                                new Gson().fromJson(errorBodyStr, uDrive.LoginResponse.class).getMessage();
                    }
                    catch (IOException e)
                    {
                        throw new RuntimeException(e);
                    }
                }
                isFinished.setValue(Boolean.TRUE);
            }

            @Override
            public void onFailure(Call<uDrive.LoginResponse> call, Throwable t)
            {
                errorText += "Die Kommunikation mit der API war nicht möglich!\n";
                errorText += "Bitte stelle sicher, das du eine aktive Internetverbindung hast!\n";
                errorText += t.getMessage();
                Log.wtf("uDrive.Login.Failure", t.getMessage());

                isFinished.setValue(Boolean.TRUE);
            }
        };

        public MutableLiveData<Boolean> getFinishedState() { return isFinished; }

        public String getError() { return this.errorText; }

        public boolean isLoginSuccessful() { return loginSuccessful; }
    }

    /**
     * SignUpHandler ist eine Klasse, die sich um den SignUp API Call kümmert <br>
     * @author Lucas
     */
    public static class SignUpHandler
    {
        private String errorText = "";
        private boolean signUpSuccessful = false;
        private MutableLiveData<Boolean> isFinished = new MutableLiveData<>(Boolean.FALSE);

        /**
         * Methode, die für das versenden von Daten an die API verantwortlich ist <br>
         * @param signUp Objekt, das SignUp-Informationen (User-Input aus {@link de.bws.udrive.MainActivity}) enthält
         * */
        public void handle(uDrive.SignUp signUp)
        {
            /* API Call vorbereiten */
            Call<uDrive.SignUpResponse> signUpRequest =
                    APIClient.getAPI().create(APIInterface.class).sendSignUpRequest(signUp);

            /* Asynchroner Aufruf */
            signUpRequest.enqueue(signUpCallback);
        }

        /**
         * Callback das durch {@link #handle(uDrive.SignUp)} benötigt wird <br>
         * Wertet die Antwort der API aus <br>
         * Überschreibt 2 Methoden aus dem Interface {@link Callback} <br>
         * onResponse() -» API hat geantwortet <br>
         * onFailure()  -» API hat nicht geantwortet <br>
         * @author Lucas
         */
        private Callback<uDrive.SignUpResponse> signUpCallback = new Callback<uDrive.SignUpResponse>() {
            @Override
            public void onResponse(Call<uDrive.SignUpResponse> call, Response<uDrive.SignUpResponse> response)
            {
                /* Antwort von API OK */
                if(response.code() == 200)
                {
                    if(response.body() != null)
                    {
                        uDrive.SignUpResponse signUpResponse = response.body();

                        String id = signUpResponse.getData().get("id").toString();
                        String bearerToken = signUpResponse.getData().get("token").toString();
                        String signedInUserVorname = signUpResponse.getData().get("firstname").toString();
                        String signedInUserNachname = signUpResponse.getData().get("lastname").toString();
                        String signedInUserMail = signUpResponse.getData().get("email").toString();
                        ArrayList<String> rollen = (ArrayList<String>) signUpResponse.getData().get("roles");

                        uDrive.SignedInUser signedInUser = new uDrive.SignedInUser();

                        if(id != null)
                            signedInUser.setID(id);

                        if(bearerToken != null)
                            signedInUser.setToken(bearerToken);

                        if(signedInUserVorname != null)
                            signedInUser.setVorname(signedInUserVorname);

                        if(signedInUserNachname != null)
                            signedInUser.setNachname(signedInUserNachname);

                        if(signedInUserMail != null)
                            signedInUser.setMail(signedInUserMail);

                        rollen.forEach(rolle -> {
                            if(rolle != null)
                                signedInUser.addRole(rolle);
                        });

                        uDrive.General.setSignedInUser(signedInUser);

                        signUpSuccessful = true;
                    }
                    else /* Body is null */
                    {
                        errorText += "Der ResponseBody ist null!\n";
                        try
                        {
                            errorText += (response.errorBody() == null) ? "Der ErrorBody ist null!\n" : new Gson().fromJson(response.errorBody().string(), uDrive.SignUpResponse.class).getMessage();
                        }
                        catch (IOException e)
                        {
                            throw new RuntimeException(e);
                        }
                    }
                }
                else /* Responsecode != 200 */
                {
                    errorText += "Bei der Registrierung ist ein Fehler aufgetreten!\n";
                    errorText += "Fehler-Code: " + response.code() + "\n";

                    try
                    {
                        String errorBodyStr = (response.errorBody() == null) ? null : response.errorBody().string();

                        errorText += (errorBodyStr == null) ?
                                "Unbekannter Fehler\n" :
                                new Gson().fromJson(errorBodyStr, uDrive.SignUpResponse.class).getMessage();
                    }
                    catch (IOException e)
                    {
                        throw new RuntimeException(e);
                    }
                }

                isFinished.setValue(Boolean.TRUE);
            }

            @Override
            public void onFailure(Call<uDrive.SignUpResponse> call, Throwable t)
            {
                errorText += "Die Kommunikation mit der API war nicht möglich!\n";
                errorText += "Bitte stelle sicher, das du eine aktive Internetverbindung hast!\n";
                errorText += t.getMessage();
                Log.wtf("uDrive.Login.Failure", t.getMessage());

                isFinished.setValue(Boolean.TRUE);
            }
        };

        public MutableLiveData<Boolean> getFinishedState() { return this.isFinished; }

        public boolean isSignUpSuccessful()
        {
            return this.signUpSuccessful;
        }

        public String getError() { return this.errorText; }
    }

    /**
     * TourPlanHandler ist eine Klasse, die sich um das publishen von Fahrten kümmert <br>
     *
     * @author Lucas
     */
    public static class TourPlanHandler
    {
        private String errorText = "";
        private boolean tourPlanSuccessful = false;
        private MutableLiveData<Boolean> isFinished = new MutableLiveData<>(Boolean.FALSE);

        public void handle(uDrive.TourPlan tourPlan)
        {
            /* API Call vorbereiten */
            Call<uDrive.TourPlanResponse> tourPlanRequest = APIClient.getAPI().create(APIInterface.class).
                    postTourData(uDrive.General.getSignedInUser().getHTTPAuthHeader(), tourPlan);

            /* Asynchroner Aufruf */
            tourPlanRequest.enqueue(tourPlanCallback);
        }

        private Callback<uDrive.TourPlanResponse> tourPlanCallback = new Callback<uDrive.TourPlanResponse>() {
            @Override
            public void onResponse(Call<uDrive.TourPlanResponse> call, Response<uDrive.TourPlanResponse> response)
            {
                /* Antwort von API OK */
                if(response.code() == 200)
                {
                    if(response.body() != null)
                    {
                        uDrive.TourPlanResponse tourPlanResponse = response.body();

                        /* Do something... */



                        tourPlanSuccessful = true;
                    }
                    else /* Body is null */
                    {
                        errorText += "Der ResponseBody ist null!\n";
                        try
                        {
                            errorText += (response.errorBody() == null) ? "Der ErrorBody ist null!\n" : new Gson().fromJson(response.errorBody().string(), uDrive.TourPlanResponse.class).toString();
                        }
                        catch (IOException e)
                        {
                            throw new RuntimeException(e);
                        }
                    }
                }
                else /* Fehlercode != 200 */
                {
                    errorText += "Beim senden der Fahrt ist ein Fehler aufgetreten!\n";
                    errorText += "Fehler-Code: " + response.code() + "\n";

                    try
                    {
                        String errorBodyStr = (response.errorBody() == null) ? null : response.errorBody().string();

                        errorText += (errorBodyStr == null) ?
                                "Unbekannter Fehler\n" :
                                new Gson().fromJson(errorBodyStr, uDrive.TourPlanResponse.class).toString();
                    }
                    catch (IOException e)
                    {
                        throw new RuntimeException(e);
                    }
                }

                isFinished.setValue(Boolean.TRUE);
            }

            @Override
            public void onFailure(Call<uDrive.TourPlanResponse> call, Throwable t)
            {
                errorText += "Die Kommunikation mit der API war nicht möglich!\n";
                errorText += "Bitte stelle sicher, das du eine aktive Internetverbindung hast!\n";
                errorText += t.getMessage();
                Log.wtf("uDrive.TourPlan.Failure", t.getMessage());

                isFinished.setValue(Boolean.TRUE);
            }
        };

        public MutableLiveData<Boolean> getFinishedState() { return this.isFinished; }

        public boolean isTourPlanSuccessful()
        {
            return this.tourPlanSuccessful;
        }

        public String getError() { return this.errorText; }
    }

    /**
     * DriveRequestsHandler ist eine Klasse, die sich um das holen von Fahrten kümmert <br>
     *
     * @author Lucas, Niko
     */
    public static class DriveRequestsHandler
    {
        private String errorText = "";
        private boolean driveRequests = false;
        private MutableLiveData<Boolean> isFinished = new MutableLiveData<>(Boolean.FALSE);

        public void handle(uDrive.DriveRequest driveRequest)
        {
            Call<uDrive.DriveRequestResponse> driveRequestResponse =
                    APIClient.getAPI().create(APIInterface.class).getAvailableDrivers(uDrive.General.getSignedInUser().getHTTPAuthHeader(), driveRequest);

            driveRequestResponse.enqueue(driveRequestCallback);
        }

        private final Callback<uDrive.DriveRequestResponse> driveRequestCallback = new Callback<uDrive.DriveRequestResponse>() {
            @Override
            public void onResponse(Call<uDrive.DriveRequestResponse> call, Response<uDrive.DriveRequestResponse> response)
            {
                if(response.code() == 200)
                {

                }
            }

            @Override
            public void onFailure(Call<uDrive.DriveRequestResponse> call, Throwable t)
            {

            }
        };
    }
}

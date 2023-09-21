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
                /* Antwort OK von API */
                if(response.code() == 200)
                {
                    if(response.body() != null)
                    {
                        uDrive.LoginResponse responseObj = response.body();

                        String bearerToken = responseObj.getData().get("token").toString();
                        String signedInUserVorname = responseObj.getData().get("firstname").toString();
                        String signedInUserNachname = responseObj.getData().get("lastname").toString();
                        String signedInUserMail = responseObj.getData().get("email").toString();
                        ArrayList<String> rollen = (ArrayList<String>) responseObj.getData().get("roles");

                        uDrive.SignedInUser signedInUser = new uDrive.SignedInUser();

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
                    else /* Body null */
                    {
                        errorText += "Responsebody was null.\n";
                        errorText += response.errorBody().toString() + "\n";
                    }
                }
                else /* ResponseCode 200 */
                {
                    errorText += "Beim Login ist ein Fehler aufgetreten!\n";
                    errorText += "Fehler-Code: " + response.code() + "\n";

                    try
                    {
                        String errorBodyStr = response.errorBody().string();

                        errorText += (errorBodyStr != null) ?
                                new Gson().fromJson(errorBodyStr, uDrive.LoginResponse.class).getMessage() :
                                "Unbekannter Fehler\n";

                    } catch (IOException e)
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

        public SignUpHandler() {}

        public void handle(uDrive.SignUp signUp)
        {
            /* API Call vorbereiten */
            Call<uDrive.SignUpResponse> signUpRequest =
                    APIClient.getAPI().create(APIInterface.class).sendSignUpRequest(signUp);

            /* Asynchroner Aufruf */
            signUpRequest.enqueue(signUpCallback);
        }

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

                    }
                }
                else
                {
                    errorText += "Bei der Registrierung ist ein Fehler aufgetreten!\n";
                    errorText += "Fehler-Code: " + response.code() + "\n";

                    try
                    {
                        String errorBodyStr = response.errorBody().string();

                        errorText += (errorBodyStr != null) ?
                                new Gson().fromJson(errorBodyStr, uDrive.LoginResponse.class).getMessage() :
                                "Unbekannter Fehler\n";

                    } catch (IOException e)
                    {
                        throw new RuntimeException(e);
                    }
                }

                isFinished.setValue(Boolean.TRUE);
            }

            @Override
            public void onFailure(Call<uDrive.SignUpResponse> call, Throwable t)
            {

            }
        };

        public MutableLiveData<Boolean> getFinishedState() { return this.isFinished; }

        public boolean isSignUpSuccessful()
        {
            return this.signUpSuccessful;
        }

        public String getError() { return this.errorText; }
    }
}

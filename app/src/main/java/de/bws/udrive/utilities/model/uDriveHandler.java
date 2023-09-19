package de.bws.udrive.utilities.model;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

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

            Log.d("uDrive.uDriveHandler.LoginHandler", "handle() method finished!");
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
            public void onResponse(Call<uDrive.LoginResponse> call, Response<uDrive.LoginResponse> response) {
                Log.d("uDrive.uDriveHandler.LoginHandler", "API called... onResponse()");
                /* Antwort OK von API */
                if(response.code() == 200)
                {
                    if(response.body() != null)
                    {
                        uDrive.LoginResponse responseObj = response.body();

                        String bearerToken = responseObj.getData().get("token");
                        String signedInUserName = responseObj.getData().get("firstname");
                        String signedInUserMail = responseObj.getData().get("email");

                        if(bearerToken != null)
                            uDrive.General.setToken(bearerToken);

                        if(signedInUserName != null)
                            uDrive.General.setUserName(signedInUserName);

                        if(signedInUserMail != null)
                            uDrive.General.setUserMail(signedInUserMail);

                        Log.i("uDrive.uDriveHandler.loginResponse.onResponse", "Token is: " + uDrive.General.getToken());

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
                    errorText += "Der Login war nicht möglich!\n";
                    errorText += "Fehler-Code: " + response.code() + "\n";
                    errorText += "Fehler: " + response.message() + "\n";
                }

                isFinished.setValue(Boolean.TRUE);
            }

            @Override
            public void onFailure(Call<uDrive.LoginResponse> call, Throwable t)
            {
                errorText += "Die Kommunikation mit der API war nicht möglich!\n";
                Log.wtf("uDrive.uDriveHandler.loginResponse.onFailure", t.getMessage());

                isFinished.setValue(Boolean.TRUE);
            }
        };

        public MutableLiveData<Boolean> getFinishedState() { return isFinished; }

        public String getError() { return this.errorText; }

        public boolean isLoginSuccessful() { return loginSuccessful; }
    }
}

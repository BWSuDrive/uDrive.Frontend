package de.bws.udrive.utilities.handler;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import de.bws.udrive.utilities.APIClient;
import de.bws.udrive.utilities.APIInterface;
import de.bws.udrive.utilities.model.General;
import de.bws.udrive.utilities.model.Login;
import de.bws.udrive.utilities.model.SignedInUser;
import de.bws.udrive.utilities.response.LoginResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * LoginHandler ist eine Klasse, die sich um den Login API Call kümmert <br>
 *
 * @author Lucas
 */
public class LoginHandler {
    private String errorText = "";
    private MutableLiveData<Boolean> isFinished = new MutableLiveData<>(Boolean.FALSE);
    private boolean loginSuccessful = false;

    /**
     * Methode, die für das versenden von Daten an die API verantwortlich ist <br>
     *
     * @param login Objekt, das Login-Informationen (User-Input aus {@link de.bws.udrive.MainActivity}) enthält
     */
    public void handle(Login login) {
        /* API Call vorbereiten */
        Call<LoginResponse> loginRequest = APIClient.getAPI().create(APIInterface.class).sendLoginRequest(login);

        /* Asynchroner Aufruf an API */
        loginRequest.enqueue(this.loginCallback);
    }

    /**
     * Callback das durch {@link #handle(Login)} benötigt wird <br>
     * Wertet die Antwort der API aus <br>
     * Überschreibt 2 Methoden aus dem Interface {@link Callback} <br>
     * onResponse() -» API hat geantwortet <br>
     * onFailure()  -» API hat nicht geantwortet <br>
     *
     * @author Lucas
     */
    private Callback<LoginResponse> loginCallback = new Callback<LoginResponse>() {
        @Override
        public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
            /* Antwort von API OK */
            if (response.code() == 200) {
                if (response.body() != null) {
                    LoginResponse loginResponse = response.body();

                    String id = loginResponse.getData().get("id").toString();
                    String bearerToken = loginResponse.getData().get("token").toString();
                    String signedInUserVorname = loginResponse.getData().get("firstname").toString();
                    String signedInUserNachname = loginResponse.getData().get("lastname").toString();
                    String signedInUserMail = loginResponse.getData().get("email").toString();
                    ArrayList<String> rollen = (ArrayList<String>) loginResponse.getData().get("roles");

                    SignedInUser signedInUser = new SignedInUser();

                    if (id != null)
                        signedInUser.setID(id);

                    if (bearerToken != null)
                        signedInUser.setToken(bearerToken);

                    if (signedInUserVorname != null)
                        signedInUser.setVorname(signedInUserVorname);

                    if (signedInUserNachname != null)
                        signedInUser.setNachname(signedInUserNachname);

                    if (signedInUserMail != null)
                        signedInUser.setMail(signedInUserMail);

                    rollen.forEach(rolle -> {
                        if (rolle != null)
                            signedInUser.addRole(rolle);
                    });

                    General.setSignedInUser(signedInUser);

                    Log.i("uDrive.uDriveHandler.loginResponse.onResponse", "Token is: " + signedInUser.getToken());

                    loginSuccessful = true;
                } else /* Body is null */ {
                    errorText += "Responsebody was null.\n";
                    errorText += response.errorBody().toString() + "\n";
                }
            } else /* Responsecode != 200 */ {
                errorText += "Beim Login ist ein Fehler aufgetreten!\n";
                errorText += "Fehler-Code: " + response.code() + "\n";

                try {
                    String errorBodyStr = (response.errorBody() == null) ? null : response.errorBody().string();

                    errorText += (errorBodyStr == null) ?
                            "Unbekannter Fehler\n" :
                            new Gson().fromJson(errorBodyStr, LoginResponse.class).getMessage();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            isFinished.setValue(Boolean.TRUE);
        }

        @Override
        public void onFailure(Call<LoginResponse> call, Throwable t) {
            errorText += "Die Kommunikation mit der API war nicht möglich!\n";
            errorText += "Bitte stelle sicher, das du eine aktive Internetverbindung hast!\n";
            errorText += t.getMessage();
            Log.wtf("uDrive.Login.Failure", t.getMessage());

            isFinished.setValue(Boolean.TRUE);
        }
    };

    public MutableLiveData<Boolean> getFinishedState() {
        return isFinished;
    }

    public String getError() {
        return this.errorText;
    }

    public boolean isLoginSuccessful() {
        return loginSuccessful;
    }
}

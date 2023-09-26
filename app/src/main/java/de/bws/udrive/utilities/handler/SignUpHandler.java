package de.bws.udrive.utilities.handler;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import de.bws.udrive.utilities.APIClient;
import de.bws.udrive.utilities.APIInterface;
import de.bws.udrive.utilities.model.General;
import de.bws.udrive.utilities.model.SignUp;
import de.bws.udrive.utilities.model.SignedInUser;
import de.bws.udrive.utilities.response.SignUpResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * SignUpHandler ist eine Klasse, die sich um den SignUp API Call kümmert <br>
 *
 * @author Lucas
 */
public class SignUpHandler {
    private final String TAG = "uDrive." + getClass().getSimpleName();
    private String informationString = "";
    private boolean signUpSuccessful = false;
    private MutableLiveData<Boolean> isFinished = new MutableLiveData<>(Boolean.FALSE);

    /**
     * Methode, die für das versenden von Daten an die API verantwortlich ist <br>
     *
     * @param signUp Objekt, das SignUp-Informationen (User-Input aus {@link de.bws.udrive.MainActivity}) enthält
     */
    public void handle(SignUp signUp) {
        /* API Call vorbereiten */
        Call<SignUpResponse> signUpRequest =
                APIClient.getAPI().create(APIInterface.class).sendSignUpRequest(signUp);

        /* Asynchroner Aufruf */
        signUpRequest.enqueue(signUpCallback);
    }

    /**
     * Callback das durch {@link #handle(SignUp)} benötigt wird <br>
     * Wertet die Antwort der API aus <br>
     * Überschreibt 2 Methoden aus dem Interface {@link Callback} <br>
     * onResponse() -» API hat geantwortet <br>
     * onFailure()  -» API hat nicht geantwortet <br>
     *
     * @author Lucas
     */
    private Callback<SignUpResponse> signUpCallback = new Callback<SignUpResponse>() {
        @Override
        public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {

            Log.i(TAG, "Response-Code: " + response.code());

            switch(response.code())
            {
                case 200:
                    if (response.body() != null)
                    {
                        signUpSuccessful = true;
                    }
                    else /* Body is null */
                    {
                        informationString += "Der ResponseBody ist null!\n";
                        try {
                            informationString += (response.errorBody() == null) ? "Der ErrorBody ist null!\n" : new Gson().fromJson(response.errorBody().string(), SignUpResponse.class).getMessage();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    break;

                default:
                    informationString += "Bei der Registrierung ist ein Fehler aufgetreten!\n";
                    informationString += "Fehler-Code: " + response.code() + "\n";

                    try {
                        String errorBodyStr = (response.errorBody() == null) ? null : response.errorBody().string();

                        informationString += (errorBodyStr == null) ?
                                "Unbekannter Fehler\n" :
                                new Gson().fromJson(errorBodyStr, SignUpResponse.class).getMessage();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;

            }

            isFinished.setValue(Boolean.TRUE);
        }

        @Override
        public void onFailure(Call<SignUpResponse> call, Throwable t) {
            informationString += "Die Kommunikation mit der API war nicht möglich!\n";
            informationString += "Bitte stelle sicher, das du eine aktive Internetverbindung hast!\n";
            informationString += t.getMessage();
            Log.wtf("uDrive.Login.Failure", t.getMessage());

            isFinished.setValue(Boolean.TRUE);
        }
    };

    public MutableLiveData<Boolean> getFinishedState() {
        return this.isFinished;
    }

    public boolean isSignUpSuccessful() {
        return this.signUpSuccessful;
    }

    public String getInformationString() {
        return this.informationString;
    }
}

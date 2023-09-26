package de.bws.udrive.utilities.handler;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import org.json.JSONObject;

import java.io.IOException;

import de.bws.udrive.utilities.APIClient;
import de.bws.udrive.utilities.APIInterface;
import de.bws.udrive.utilities.model.General;
import de.bws.udrive.utilities.model.TourPlan;
import de.bws.udrive.utilities.response.TourPlanResponse;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * TourPlanHandler ist eine Klasse, die sich um das publishen von Fahrten kümmert <br>
 *
 * @author Lucas
 */
public class TourPlanHandler {

    private final String TAG = "uDrive." + getClass().getSimpleName();
    private String informationString = "";
    private boolean tourPlanSuccessful = false;
    private MutableLiveData<Boolean> isFinished = new MutableLiveData<>(Boolean.FALSE);

    public void handle(TourPlan tourPlan) {
        /* API Call vorbereiten */
        Call<ResponseBody> tourPlanRequest = APIClient.getAPI().create(APIInterface.class).
                postTourData(General.getSignedInUser().getHTTPAuthHeader(), tourPlan);

        /* Asynchroner Aufruf */
        tourPlanRequest.enqueue(tourPlanCallback);
    }

    private Callback<ResponseBody> tourPlanCallback = new Callback<ResponseBody>() {
        @Override
        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            Log.i(TAG, "Response-Code: " + response.code());
            /* Antwort von API OK */
            switch(response.code())
            {
                /* OK */
                case 200:
                    if (response.body() != null)
                    {
                    informationString = "Deine Fahrt wurde erfolgreich registriert!";

                    tourPlanSuccessful = true;
                    }
                    else /* Body is null */
                    {
                        informationString += "Der ResponseBody ist null!\n";
                        try {
                            informationString += (response.errorBody() == null) ? "Der ErrorBody ist null!\n" : new Gson().fromJson(response.errorBody().string(), TourPlanResponse.class).toString();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;

                case 400:
                    String error = null;

                    try {
                        error = response.errorBody().string();
                    }
                    catch (IOException e)
                    {
                        Log.e(TAG, "ErrorBody konnte nicht zum String gecastet werden!");
                    }

                    if(error == null)
                    {
                        informationString = "Unbekannter Fehler!";
                    }
                    else
                    {
                        Log.i(TAG, "Fehler aufgetreten!");
                        Log.i(TAG, "Fehlerbody: " + error);
                        informationString = "Eingaben überprüfen!";
                    }
                    break;


                default:
                    informationString += "Beim senden der Fahrt ist ein Fehler aufgetreten!\n";
                    informationString += "Fehler-Code: " + response.code() + "\n";

                    Log.i(TAG, "Responsecode: " + response.code());
                    Log.i(TAG, "Responsebody null?: " + (response.body() == null));
                    Log.i(TAG, "Errorbody null?: " + (response.errorBody() == null));

                    if(response.body() != null)
                        Log.i(TAG, "Reponsebody: " + response.body());
                    else if(response.errorBody() != null) {
                        try {
                            Log.i(TAG, "Errorbody: " + response.errorBody().string());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    try {
                        String errorBodyStr = (response.errorBody() == null) ? null : response.errorBody().string();

                        informationString += (errorBodyStr == null) ?
                                "Unbekannter Fehler\n" :
                                new Gson().fromJson(errorBodyStr, TourPlanResponse.class).toString();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;
            }

            isFinished.setValue(Boolean.TRUE);
        }

        @Override
        public void onFailure(Call<ResponseBody> call, Throwable t) {
            informationString += "Die Kommunikation mit der API war nicht möglich!\n";
            informationString += "Bitte stelle sicher, das du eine aktive Internetverbindung hast!\n";
            informationString += t.getMessage();
            Log.wtf(TAG, t.getMessage());

            isFinished.setValue(Boolean.TRUE);
        }
    };

    public MutableLiveData<Boolean> getFinishedState() {
        return this.isFinished;
    }

    public boolean isTourPlanSuccessful() {
        return this.tourPlanSuccessful;
    }

    public String getInformationString() { return this.informationString; }
}

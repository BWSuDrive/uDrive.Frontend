package de.bws.udrive.utilities.handler;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;

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
    private String errorText = "";
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
            /* Antwort von API OK */
            if (response.code() == 200) {
                if (response.body() != null) {
                    ResponseBody tourPlanResponse = response.body();

                    /* Do something... */

                    Log.i("uDrive.TourPlan", response.body().toString());


                    tourPlanSuccessful = true;
                } else /* Body is null */ {
                    errorText += "Der ResponseBody ist null!\n";
                    try {
                        errorText += (response.errorBody() == null) ? "Der ErrorBody ist null!\n" : new Gson().fromJson(response.errorBody().string(), TourPlanResponse.class).toString();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            } else /* Fehlercode != 200 */ {
                errorText += "Beim senden der Fahrt ist ein Fehler aufgetreten!\n";
                errorText += "Fehler-Code: " + response.code() + "\n";

                try {
                    String errorBodyStr = (response.errorBody() == null) ? null : response.errorBody().string();

                    errorText += (errorBodyStr == null) ?
                            "Unbekannter Fehler\n" :
                            new Gson().fromJson(errorBodyStr, TourPlanResponse.class).toString();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            isFinished.setValue(Boolean.TRUE);
        }

        @Override
        public void onFailure(Call<ResponseBody> call, Throwable t) {
            errorText += "Die Kommunikation mit der API war nicht möglich!\n";
            errorText += "Bitte stelle sicher, das du eine aktive Internetverbindung hast!\n";
            errorText += t.getMessage();
            Log.wtf("uDrive.TourPlan.Failure", t.getMessage());

            isFinished.setValue(Boolean.TRUE);
        }
    };

    public MutableLiveData<Boolean> getFinishedState() {
        return this.isFinished;
    }

    public boolean isTourPlanSuccessful() {
        return this.tourPlanSuccessful;
    }

    public String getError() {
        return this.errorText;
    }
}

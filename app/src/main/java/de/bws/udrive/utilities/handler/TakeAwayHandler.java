package de.bws.udrive.utilities.handler;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import de.bws.udrive.utilities.APIClient;
import de.bws.udrive.utilities.APIInterface;
import de.bws.udrive.utilities.model.General;
import de.bws.udrive.utilities.model.RequestTakeAway;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Klasse, die Anfragen zur Mitnahme sendet
 *
 * @author Lucas, Niko
 */
public class TakeAwayHandler
{
    private final String TAG = "uDrive." + getClass().getSimpleName();
    private String informationString = "";
    private boolean requestSuccessul = false;
    private MutableLiveData<Boolean> isFinished = new MutableLiveData<>(Boolean.FALSE);

    public void handle(RequestTakeAway takeAway)
    {
        Call<ResponseBody> takeAwayResponse =
                APIClient.getAPI().create(APIInterface.class)
                        .sendPassengerRequest(General.getSignedInUser().getHTTPAuthHeader(), takeAway);

        takeAwayResponse.enqueue(takeAwayCallback);
    }

    private final Callback<ResponseBody> takeAwayCallback = new Callback<ResponseBody>() {
        @Override
        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
        {
            switch(response.code())
            {
                case 200:
                    Log.i(TAG, "Responsecode 200");
                    requestSuccessul = true;
                    break;


                default:
                    Log.d(TAG, "Responsecode " + response.code());
                    Log.d(TAG, response.raw().toString());
                    break;
            }

            isFinished.setValue(Boolean.TRUE);

        }

        @Override
        public void onFailure(Call<ResponseBody> call, Throwable t)
        {
            Log.e(TAG, "Failure");
            Log.e(TAG, t.getCause().toString());
            Log.e(TAG, t.getMessage());
            informationString = t.getMessage();

            isFinished.setValue(Boolean.TRUE);
        }
    };

    public MutableLiveData<Boolean> getFinishedState() { return this.isFinished; }

    public boolean requestSuccessful() { return this.requestSuccessul; }
}

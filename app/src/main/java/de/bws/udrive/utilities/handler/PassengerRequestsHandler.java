package de.bws.udrive.utilities.handler;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import de.bws.udrive.utilities.APIClient;
import de.bws.udrive.utilities.APIInterface;
import de.bws.udrive.utilities.model.General;
import de.bws.udrive.utilities.model.PassengerRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Klasse, die Anfragen zu Fahrten lädt
 *
 * @author Lucas, Niko
 */
public class PassengerRequestsHandler {
    private final String TAG = "uDrive." + getClass().getSimpleName();
    private String informationString = "";
    private boolean passengerRequestsAvailable = true;
    private MutableLiveData<Boolean> isFinished = new MutableLiveData<>(Boolean.FALSE);
    private List<PassengerRequest> availablePassengers = new ArrayList<PassengerRequest>();

    public void handle() {
        Call<List<PassengerRequest>> driveRequestResponse =
                APIClient.getAPI().create(APIInterface.class)
                        .getCurrentRequests(General.getSignedInUser().getHTTPAuthHeader());

        driveRequestResponse.enqueue(passengerRequestCallback);
    }
    private final Callback<List<PassengerRequest>> passengerRequestCallback = new Callback<List<PassengerRequest>>() {
        @Override
        public void onResponse(Call<List<PassengerRequest>> call, Response<List<PassengerRequest>> response) {
            switch (response.code()) {
                /* OK */
                case 200:

                    if (response.body() != null)
                    {
                        Log.i(TAG, "Responsecode 200");
                        Log.i(TAG, response.toString());

                        List<PassengerRequest> responseList = response.body();

                        availablePassengers = responseList;

                    }
                    else
                    {
                        informationString = "Body ist null!";
                        Log.wtf(TAG, "Responsebody ist null!");
                    }
                    break;
                default:
                    Log.i(TAG, "Responsecode" + response.code());
                    Log.i(TAG,response.toString());
                    break;
            }
            isFinished.setValue(Boolean.TRUE);
        }
        @Override
        public void onFailure(Call<List<PassengerRequest>> call, Throwable t) {
            Log.i(TAG, "Failure");
            Log.i(TAG,t.getMessage());
            isFinished.setValue(Boolean.TRUE);
        }
    };
    public String getInformationString() {
        return informationString;
    }

    public boolean requestsAvailable() {
        return passengerRequestsAvailable;
    }

    public MutableLiveData<Boolean> getFinishedState() {
        return isFinished;
    }

    public List<PassengerRequest> getAvailablePassengers() { return availablePassengers; }
}

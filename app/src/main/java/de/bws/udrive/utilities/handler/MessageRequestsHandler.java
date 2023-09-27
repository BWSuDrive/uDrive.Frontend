package de.bws.udrive.utilities.handler;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import de.bws.udrive.utilities.APIClient;
import de.bws.udrive.utilities.APIInterface;
import de.bws.udrive.utilities.model.General;
import de.bws.udrive.utilities.model.MessageRequest;
import de.bws.udrive.utilities.model.PassengerRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessageRequestsHandler {
    private final String TAG = "uDrive." + getClass().getSimpleName();
    private String informationString = "";
    private boolean messageRequestsAvailable = true;
    private MutableLiveData<Boolean> isFinished = new MutableLiveData<>(Boolean.FALSE);
    private List<MessageRequest> availableMessages = new ArrayList<MessageRequest>();

    public void handle() {
        /*Call<List<MessageRequest>> messageRequestResponse =
                APIClient.getAPI().create(APIInterface.class)
                        .getCurrentRequests(General.getSignedInUser().getHTTPAuthHeader());

        messageRequestResponse.enqueue(messageRequestCallback);
        */
    }
    private final Callback<List<MessageRequest>> messageRequestCallback = new Callback<List<MessageRequest>>() {
        @Override
        public void onResponse(Call<List<MessageRequest>> call, Response<List<MessageRequest>> response) {
            switch (response.code()) {
                /* OK */
                case 200:

                    if (response.body() != null) {
                        Log.i(TAG, "Responsecode 200");
                        Log.i(TAG, response.toString());

                        List<MessageRequest> responseList = response.body();

                        responseList.forEach(tour -> {

                        });
                    } else {
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
        public void onFailure(Call<List<MessageRequest>> call, Throwable t) {
            Log.i(TAG, "Failure");
            Log.i(TAG,t.getMessage());
            isFinished.setValue(Boolean.TRUE);
        }
    };
    public String getInformationString() {
        return informationString;
    }

    public boolean requestsAvailable() {
        return messageRequestsAvailable;
    }

    public MutableLiveData<Boolean> getFinishedState() {
        return isFinished;
    }

    public List<MessageRequest> getAvailableMessages() {
        return availableMessages;
    }
}

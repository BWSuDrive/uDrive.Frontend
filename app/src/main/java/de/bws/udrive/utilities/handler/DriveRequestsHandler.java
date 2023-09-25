package de.bws.udrive.utilities.handler;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import de.bws.udrive.utilities.APIClient;
import de.bws.udrive.utilities.APIInterface;
import de.bws.udrive.utilities.model.DriveRequest;
import de.bws.udrive.utilities.model.General;
import de.bws.udrive.utilities.response.DriveRequestResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * DriveRequestsHandler ist eine Klasse, die sich um das holen von Fahrten kümmert <br>
 *
 * @author Lucas, Niko
 */
public class DriveRequestsHandler {
    private String errorText = "";
    private boolean driveRequests = false;
    private MutableLiveData<Boolean> isFinished = new MutableLiveData<>(Boolean.FALSE);

    public void handle(DriveRequest driveRequest) {
        Call<DriveRequestResponse> driveRequestResponse =
                APIClient.getAPI().create(APIInterface.class).getAvailableDrivers(General.getSignedInUser().getHTTPAuthHeader(), driveRequest);

        driveRequestResponse.enqueue(driveRequestCallback);
    }

    private final Callback<DriveRequestResponse> driveRequestCallback = new Callback<DriveRequestResponse>() {
        @Override
        public void onResponse(Call<DriveRequestResponse> call, Response<DriveRequestResponse> response) {
            if (response.code() == 200)
            {
                Log.i("uDrive.DriveRequestsHandler", "Responsecode 200");
                Log.i("uDrive.DriveRequestsHandler", response.toString());
            }
            else if(response.code() == 204)
            {
                Log.i("uDrive.DriveRequestsHandler", "Responsecode 204");
                Log.i("uDrive.DriveRequestsHandler", "Keine Fahrten verfügbar!");
            }
            else
            {
                Log.i("uDrive.DriveRequestsHandler", "Responsecode " + response.code());
                Log.i("uDrive.DriveRequestsHandler", response.toString());
            }
        }

        @Override
        public void onFailure(Call<DriveRequestResponse> call, Throwable t)
        {
            Log.i("uDrive.DriveRequestsHandler", "Failure");
            Log.i("uDrive.DriveRequestsHandler", t.getMessage());
        }
    };

    public MutableLiveData<Boolean> getFinishedState() { return isFinished; }

    public String getError() { return this.errorText; }
}

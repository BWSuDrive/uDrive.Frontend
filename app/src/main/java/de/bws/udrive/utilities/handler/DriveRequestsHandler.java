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

    private final String TAG = "uDrive." + getClass().getSimpleName();
    private String informationString = "";
    private boolean driveRequestsAvailable = true;
    private MutableLiveData<Boolean> isFinished = new MutableLiveData<>(Boolean.FALSE);

    public void handle(DriveRequest driveRequest) {
        Call<DriveRequestResponse> driveRequestResponse =
                APIClient.getAPI().create(APIInterface.class)
                        .getAvailableDrivers(General.getSignedInUser().getHTTPAuthHeader(), driveRequest);

        driveRequestResponse.enqueue(driveRequestCallback);
    }

    private final Callback<DriveRequestResponse> driveRequestCallback = new Callback<DriveRequestResponse>() {
        @Override
        public void onResponse(Call<DriveRequestResponse> call, Response<DriveRequestResponse> response) {

            Log.i(TAG, "Response-Code: " + response.code());
            switch(response.code())
            {
                /* OK */
                case 200:
                    Log.i(TAG, "Responsecode 200");
                    Log.i(TAG, response.toString());
                    break;

                /* NO CONTENT */
                case 204:
                    Log.i(TAG, "Keine Fahrten verfügbar!");
                    informationString = "Es sind aktuell keine Fahrten verfügbar!";
                    driveRequestsAvailable = false;
                    break;

                /* FORBIDDEN */
                case 403:
                    Log.e(TAG, "User nicht autorisiert!");
                    informationString = "Du bist nicht authentifiziert!";
                    driveRequestsAvailable = false;
                    break;

                /* ANY */
                default:
                    Log.i(TAG, "Responsecode " + response.code());
                    Log.i(TAG, response.toString());
                    break;
            }

            isFinished.setValue(Boolean.TRUE);
        }

        @Override
        public void onFailure(Call<DriveRequestResponse> call, Throwable t)
        {
            Log.i(TAG, "Failure");
            Log.i(TAG, t.getMessage());

            isFinished.setValue(Boolean.TRUE);
        }
    };

    public MutableLiveData<Boolean> getFinishedState() { return isFinished; }

    public String getInformationString() { return this.informationString; }

    public boolean requestsAvailable() { return this.driveRequestsAvailable; }
}

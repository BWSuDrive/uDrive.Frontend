package de.bws.udrive.utilities.handler;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import de.bws.udrive.ui.home.model.AvailableTours;
import de.bws.udrive.ui.home.model.Person;
import de.bws.udrive.ui.home.model.PlannedDrive;
import de.bws.udrive.utilities.APIClient;
import de.bws.udrive.utilities.APIInterface;
import de.bws.udrive.utilities.model.DriveRequest;
import de.bws.udrive.utilities.model.General;
import de.bws.udrive.utilities.response.DriveRequestResponse;
import de.bws.udrive.utilities.uDriveUtilities;
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
    private List<AvailableTours> availableTours = new ArrayList<AvailableTours>();

    public void handle(DriveRequest driveRequest)
    {
        Call<List<DriveRequestResponse>> driveRequestResponse =
                APIClient.getAPI().create(APIInterface.class)
                        .getAvailableDrivers(General.getSignedInUser().getHTTPAuthHeader(), driveRequest);

        Log.i(TAG, new Gson().toJson(driveRequest));

        driveRequestResponse.enqueue(driveRequestCallback);
    }

    private final Callback<List<DriveRequestResponse>> driveRequestCallback = new Callback<List<DriveRequestResponse>>() {
        @Override
        public void onResponse(Call<List<DriveRequestResponse>> call, Response<List<DriveRequestResponse>> response) {

            Log.i(TAG, "Response-Code: " + response.code());
            switch(response.code())
            {
                /* OK */
                case 200:

                    if(response.body() != null)
                    {
                        Log.i(TAG, "Responsecode 200");
                        Log.i(TAG, response.toString());

                        List<DriveRequestResponse> responseList = response.body();

                        responseList.forEach(tour -> {

                            String distance = tour.getDistance();

                            /* Personendaten */
                            String firstName = tour.getPerson().get("firstname").toString();
                            String lastName = tour.getPerson().get("lastname").toString();
                            String email = tour.getPerson().get("email").toString();
                            String phoneNumber = tour.getPerson().get("phoneNumber").toString();

                            /* Tourdaten */
                            String id = tour.getTourPlan().get("id").toString();
                            String departure = uDriveUtilities.parseString(tour.getTourPlan().get("departure").toString());
                            String eta = tour.getTourPlan().get("eta").toString();
                            String start = tour.getTourPlan().get("start").toString();
                            String destination = tour.getTourPlan().get("destination").toString();
                            String message = tour.getTourPlan().get("message").toString();

                            Person person = new Person(firstName, lastName, email, phoneNumber);
                            PlannedDrive plannedDrive = new PlannedDrive(distance, id, departure, eta, start, destination, message);

                            AvailableTours availableTour = new AvailableTours(person, plannedDrive);

                            availableTours.add(availableTour);
                        });
                    }
                    else
                    {
                        informationString = "Body ist null!";
                        Log.wtf(TAG, "Responsebody ist null!");
                    }


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
        public void onFailure(Call<List<DriveRequestResponse>> call, Throwable t)
        {
            Log.i(TAG, "Failure");
            Log.i(TAG, t.getMessage());

            isFinished.setValue(Boolean.TRUE);
        }
    };

    public MutableLiveData<Boolean> getFinishedState() { return isFinished; }

    public String getInformationString() { return this.informationString; }

    public boolean requestsAvailable() { return this.driveRequestsAvailable; }

    public List<AvailableTours> getAvailableTours() { return availableTours; }
}

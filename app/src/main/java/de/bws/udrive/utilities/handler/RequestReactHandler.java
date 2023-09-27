package de.bws.udrive.utilities.handler;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import de.bws.udrive.utilities.APIClient;
import de.bws.udrive.utilities.APIInterface;
import de.bws.udrive.utilities.model.General;
import de.bws.udrive.utilities.model.PassengerRequest;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Akzeptiert anfragen oder lehnt sie ab
 *
 * @author Lucas, Niko
 */
public class RequestReactHandler
{
    private final String TAG = "uDrive." + getClass().getSimpleName();
    private String informationString = "";
    private MutableLiveData<Boolean> isFinished = new MutableLiveData<>(Boolean.FALSE);
    private boolean requestSuccessful = false;


    public void handle(PassengerRequest request, boolean acceptRequest)
    {
        if(acceptRequest)
        {
            Call<ResponseBody> acceptedRequest = APIClient.getAPI().create(APIInterface.class).acceptRequest(General.getSignedInUser().getHTTPAuthHeader(), request);

            acceptedRequest.enqueue(acceptedCallback);
        }
        else
        {
            Call<ResponseBody> deniedRequest = APIClient.getAPI().create(APIInterface.class).denyRequest(General.getSignedInUser().getHTTPAuthHeader(), request);

            deniedRequest.enqueue(deniedCallback);
        }
    }

    private final Callback<ResponseBody> acceptedCallback = new Callback<ResponseBody>()
    {
        @Override
        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
        {
            switch(response.code())
            {
                case 200:
                    Log.i(TAG, "Accept successful!");
                    requestSuccessful = true;
                    break;

                default:
                    Log.d(TAG, "Responsecode: " + response.code());
                    break;
            }

            isFinished.setValue(Boolean.TRUE);
        }

        @Override
        public void onFailure(Call<ResponseBody> call, Throwable t)
        {
            Log.e(TAG, t.getMessage());
            Log.e(TAG, t.toString());
            informationString = t.getMessage();

            isFinished.setValue(Boolean.TRUE);
        }
    };

    private final Callback<ResponseBody> deniedCallback = new Callback<ResponseBody>()
    {
        @Override
        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
        {
            switch(response.code())
            {
                case 200:
                    Log.i(TAG, "Denied successful!");
                    requestSuccessful = true;
                    break;

                default:
                    Log.d(TAG, "Responsecode: " + response.code());
                    Log.d(TAG, response.raw().toString());
                    Log.d(TAG, response.raw().toString());
                    break;
            }
            isFinished.setValue(Boolean.TRUE);
        }

        @Override
        public void onFailure(Call<ResponseBody> call, Throwable t)
        {
            Log.e(TAG, t.getMessage());
            Log.e(TAG, t.toString());
            informationString = t.getMessage();

            isFinished.setValue(Boolean.TRUE);
        }
    };

    public MutableLiveData<Boolean> getFinishedState() { return this.isFinished; }

    public String getInformationString() { return this.informationString; }
    public boolean requestSuccessful() { return this.requestSuccessful; }

}

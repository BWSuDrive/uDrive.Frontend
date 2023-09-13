package de.bws.udrive;

import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

import de.bws.udrive.utilities.APIClientTest;
import de.bws.udrive.utilities.APIInterfaceTest;
import de.bws.udrive.utilities.Tag;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class APIConnectionTest {

    @Test
    public void connectToAPI()
    {
        System.out.println("connectToAPI Test");
        APIInterfaceTest i = APIClientTest.getAPI().create(APIInterfaceTest.class);
        Call<String> testCall = i.testAPIConnection();

        System.out.println("Calling API...");
        testCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                System.out.println(Tag.SUCCESS + " " + String.valueOf(response.code()));
                System.out.println(Tag.SUCCESS + " " + response.body().toString());
                assert(response.isSuccessful());
                assert(!response.body().toString().isEmpty());
                Log.d(Tag.SUCCESS, response.body().toString());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                System.out.println(Tag.FAILURE + " " + t.getMessage());
            }
        });
    }
}

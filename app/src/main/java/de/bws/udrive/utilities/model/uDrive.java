package de.bws.udrive.utilities.model;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.annotations.SerializedName;
import com.google.gson.internal.LinkedTreeMap;

import org.json.JSONObject;

import de.bws.udrive.utilities.APIClient;
import de.bws.udrive.utilities.APIInterface;
import de.bws.udrive.utilities.Tag;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/* Model, um Anfrage an API abzubilden */
public class uDrive {

    /**
     * Klasse, die generische Sachen speichert, die an verschiedenen Orten benötigt werden <br>
     *
     */
    public static class Generic
    {
        /** Speichert den Bearer Token, der von der API beim Login zurück kommt */
        private static String bearerToken = "N/A";
        /** Speichert, ob der Login erfolgreich war */
        private static boolean loginSuccessful = false;

        public static void setToken(String newToken) {
            if(Generic.bearerToken.equalsIgnoreCase("N/A"))
                Generic.bearerToken = newToken;
        }
        public static String getToken() { return Generic.bearerToken; }

        public static boolean isLoginSuccessful() {
            return loginSuccessful;
        }

        public static void setLoginSuccessful(boolean loginSuccessful) {
            if(!Generic.loginSuccessful)
                Generic.loginSuccessful = loginSuccessful;
        }
    }

    public static class LoginHandler
    {
        private StringBuffer errorTextBuffer = new StringBuffer();

        private MutableLiveData<Boolean> isFinished = new MutableLiveData<>(Boolean.FALSE);

        private boolean loginSuccessful = false;

        public LoginHandler() {}

        public void handle(Login login)
        {
            Call<LoginResponse> loginRequest = APIClient.getAPI().create(APIInterface.class).sendLoginRequest(login);

            loginRequest.enqueue(loginResponse);

            Log.d("uDrive.LoginHandler", "Request finished");
        }

        private Callback<uDrive.LoginResponse> loginResponse = new Callback<uDrive.LoginResponse>()
        {
            @Override
            public void onResponse(Call<uDrive.LoginResponse> call, Response<LoginResponse> response) {
                Log.d("uDrive.LoginHandler", "API called... onResponse()");
                /* Antwort OK von API */
                if(response.code() == 200)
                {
                    if(response.body() != null)
                    {
                        String bearerToken = response.body().getData().get("token");

                        if(bearerToken != null)
                            uDrive.Generic.setToken(bearerToken);

                        Log.d("uDrive.MainActivity.loginResponse.onResponse", "Token is: " + uDrive.Generic.getToken());
                        Log.d("uDrive.MainActivity.loginResponse.onResponse", "Login was successful. Setting generic value");

                        loginSuccessful = true;
                    }
                    else /* Body null */
                    {
                        errorTextBuffer.append("Responsebody was null.").append("\n");
                        errorTextBuffer.append("Unknown error occured!").append("\n");
                        errorTextBuffer.append(response.errorBody().toString()).append("\n");
                    }
                }
                else /* Antwort nicht OK */
                {
                    errorTextBuffer.append("Der Login war nicht möglich!").append("\n");
                    errorTextBuffer.append("Fehler-Code: " + response.code()).append("\n");
                    errorTextBuffer.append("Fehler: " + response.message()).append("\n");
                }

                isFinished.setValue(Boolean.TRUE);
            }

            @Override
            public void onFailure(Call<uDrive.LoginResponse> call, Throwable t)
            {
                errorTextBuffer.append("Die Kommunikation mit der API war nicht möglich!").append("\n");
                errorTextBuffer.append(t.getMessage()).append("\n");
                Log.wtf(Tag.FAILURE, errorTextBuffer.toString());

                isFinished.setValue(Boolean.TRUE);
            }
        };

        public MutableLiveData<Boolean> getIsFinished() {
            return isFinished;
        }

        public String getError() { return this.errorTextBuffer.toString(); }

        public boolean isLoginSuccessful() {
            return loginSuccessful;
        }
    }

    /**
     * Klasse, die für den Login benutzt wird <br>
     * Besitzt folgende Parameter: <br>
     * -> userName: String <br>
     * -> password: String <br>
     * -> email: String
     * */
    public static class Login {
        private String userName;
        private String password;
        private String email;

        public Login(String userName, String password, String email)
        {
            this.userName = userName;
            this.password = password;
            this.email = email;
        }
    }

    /**
     * Klasse, die für die Login-Antwort benutzt wird
     * */
    public static class LoginResponse
    {
        private boolean success;

        private LinkedTreeMap<String, String> data;

        private String message;

        public LoginResponse(boolean success, LinkedTreeMap<String, String> data, String message)
        {
            this.success = success;
            this.data = data;
            this.message = message;
        }

        public boolean isSuccess() {
            return success;
        }

        public LinkedTreeMap<String, String> getData() {
            return data;
        }

        public String getMessage() {
            return message;
        }
    }
}

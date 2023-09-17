package de.bws.udrive.utilities.model;

import com.google.gson.annotations.SerializedName;
import com.google.gson.internal.LinkedTreeMap;

import org.json.JSONObject;

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

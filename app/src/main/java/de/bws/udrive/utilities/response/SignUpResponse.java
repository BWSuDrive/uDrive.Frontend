package de.bws.udrive.utilities.response;

import com.google.gson.internal.LinkedTreeMap;

/**
 * Klasse, die für die Registrierungs-Antwort benutzt wird <br>
 */
public class SignUpResponse {
    private boolean success;
    private LinkedTreeMap<Object, Object> data;
    private String message;

    public SignUpResponse(boolean success, LinkedTreeMap<Object, Object> data, String message) {
        this.success = success;
        this.data = data;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public LinkedTreeMap<Object, Object> getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }
}

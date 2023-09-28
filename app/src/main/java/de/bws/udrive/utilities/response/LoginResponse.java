package de.bws.udrive.utilities.response;

import com.google.gson.internal.LinkedTreeMap;

/**
 * Klasse, die für die Login-Antwort benutzt wird <br>
 * Speichert die zurückgegeben Werte der API Antwort ab <br>
 *
 * @author Lucas
 */
public class LoginResponse {
    private boolean success;

    private LinkedTreeMap<Object, Object> data;

    private String message;

    public LoginResponse(boolean success, LinkedTreeMap<Object, Object> data, String message) {
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

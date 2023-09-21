package de.bws.udrive.utilities.model;

import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;

import de.bws.udrive.utilities.APIClient;

/**
 Model, um Anfrage an API abzubilden <br>
 Instanzen von Klassen werden durch {@link retrofit2.converter.gson.GsonConverterFactory} in {@link APIClient#getAPI()}
 zu einem JSON-String geparsed <br>

 @author Lucas
 */
public class uDrive {

    /**
     * Klasse, die generelle Sachen speichert, die an verschiedenen Orten im Code benötigt werden <br>
     * Speichert u.A. den Bearer-Token (für die Kommunikation mit der API), sowie den Usernamen und die E-Mail <br>
     * @author Lucas
     */
    public static class General
    {
        private static SignedInUser signedInUser = null;

        public static void setSignedInUser(SignedInUser signedInUser)
        {
            if(General.signedInUser == null)
                General.signedInUser = signedInUser;
        }

        public static SignedInUser getSignedInUser()
        {
            return General.signedInUser;
        }

    }

    public static class SignedInUser
    {
        /* JSON Object */
        /*
        {
            "Email": null,
            "Lastname": null,
            "Password": null,
            "UserName": null,
            "Firstname": null
        }
         */

        private String bearerToken = "N/A";
        private String vorname = "N/A";
        private String nachname = "N/A";
        private String mail = "N/A";
        private ArrayList<String> rollen = new ArrayList<>();

        public SignedInUser()
        {

        }

        public void setToken(String token)
        {
            if(this.bearerToken.equalsIgnoreCase("N/A"))
                this.bearerToken = token;
        }

        public void setVorname(String vorname)
        {
            if(this.vorname.equalsIgnoreCase("N/A"))
                this.vorname = vorname;
        }

        public void setNachname(String nachname)
        {
            if(this.nachname.equalsIgnoreCase("N/A"))
                this.nachname = nachname;
        }

        public void setMail(String mail)
        {
            if(this.mail.equalsIgnoreCase("N/A"))
                this.mail = mail;
        }

        public void addRole(String rolle)
        {
            if(!this.rollen.contains(rolle))
                this.rollen.add(rolle);
        }

        public String getToken() { return bearerToken; }

        public String getVorname() { return vorname; }

        public String getNachname() { return nachname; }

        public String getMail() { return mail; }

        public ArrayList<String> getRollen() { return rollen; }
    }

    /**
     * Klasse, die für den Login benutzt wird <br>
     * Besitzt die Eigenschaften {@link Login#userName}, {@link Login#password} & {@link Login#email} <br>
     * @author Lucas
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
     * Klasse, die für die Login-Antwort benutzt wird <br>
     * Speichert die zurückgegeben Werte der API Antwort ab <br>
     * @author Lucas
     * */
    public static class LoginResponse
    {
        private boolean success;

        private LinkedTreeMap<Object, Object> data;

        private String message;

        public LoginResponse(boolean success, LinkedTreeMap<Object, Object> data, String message)
        {
            this.success = success;
            this.data = data;
            this.message = message;
        }

        public boolean isSuccess() {
            return success;
        }

        public LinkedTreeMap<Object, Object> getData() { return data; }

        public String getMessage() {
            return message;
        }
    }

    /**
     * Klasse, die für die Registrierung benutzt wird <br>
     * Besitzt alle Eigenschaften, die für die Registrierung benötigt werden <br>
     * @author Lucas
     */
    public static class SignUp {

        private String vorname;
        private String nachname;
        private String email;
        private String password;
        private String confirmPassword;

        public SignUp(String vorname, String nachname, String email, String password, String confirmPassword) {
            this.vorname = vorname;
            this.nachname = nachname;
            this.email = email;
            this.password = password;
            this.confirmPassword = confirmPassword;
        }

        public boolean equalPasswords()
        {
            return this.password.equals(this.confirmPassword);
        }
    }

    /**
     * Klasse, die für die Registrierungs-Antwort benutzt wird <br>
     */
    public static class SignUpResponse {}
}

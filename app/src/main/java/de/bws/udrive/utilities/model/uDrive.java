package de.bws.udrive.utilities.model;

import com.google.gson.internal.LinkedTreeMap;

import org.jetbrains.annotations.NotNull;

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
        private static String bearerToken = "N/A";
        private static String userName = "N/A";
        private static String userMail = "N/A";

        public static void setSignedInUser(SignedInUser signedInUser)
        {
            if(General.signedInUser == null)
                General.signedInUser = signedInUser;
        }

        public static SignedInUser getSignedInUser()
        {
            return General.signedInUser;
        }

        public static void setToken(String newToken)
        {
            if(General.bearerToken.equalsIgnoreCase("N/A"))
                General.bearerToken = newToken;
        }
        public static String getToken() { return General.bearerToken; }

        public static void setUserName(String userName)
        {
            if(General.userName.equalsIgnoreCase("N/A"))
                General.userName = userName;
        }

        public static String getUserName() { return userName; }

        public static void setUserMail(String userMail)
        {
            if(General.userMail.equalsIgnoreCase("N/A"))
                General.userMail = userMail;
        }

        public static String getUserMail() { return userMail; }
    }

    public static class SignedInUser
    {
        private String bearerToken = "N/A";
        private String vorname = "N/A";
        private String nachname = "N/A";
        private String mail = "N/A";
        private String rolle = "N/A";

        public SignedInUser()
        {

        }

        public void setBearerToken(String token)
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

        public void setRolle(String rolle)
        {
            if(this.rolle.equalsIgnoreCase("N/A"))
                this.rolle = rolle;
        }
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

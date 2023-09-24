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
     * Speichert bspw. den aktuell angemeldeten Benutzer <br>
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

    /**
     * Speichert Informationen über den aktuell angemeldeten Benutzer
     *
     * @author Lucas
     */
    public static class SignedInUser
    {
        private String id = "N/A";
        private String bearerToken = "N/A";
        private String vorname = "N/A";
        private String nachname = "N/A";
        private String mail = "N/A";
        private ArrayList<String> rollen = new ArrayList<>();

        public void setID(String id)
        {
            if(this.id.equalsIgnoreCase("N/A"))
                this.id = id;
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

        public String getID() { return id; }

        public String getToken() { return bearerToken; }

        public String getHTTPAuthHeader() { return "Bearer " + this.bearerToken; }

        public String getVorname() { return vorname; }

        public String getNachname() { return nachname; }

        public String getMail() { return mail; }

        public ArrayList<String> getRoles() { return rollen; }

        public boolean hasRole(String rolle) { return this.rollen.contains(rolle); }
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
    public static class SignUp
    {
        private String Firstname;
        private String Lastname;
        private String Email;
        private String Password;
        private String UserName;

        public SignUp(String vorname, String nachname, String email, String password) {
            this.Firstname = vorname;
            this.Lastname = nachname;
            this.Email = email;
            this.UserName = email;
            this.Password = password;
        }

        public boolean equalPasswords(String confirmPassword) { return this.Password.contentEquals(confirmPassword); }
    }

    /**
     * Klasse, die für die Registrierungs-Antwort benutzt wird <br>
     */
    public static class SignUpResponse
    {
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

    /**
     * Klasse, die für das senden von Fahrten benutzt wird <br>
     *
     * @author Lucas
     */
    public static class TourPlan
    {
        private String idDriver;
        private String departure;
        private int stopRequests;
        private String eta;
        private String start;
        private String destination;

        public TourPlan(String idDriver, String departure, int stopRequests, String eta, String start, String destination)
        {
            this.idDriver = idDriver;
            this.departure = departure;
            this.stopRequests = stopRequests;
            this.eta = eta;
            this.start = start;
            this.destination = destination;
        }

        public String getIdDriver() { return idDriver; }

        public String getDeparture() { return departure; }

        public int getStopRequests() { return stopRequests; }

        public String getEta() { return eta; }

        public String getStart() { return start; }

        public String getDestination() { return destination; }
    }

    /**
     * Klasse, die für die Antwort von Fahrten benutzt wird <br>
     *
     * @author Lucas
     */
    public static class TourPlanResponse
    {
        private String id;
        private String idDriver;
        private String depature;
        private int stopRequests;
        private String eta;
        private String start;
        private String destination;
        private Object driver;

        public TourPlanResponse(String id, String idDriver, String depature, int stopRequests, String eta, String start, String destination, Object driver)
        {
            this.id = id;
            this.idDriver = idDriver;
            this.depature = depature;
            this.stopRequests = stopRequests;
            this.eta = eta;
            this.start = start;
            this.destination = destination;
            this.driver = driver;
        }

        public String getId() { return id; }

        public String getIdDriver() { return idDriver; }

        public String getDepature() { return depature; }

        public int getStopRequests() { return stopRequests; }

        public String getEta() { return eta; }

        public String getStart() { return start; }

        public String getDestination() { return destination; }

        public Object getDriver() { return driver; }
    }
}

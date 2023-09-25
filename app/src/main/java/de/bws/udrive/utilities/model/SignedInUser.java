package de.bws.udrive.utilities.model;

import java.util.ArrayList;

/**
 * Speichert Informationen über den aktuell angemeldeten Benutzer
 *
 * @author Lucas
 */
public class SignedInUser {
    private String id = "N/A";
    private String bearerToken = "N/A";
    private String vorname = "N/A";
    private String nachname = "N/A";
    private String mail = "N/A";
    private ArrayList<String> rollen = new ArrayList<>();

    public void setID(String id) {
        if (this.id.equalsIgnoreCase("N/A"))
            this.id = id;
    }

    public void setToken(String token) {
        if (this.bearerToken.equalsIgnoreCase("N/A"))
            this.bearerToken = token;
    }

    public void setVorname(String vorname) {
        if (this.vorname.equalsIgnoreCase("N/A"))
            this.vorname = vorname;
    }

    public void setNachname(String nachname) {
        if (this.nachname.equalsIgnoreCase("N/A"))
            this.nachname = nachname;
    }

    public void setMail(String mail) {
        if (this.mail.equalsIgnoreCase("N/A"))
            this.mail = mail;
    }

    public void addRole(String rolle) {
        if (!this.rollen.contains(rolle))
            this.rollen.add(rolle);
    }

    public String getID() {
        return id;
    }

    public String getToken() {
        return bearerToken;
    }

    public String getHTTPAuthHeader() {
        return "Bearer " + this.bearerToken;
    }

    public String getVorname() {
        return vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public String getMail() {
        return mail;
    }

    public ArrayList<String> getRoles() {
        return rollen;
    }

    public boolean hasRole(String rolle) {
        return this.rollen.contains(rolle);
    }
}

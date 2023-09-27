package de.bws.udrive.utilities.model;

/**
 * Klasse, die Anfrage für Login repräsentiert
 *
 * @author Lucas
 */
public class Login {
    private String userName;
    private String password;
    private String email;

    public Login(String userName, String password, String email) {
        this.userName = userName;
        this.password = password;
        this.email = email;
    }
}

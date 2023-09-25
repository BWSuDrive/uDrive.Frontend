package de.bws.udrive.utilities.model;

/**
 * Klasse, die für den Login benutzt wird <br>
 * Besitzt die Eigenschaften {@link Login#userName}, {@link Login#password} & {@link Login#email} <br>
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

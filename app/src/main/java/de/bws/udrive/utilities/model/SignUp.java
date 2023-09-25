package de.bws.udrive.utilities.model;

/**
 * Klasse, die für die Registrierung benutzt wird <br>
 * Besitzt alle Eigenschaften, die für die Registrierung benötigt werden <br>
 *
 * @author Lucas
 */
public class SignUp {
    private String Firstname;
    private String Lastname;
    private String Email;
    private String Password;
    private String UserName;
    private String telephone;

    public SignUp(String vorname, String nachname, String email, String password, String telephone) {
        this.Firstname = vorname;
        this.Lastname = nachname;
        this.Email = email;
        this.UserName = email;
        this.Password = password;
        this.telephone = telephone;
    }

    public boolean equalPasswords(String confirmPassword) {
        return this.Password.contentEquals(confirmPassword);
    }
}

package de.bws.udrive.utilities.model;

/**
 * Klasse, die generelle Sachen speichert, die an verschiedenen Orten im Code benötigt werden <br>
 * Speichert bspw. den aktuell angemeldeten Benutzer <br>
 *
 * @author Lucas
 */
public class General {
    private static SignedInUser signedInUser = null;

    public static void setSignedInUser(SignedInUser signedInUser) {
        if (General.signedInUser == null)
            General.signedInUser = signedInUser;
    }

    public static SignedInUser getSignedInUser() {
        return General.signedInUser;
    }

}

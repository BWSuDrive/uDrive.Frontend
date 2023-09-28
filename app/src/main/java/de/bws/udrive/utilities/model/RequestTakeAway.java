package de.bws.udrive.utilities.model;

/**
 * Klasse, die Anfrage für Mitfahrten repräsentiert
 *
 * @author Lucas, Niko
 */
public class RequestTakeAway
{
    private String idPerson;
    private String idTourPlan;
    private String message;
    private double currentLatitude;
    private double currentLongitude;

    public RequestTakeAway(String idTourPlan, String message, double currentLatitude, double currentLongitude)
    {
        this.idPerson = "AnnieAreYouOkay?";
        this.idTourPlan = idTourPlan;
        this.message = message;
        this.currentLatitude = currentLatitude;
        this.currentLongitude = currentLongitude;
    }
}

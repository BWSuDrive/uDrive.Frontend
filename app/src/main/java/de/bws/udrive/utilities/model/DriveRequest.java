package de.bws.udrive.utilities.model;

/**
 * Klasse, die Anfrage für verfügbare Fahrten repräsentiert
 *
 * @author Lucas, Niko
 */
public class DriveRequest {
    private double currentLatitude;
    private double currentLongitude;

    public DriveRequest(double currentLatitude, double currentLongitude)
    {
        this.currentLatitude = currentLatitude;
        this.currentLongitude = currentLongitude;
    }
}

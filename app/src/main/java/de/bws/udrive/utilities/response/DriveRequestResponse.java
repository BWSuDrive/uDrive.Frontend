package de.bws.udrive.utilities.response;

import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.List;

/**
 * Klasse, die für die Antwort von verfügbaren Fahrten benutzt wird <br>
 */
public class DriveRequestResponse
{
    private String distance;
    private LinkedTreeMap<Object, Object> person;
    private LinkedTreeMap<Object, Object> driver;
    private LinkedTreeMap<Object, Object> tourPlan;

    public DriveRequestResponse(String distance, LinkedTreeMap<Object, Object> person, LinkedTreeMap<Object, Object> driver, LinkedTreeMap<Object, Object> tourPlan)
    {
        this.distance = distance;
        this.person = person;
        this.driver = driver;
        this.tourPlan = tourPlan;
    }

    public String getDistance() { return distance; }

    public LinkedTreeMap<Object, Object> getPerson() { return person; }

    public LinkedTreeMap<Object, Object> getDriver() { return driver; }

    public LinkedTreeMap<Object, Object> getTourPlan() { return tourPlan; }
}

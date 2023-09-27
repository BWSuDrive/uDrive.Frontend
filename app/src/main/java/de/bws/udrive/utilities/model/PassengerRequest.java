package de.bws.udrive.utilities.model;

import com.google.gson.internal.LinkedTreeMap;

public class PassengerRequest
{
    private String id;
    private String idPerson;
    private String idTourPlan;
    private String message;
    private double currentLatitude;
    private double currentLongitude;
    private boolean isPending;
    private boolean isDenied;
    private LinkedTreeMap<Object, Object> person;
    private Object tourPlan;

    public PassengerRequest(String id, String idPerson, String idTourPlan, String message, double currentLatitude, double currentLongitude, boolean isPending, boolean isDenied, LinkedTreeMap<Object, Object> person, Object tourPlan)
    {
        this.id = id;
        this.idPerson = idPerson;
        this.idTourPlan = idTourPlan;
        this.message = message;
        this.currentLatitude = currentLatitude;
        this.currentLongitude = currentLongitude;
        this.isPending = isPending;
        this.isDenied = isDenied;
        this.person = person;
        this.tourPlan = tourPlan;
    }

    public String getId() { return id; }

    public String getIdPerson() { return idPerson; }

    public String getIdTourPlan() { return idTourPlan; }

    public String getMessage() { return message; }

    public double getCurrentLatitude() { return currentLatitude; }

    public double getCurrentLongitude() { return currentLongitude; }

    public boolean isPending() { return isPending; }

    public boolean isDenied() { return isDenied; }

    public LinkedTreeMap<Object, Object> getPerson() { return person; }

    public Object getTourPlan() { return tourPlan; }
}

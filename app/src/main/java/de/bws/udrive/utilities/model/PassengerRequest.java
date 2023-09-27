package de.bws.udrive.utilities.model;

import de.bws.udrive.ui.start.model.Person;

public class PassengerRequest extends Person {
    public PassengerRequest(String firstname, String lastname, String email, String phoneNumber, String id,
                            String idPerson, String idTourPlan, String message,
                            double currentLatitude, double currentLongitude, boolean isPending) {
        super(firstname, lastname, email, phoneNumber);
        this.id = id;
        this.idPerson = idPerson;
        this.idTourPlan = idTourPlan;
        this.message = message;
        this.currentLatitude = currentLatitude;
        this.currentLongitude = currentLongitude;
        this.isPending = isPending;
    }

    private String id;
    private String idPerson;
    private String idTourPlan;
    private String message;
    private double currentLatitude;
    private double currentLongitude;
    private boolean isPending;
    private

    public String getId() {
        return id;
    }

    public String getIdPerson() {
        return idPerson;
    }

    public String getIdTourPlan() {
        return idTourPlan;
    }

    public String getMessage() {
        return message;
    }

    public double getCurrentLatitude() {
        return currentLatitude;
    }

    public double getCurrentLongitude() {
        return currentLongitude;
    }

    public boolean isPending() {
        return isPending;
    }
}

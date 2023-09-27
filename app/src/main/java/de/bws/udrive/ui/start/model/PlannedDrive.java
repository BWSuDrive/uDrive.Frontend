package de.bws.udrive.ui.start.model;

public class PlannedDrive {

    private String distance;
    private String id;
    private String departure;
    private String eta;
    private String start;
    private String destination;
    private String message;

    public PlannedDrive(String distance, String id, String departure, String eta, String start, String destination, String message)
    {
        this.distance = distance;
        this.id = id;
        this.departure = departure;
        this.eta = eta;
        this.start = start;
        this.destination = destination;
        this.message = message;
    }

    public String getDistance() {
        return distance;
    }

    public String getId() {
        return id;
    }

    public String getDeparture() {
        return departure;
    }

    public String getEta() {
        return eta;
    }

    public String getStart() {
        return start;
    }

    public String getDestination() {
        return destination;
    }

    public String getMessage() {
        return message;
    }
}

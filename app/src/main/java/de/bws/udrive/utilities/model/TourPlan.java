package de.bws.udrive.utilities.model;

/**
 * Klasse, die für das senden von Fahrten benutzt wird <br>
 *
 * @author Lucas
 */
public class TourPlan {
    private String idDriver;
    private String departure;
    private int stopRequests;
    private String eta;
    private String start;
    private String destination;

    public TourPlan(String idDriver, String departure, int stopRequests, String eta, String start, String destination) {
        this.idDriver = idDriver;
        this.departure = departure;
        this.stopRequests = stopRequests;
        this.eta = eta;
        this.start = start;
        this.destination = destination;
    }

    public String getIdDriver() {
        return idDriver;
    }

    public String getDeparture() {
        return departure;
    }

    public int getStopRequests() {
        return stopRequests;
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
}

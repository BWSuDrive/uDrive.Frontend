package de.bws.udrive.utilities.response;

/**
 * Klasse, die für die Antwort von Fahrten benutzt wird <br>
 *
 * @author Lucas
 */
public class TourPlanResponse {
    private String id;
    private String idDriver;
    private String depature;
    private int stopRequests;
    private String eta;
    private String start;
    private String destination;
    private Object driver;

    public TourPlanResponse(String id, String idDriver, String depature, int stopRequests, String eta, String start, String destination, Object driver) {
        this.id = id;
        this.idDriver = idDriver;
        this.depature = depature;
        this.stopRequests = stopRequests;
        this.eta = eta;
        this.start = start;
        this.destination = destination;
        this.driver = driver;
    }

    public String getId() {
        return id;
    }

    public String getIdDriver() {
        return idDriver;
    }

    public String getDepature() {
        return depature;
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

    public Object getDriver() {
        return driver;
    }
}

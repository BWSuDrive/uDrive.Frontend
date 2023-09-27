package de.bws.udrive.utilities.model;

public class MessageRequest {
    private String tvMessageName;
    private String tvMessageETA;
    private String tvMessageStartTime;
    private String tvMessageDestination;

    public MessageRequest(String firstName, String lastName, String tvMessageETA, String tvMessageStartTime, String tvMessageDestination) {
        this.tvMessageName = firstName + " " + lastName;
        this.tvMessageETA = tvMessageETA;
        this.tvMessageStartTime = tvMessageStartTime;
        this.tvMessageDestination = tvMessageDestination;
    }

    public String getTvMessageName() {
        return tvMessageName;
    }

    public String getTvMessageETA() {
        return tvMessageETA;
    }

    public String getTvMessageStartTime() {
        return tvMessageStartTime;
    }

    public String getTvMessageDestination() {
        return tvMessageDestination;
    }
}

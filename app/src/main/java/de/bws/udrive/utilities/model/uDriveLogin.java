package de.bws.udrive.utilities.model;

/* Model, um Anfrage an API abzubilden */
public class uDriveLogin {
    private String id;
    private String name;
    private String description;
    private int number;
    private String testDate;

    public uDriveLogin(String id, String name, String description, int number, String testDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.number = number;
        this.testDate = testDate;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getNumber() {
        return number;
    }

    public String getTestDate() {
        return testDate;
    }
}

package de.bws.udrive.ui.home.model;

/**
 * Klasse, die verfügbare Fahrten abspeichert
 *
 * @author Lucas, Niko
 */
public class AvailableTours {

    private Person person;
    private PlannedDrive plannedDrive;

    public AvailableTours(Person person, PlannedDrive plannedDrive)
    {
        this.person = person;
        this.plannedDrive = plannedDrive;
    }

    public Person getPerson()
    {
        return person;
    }

    public PlannedDrive getPlannedDrive()
    {
        return plannedDrive;
    }
}

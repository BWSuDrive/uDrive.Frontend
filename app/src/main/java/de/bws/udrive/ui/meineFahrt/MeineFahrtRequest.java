package de.bws.udrive.ui.meineFahrt;

public class MeineFahrtRequest {
    String vorname;
    String nachname;
    String phone;
    String freitext;

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFreitext() {
        return freitext;
    }

    public void setFreitext(String freitext) {
        this.freitext = freitext;
    }
}

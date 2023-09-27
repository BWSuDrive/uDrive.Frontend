package de.bws.udrive.utilities;

public class uDriveUtilities {

    public static String convertToISO8601Date(int year, int month, int day, int hour, int minute)
    {
        return year + "-" +
                ((month < 10) ? "0" + month : month) + "-" +
                ((day < 10) ? "0" + day : day) + "T" +
                ((hour < 10) ? "0" + hour : hour) + ":" +
                ((minute < 10) ? ((minute == 0) ? "00" : "0" + minute) : minute) +
                ":00.000Z";
    }

    public static String convertToGermanDate(int year, int month, int day, int hour, int minute)
    {
        return ((day < 10) ? "0" + day : day) + "." +
               ((month < 10) ? "0" + month : month) + "." +
               year + " " +
               convertTimeToString(hour, minute);
    }

    public static String convertTimeToString(int hour, int minute)
    {
        return ((hour < 10) ? "0" + hour : hour) + ":" +
               ((minute < 10) ? ((minute == 0) ? "00" : "0" + minute) : minute) +
               ":00";
    }

    public static String parseString(String isoFormat)
    {
        int year = Integer.parseInt(isoFormat.substring(0, 4));
        int month = Integer.parseInt(isoFormat.substring(5, 7));
        int day = Integer.parseInt(isoFormat.substring(8, 10));

        int hour = Integer.parseInt(isoFormat.substring(11, 13));
        int minute = Integer.parseInt(isoFormat.substring(14, 16));

        return convertToGermanDate(year, month, day, hour, minute);
    }
}

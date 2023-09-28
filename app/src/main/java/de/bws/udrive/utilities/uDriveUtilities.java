package de.bws.udrive.utilities;

/**
 * Utilities Klasse
 *
 * @author Lucas, Niko
 */
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

    /**
     * Calculate distance between two points in latitude and longitude taking <br>
     * into account height difference. If you are not interested in height <br>
     * difference pass 0.0. Uses Haversine method as its base.
     *
     * lat1, lon1 Start point lat2, lon2 End point el1 Start altitude in meters
     * el2 End altitude in meters <br>
     * Quelle <a href="https://stackoverflow.com/posts/16794680/revisions">StackOverflow</a>
     * @returns Distance in Meters
     */
    public static double calculateDistance(double lat1, double lat2, double lon1,
                                  double lon2, double el1, double el2)
    {

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        double height = el1 - el2;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        return Math.sqrt(distance);
    }
}

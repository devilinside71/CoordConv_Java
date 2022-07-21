/*
 * Copyright (c) 2022. Laszlo TAMAS9
 * National Operations Management Division
 * HM Electronics, Logistics and Property Management Private Co.
 */

package hmei.nmrd;

/**
 * Created on 2022-07-17
 *
 * @author Laszlo TAMAS9
 */
public class UTMData {
    int ZoneNumber;
    String ZoneLetter;
    int Easting;
    int Northing;

    public UTMData(int zoneNumber, String zoneLetter, int easting, int northing) {
        ZoneNumber = zoneNumber;
        ZoneLetter = zoneLetter;
        Easting = easting;
        Northing = northing;
    }
}

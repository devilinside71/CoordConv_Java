/*
 * Copyright (c) 2022. Laszlo TAMAS9
 * National Operations Management Division
 * HM Electronics, Logistics and Property Management Private Co.
 */

/**
 * Created on 2022-07-17
 *
 * @author Laszlo TAMAS9
 */
public class MGRSData {
    int ZoneNumber;
    String ZoneLetter;
    String ID100kCol;
    String ID100kRow;
    int Easting;
    int Northing;



    int Accuracy;

    public MGRSData(int zoneNumber, String zoneLetter, String ID100kCol, String ID100kRow, int easting, int northing, int accuracy) {
        ZoneNumber = zoneNumber;
        ZoneLetter = zoneLetter;
        this.ID100kCol = ID100kCol;
        this.ID100kRow = ID100kRow;
        Easting = easting;
        Northing = northing;
        Accuracy = accuracy;
    }
}

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
public class DMSData {

    int LatDeg;
    int LatMin;
    double LatSec;
    String LatHemisphere;

    int LonDeg;
    int LonMin;
    double LonSec;
    String LonHemisphere;


    public DMSData(int latDeg, int latMin, double latSec, String latHemisphere, int lonDeg, int lonMin, double lonSec, String lonHemisphere) {
        LatDeg = latDeg;
        LatMin = latMin;
        LatSec = latSec;
        LatHemisphere = latHemisphere;
        LonDeg = lonDeg;
        LonMin = lonMin;
        LonSec = lonSec;
        LonHemisphere = lonHemisphere;
    }
}

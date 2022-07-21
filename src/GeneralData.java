/*
 * Copyright (c) 2022. Laszlo TAMAS9
 * National Operations Management Division
 * HM Electronics, Logistics and Property Management Private Co.
 */

import java.util.regex.Pattern;

/**
 * Created on 2022-07-17
 *
 * @author Laszlo TAMAS9
 */
public class GeneralData {
    public static final String degChar = "°";
    public static final String minChar = "'";
    public static final String secChar = "\"";
    public static final String naStr = "N/A";
    public static final DEGData emptyDEGData = new DEGData(0.0, 0.0);
    public static final String emptyMGRS = "";
    public static final UTMData emptyUTMData = new UTMData(0, "", 0, 0);
    public static final DMSData emptyDMSData = new DMSData(0, 0, 0.0, "", 0, 0, 0.0, "");
    public static final MGRSData emptyMGRSData = new MGRSData(0, "X", "X", "X", 0, 0, 0);

    // Coordinate patterns
// MGRS > 30QWD0143947225 > 1:30, 2:Q, 3:W, 4:D, 5:0143947225
    static final Pattern patternMGRS = Pattern.compile("^([0-9]+)([CDEFGHJKLMNPQRSTUVWX])([ABCDEFGHJKLMNPQRSTUVWXYZ])([ABCDEFGHJKLMNPQRSTUV])([0-9]{0,10})$");

    // UTM > 42,S,3822196,528173 > 1:42, 2:S, 3:3822196, 4:528173
    static final Pattern patternUTM = Pattern.compile("^([0-9]+),([CDEFGHJKLMNPQRSTUVWX]),([0-9]+),([0-9]+)$");

    // DEG > 47.491195,-19.113478 > 1:47.491195, 2:-19.113478
    static final Pattern patternDEG = Pattern.compile("^([+-]?[0-9]*\\.[0-9]+),([+-]?[0-9]*\\.[0-9]+)$");

    // SingleDEG > -19.113478 > 1:47.491195, 2:-19.113478
    static final Pattern patternSingleDEG = Pattern.compile("^([+-]?[0-9]*\\.[0-9]+)$");

    // SingleDMSNoHemisphere > 34°32'27.91" > 1:34, 2:32, 3:27.91
    static final Pattern patternSingleDMSNoHemisphere = Pattern.compile("^([0-9]+)°([0-9]{1,2})\'(([0-9]*[.])?[0-9]+)\"$");

    // SingleDMSLatitude > 47°29'28.302"N > 1:47, 2:29, 3:28.302, 5:N
    static final Pattern patternSingleDMSLatitude = Pattern.compile("^([0-9]+)°([0-9]{1,2})\'(([0-9]*[.])?[0-9]+)\"([NS])$");

    // SingleDMSLongitude > 47°29'28.302"W > 1:47, 2:29, 3:28.302, 5:W
    static final Pattern patternSingleDMSLongitude = Pattern.compile("^([0-9]+)°([0-9]{1,2})\'(([0-9]*[.])?[0-9]+)\"([EW])$");
    static final Pattern patternSingleDMS = Pattern.compile("^([0-9]+)°([0-9]{1,2})\'(([0-9]*[.])?[0-9]+)\"([EWNS])$");

    // DMS > 47°29'28.302"N,19°6'48.521"E > 1:47, 2:29, 3:28.302, 5:N, 6:19, 7:6, 8: 48.521, 10:E
    static final Pattern patternDMS = Pattern.compile("^([0-9]+)°([0-9]{1,2})\'(([0-9]*[.])?[0-9]+)\"([NS]),([0-9]+)°([0-9]{1,2})\'(([0-9]*[.])?[0-9]+)\"([EW])$");
    static final Pattern patternGEOREF =
            Pattern.compile("^([ABCDEFGHJKLMNPQRSTUVWXYZ])([ABCDEFGHJKLM])([ABCDEFGHJKLMNPQ])([ABCDEFGHJKLMNPQ])([0-9]{4,10})$");

    static final Pattern patternGARS = Pattern.compile("^([0-9]{3})([ABCDEFGHJKLMNPQ])([ABCDEFGHJKLMNPQRSTUVWXYZ])([1-4])([0-9])$");

}

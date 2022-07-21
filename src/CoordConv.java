/*
 * Copyright (c) 2022. Laszlo TAMAS9
 * National Operations Management Division
 * HM Electronics, Logistics and Property Management Private Co.
 */


import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created on 2022-07-20
 *
 * @author Laszlo TAMAS9
 */
public class CoordConv {
    private static final String SET_ORIGIN_COLUMN_LETTERS = "AJSAJS";
    private static final String SET_ORIGIN_ROW_LETTERS = "AFAFAF";
    private static final int VAL_A = 65;
    private static final int VAL_I = 73;
    private static final int VAL_O = 79;
    private static final int VAL_V = 86;
    private static final int VAL_Z = 90;

    //region From DEG
    public UTMData deg2UTM(DEGData coord) {
        UTMData retVal = GeneralData.emptyUTMData;
        double ellipRadius = 6378137.0;
        double ellipEccSquared = 0.00669438;
        double kZero = 0.9996;
        double latRad = deg2rad(coord.Latitude);
        System.out.println("latlon2UTM - latRad " + latRad);
        double lonRad = deg2rad(coord.Longitude);
        System.out.println("latlon2UTM - lonRad " + lonRad);
        double zoneNumber = Math.floor((coord.Longitude + 180) / 6) + 1;
        if (coord.Longitude == 180) {
            zoneNumber = 60;
        }
        // Norway
        if (coord.Latitude >= 56.0 && coord.Latitude < 64.0 && coord.Longitude >= 3.0 && coord.Longitude < 12.0) {
            zoneNumber = 32;
        }
        // Svalbard
        if (coord.Latitude >= 72.0 && coord.Latitude < 84.0) {
            if (coord.Longitude >= 0.0 && coord.Longitude < 9.0) {
                zoneNumber = 31;
            } else if (coord.Longitude >= 9.0 && coord.Longitude < 21.0) {
                zoneNumber = 33;
            } else if (coord.Longitude >= 21.0 && coord.Longitude < 33.0) {
                zoneNumber = 35;
            } else if (coord.Longitude >= 33.0 && coord.Longitude < 42.0) {
                zoneNumber = 37;
            }
        }
        System.out.println("latlon2UTM - zoneNumber " + zoneNumber);
        double lonOrigin = (zoneNumber - 1) * 6 - 180 + 3;
        System.out.println("latlon2UTM - lonOrigin " + lonOrigin);
        double lonOriginRad = deg2rad(lonOrigin);
        System.out.println("latlon2UTM - lonOriginRad " + lonOriginRad);
        double eccPrimeSquared = ellipEccSquared / (1 - ellipEccSquared);
        System.out.println("latlon2UTM - eccPrimeSquared " + eccPrimeSquared);
        double nVal = ellipRadius / Math.sqrt(1 - ellipEccSquared * Math.sin(latRad) * Math.sin(latRad));
        System.out.println("latlon2UTM - nVal " + nVal);
        double tVal = Math.tan(latRad) * Math.tan(latRad);
        System.out.println("latlon2UTM - tVal " + tVal);
        double cVal = eccPrimeSquared * Math.cos(latRad) * Math.cos(latRad);
        System.out.println("latlon2UTM - cVal " + cVal);
        double aVal = Math.cos(latRad) * (lonRad - lonOriginRad);
        System.out.println("latlon2UTM - aVal " + aVal);
        double mVal = ellipRadius * ((
                1 - ellipEccSquared / 4 - 3 * ellipEccSquared * ellipEccSquared / 64 - 5 *
                        ellipEccSquared * ellipEccSquared * ellipEccSquared / 256) * latRad - (
                3 * ellipEccSquared / 8 + 3 * ellipEccSquared * ellipEccSquared / 32 + 45 * ellipEccSquared *
                        ellipEccSquared * ellipEccSquared / 1024) * Math.sin(2 * latRad) + (
                15 * ellipEccSquared * ellipEccSquared / 256 + 45 * ellipEccSquared * ellipEccSquared *
                        ellipEccSquared / 1024) * Math.sin(
                4 * latRad) - (35 * ellipEccSquared * ellipEccSquared * ellipEccSquared / 3072) * Math.sin(6 * latRad));
        System.out.println("latlon2UTM - mVal " + mVal);
        double utmEasting = (kZero * nVal * (aVal + (1 - tVal + cVal) * aVal * aVal * aVal / 6.0 + (
                5 - 18 * tVal + tVal * tVal + 72 * cVal - 58 * eccPrimeSquared) * aVal * aVal * aVal * aVal *
                aVal / 120.0) + 500000.0);
        System.out.println("latlon2UTM - utmEasting " + utmEasting);
        double utmNorthing = (kZero * (mVal + nVal * Math.tan(latRad) *
                (aVal * aVal / 2 + (5 - tVal + 9 * cVal + 4 * cVal * cVal) *
                        aVal * aVal * aVal * aVal / 24.0 +
                        (61 - 58 * tVal + tVal * tVal + 600 * cVal - 330 * eccPrimeSquared) *
                                aVal * aVal * aVal * aVal * aVal * aVal / 720.0)));
        if (coord.Latitude < 0.0) {
            utmNorthing += 10000000.0;
        }
        System.out.println("latlon2UTM - utmNorthing " + utmNorthing);

        retVal.Northing = (int) Math.round(Math.floor(utmNorthing + 0.5));
        retVal.Easting = (int) Math.round(Math.floor(utmEasting + 0.5));
        retVal.ZoneNumber = (int) Math.round(zoneNumber);
        retVal.ZoneLetter = this.utmLetterDesignator(coord.Latitude);
        return retVal;
    }

    DEGData degstringToData(String coord) {
        DEGData retVal = GeneralData.emptyDEGData;
        String tempStr = coord.replaceAll("\\s+", "");
        String[] coordParts = tempStr.split(",");
        retVal.Latitude = Double.parseDouble(coordParts[0]);
        retVal.Longitude = Double.parseDouble(coordParts[1]);
        return retVal;
    }

    String degdataToString(DEGData coord) {
        return coord.Latitude + "," + coord.Longitude;
    }

    DMSData deg2DMS(DEGData coord) {
        DMSData retVal = new DMSData(0, 0, 0.0, "", 0, 0, 0.0, "");
        retVal.LatHemisphere = "N";
        retVal.LonHemisphere = "E";
        if (coord.Latitude < 0) {
            retVal.LatHemisphere = "S";
        }
        if (coord.Longitude < 0) {
            retVal.LonHemisphere = "W";
        }
        retVal.LatDeg = Math.abs((int) coord.Latitude);
        double latmin = (coord.Latitude - (double) ((int) coord.Latitude)) * 60.0;
        retVal.LatMin = Math.abs((int) latmin);
        double latsec = (latmin - (double) ((int) latmin)) * 60.0;
        retVal.LatSec = Math.round(Math.abs(latsec * 1000.0)) / 1000.0;

        retVal.LonDeg = Math.abs((int) coord.Longitude);
        double lonmin = (coord.Longitude - (double) ((int) coord.Longitude)) * 60.0;
        retVal.LonMin = Math.abs((int) lonmin);
        double lonsec = (lonmin - (double) ((int) lonmin)) * 60.0;
        retVal.LonSec = Math.round(Math.abs(lonsec * 1000.0)) / 1000.0;
        return retVal;
    }

    public MGRSData deg2MGRS(DEGData coord, int accuracy) {
        return utm2MGRS(deg2UTM(coord), accuracy);
    }

    String deg2GMaps(DEGData coord) {
        String retVal = "https://www.google.hu/maps/place/";
        retVal = retVal + coord.Latitude + "%2C" +
                coord.Longitude + "/@" +
                coord.Latitude + "%2C" +
                coord.Longitude + ",17z";
        return retVal;
    }

    String deg2Waze(DEGData coord) {
        String retVal = "https://ul.waze.com/ul?ll=";
        retVal = retVal + coord.Latitude + "%2C" +
                coord.Longitude + "&navigate=yes";
        return retVal;
    }
    //endregion

    //region From MGRS
    String mgrsdataToString(MGRSData coord) {
        String retVal = "";
        String tempEasting = "00000" + coord.Easting;
        String tempNorthing = "00000" + coord.Northing;
        if (coord.Accuracy >= 0 && coord.Accuracy <= 5) {
            retVal = coord.ZoneNumber + coord.ZoneLetter + coord.ID100kCol
                    + coord.ID100kRow + tempEasting.substring(coord.Accuracy)
                    + tempNorthing.substring(coord.Accuracy);
        }
        return retVal;
    }

    MGRSData mgrsstringToData(String coord) {
        MGRSData retVal = GeneralData.emptyMGRSData;
        String tempStr = coord.replaceAll("\\s+", "");
        Pattern pattern = GeneralData.patternMGRS;
        Matcher matcher = pattern.matcher(tempStr);
        int matchCount = 0;
        while (matcher.find()) {
            matchCount++;
            System.out.printf("mgrsstringToData Match count: %s, Group Zero Text: '%s'%n", matchCount,
                    matcher.group());
            for (int i = 1; i <= matcher.groupCount(); i++) {
                System.out.printf("mgrsstringToData Capture Group Number: %s, Captured Text: '%s'%n", i,
                        matcher.group(i));
            }
            int mLength = matcher.group(5).length();
            if (mLength % 2 == 0) {
                retVal.ZoneNumber = Integer.parseInt(matcher.group(1));
                retVal.ZoneLetter = matcher.group(2);
                retVal.ID100kCol = matcher.group(3);
                retVal.ID100kRow = matcher.group(4);
                retVal.Easting = Integer.parseInt(matcher.group(5).substring(0, mLength / 2));
                retVal.Northing = Integer.parseInt(matcher.group(5).substring(mLength / 2));
                retVal.Accuracy = mLength / 2;
            }
        }
        return retVal;

    }

    public UTMData mgrs2UTM(MGRSData coord) {
        UTMData retVal = GeneralData.emptyUTMData;
        retVal.ZoneNumber = coord.ZoneNumber;
        retVal.ZoneLetter = coord.ZoneLetter;
        String hunK = coord.ID100kCol + coord.ID100kRow;
        int mgrsSet = get100kSet4Zone(coord.ZoneNumber);
        System.out.println("mgrs2UTM - mgrsSet " + mgrsSet);
        double east100k = getEastingFromChar(hunK.substring(0, 1), mgrsSet);
        System.out.println("mgrs2UTM - east100k " + east100k);
        double north100k = getNorthingFromChar(hunK.substring(1, 2), mgrsSet);
        while (north100k < getMinNorthing(coord.ZoneLetter)) {
            north100k += 2000000;
        }
        System.out.println("mgrs2UTM - north100k " + north100k);
        double separatedEasting = 0.0;
        double separatedNorthing = 0.0;
        double accuracyBonus = 0.0;
        String separatedEastingStr = "";
        String separatedNorthingStr = "";
        String tempEasting = "00000" + coord.Easting;
        String tempNorthing = "00000" + coord.Northing;
        String coordPart = tempEasting.substring(tempEasting.length() - 5).substring(0, coord.Accuracy) + tempNorthing.substring(tempNorthing.length() - 5).substring(0, coord.Accuracy);
        if (coord.Accuracy > 0) {
            accuracyBonus = 100000.0 / Math.pow(10, coord.Accuracy);
            separatedEastingStr = coordPart.substring(0, coord.Accuracy);
            separatedEasting = Double.parseDouble(separatedEastingStr) * accuracyBonus;
            separatedNorthingStr = coordPart.substring(coord.Accuracy);
            separatedNorthing = Double.parseDouble(separatedNorthingStr) * accuracyBonus;
        }
        System.out.println("mgrs2UTM - accuracyBonus " + accuracyBonus);
        System.out.println("mgrs2UTM - separatedEastingStr " + separatedEastingStr);
        System.out.println("mgrs2UTM - separatedEasting " + separatedEasting);
        System.out.println("mgrs2UTM - separatedNorthingStr " + separatedNorthingStr);
        System.out.println("mgrs2UTM - separatedNorthing " + separatedNorthing);
        double easting = separatedEasting + east100k;
        double northing = separatedNorthing + north100k;
        retVal.Northing = (int) northing;
        retVal.Easting = (int) easting;
        return retVal;
    }

    public DEGData mgrs2DEG(MGRSData coord) {
        return utm2DEG(mgrs2UTM(coord));
    }

    //endregion


    //region From UTM
    String utmdataToString(UTMData coord) {
        return coord.ZoneNumber + "," + coord.ZoneLetter + ","
                + coord.Easting + "," + coord.Northing;
    }

    UTMData utmstringToData(String coord) {
        UTMData retVal = GeneralData.emptyUTMData;
        String tempStr = coord.replaceAll("\\s+", "");
        String[] coordParts = tempStr.split(",");
        retVal.ZoneNumber = Integer.parseInt(coordParts[0]);
        retVal.ZoneLetter = coordParts[1];
        retVal.Easting = Integer.parseInt(coordParts[2]);
        retVal.Northing = Integer.parseInt(coordParts[3]);
        return retVal;
    }

    public DEGData utm2DEG(UTMData coord) {
        DEGData retVal = GeneralData.emptyDEGData;
        int utmNorthing = coord.Northing;
        int utmEasting = coord.Easting;
        String zoneLetter = coord.ZoneLetter;
        int zoneNumber = coord.ZoneNumber;
        if (zoneNumber < 0) {
            return retVal;
        }
        double kZero = 0.9996;
        double ellipRadius = 6378137.0;
        double ellipEccSquared = 0.00669438;
        double e1Val = (1 - Math.sqrt(1 - ellipEccSquared)) / (1 + Math.sqrt(1 - ellipEccSquared));
        double xValue = (double) utmEasting - 500000.0;
        double yValue = utmNorthing;
        if ((int) zoneLetter.toCharArray()[0] < (int) ("N").toCharArray()[0]) {
            yValue -= 10000000.0;
        }
        System.out.println("utm2LatLon - yValue " + yValue);
        int lonOrigin = (zoneNumber - 1) * 6 - 180 + 3;
        System.out.println("utm2LatLon - lonOrigin " + lonOrigin);
        double eccPrimeSquared = ellipEccSquared / (1 - ellipEccSquared);
        System.out.println("utm2LatLon - eccPrimeSquared " + eccPrimeSquared);
        double mVal = yValue / kZero;
        double muVal = mVal / (ellipRadius * (1 - ellipEccSquared / 4 - 3 * ellipEccSquared *
                ellipEccSquared / 64 - 5 * ellipEccSquared * ellipEccSquared * ellipEccSquared / 256));
        double phi1Rad = muVal + (3 * e1Val / 2 - 27 * e1Val * e1Val * e1Val / 32) * Math.sin(2 * muVal) + (
                21 * e1Val * e1Val / 16 - 55 * e1Val * e1Val * e1Val * e1Val / 32) * Math.sin(4 * muVal) + (
                151 * e1Val * e1Val * e1Val / 96) * Math.sin(6 * muVal);
        double vEllipse = 1 - ellipEccSquared * Math.sin(phi1Rad) * Math.sin(phi1Rad);
        double n1Val = ellipRadius / Math.sqrt(vEllipse);
        double t1Val = Math.tan(phi1Rad) * Math.tan(phi1Rad);
        double c1Val = eccPrimeSquared * Math.cos(phi1Rad) * Math.cos(phi1Rad);
        double r1Val = ellipRadius * (1 - ellipEccSquared) / Math.pow(vEllipse, 1.5);
        double dVal = xValue / (n1Val * kZero);
        double lat = phi1Rad - (n1Val * Math.tan(phi1Rad) / r1Val) * (dVal * dVal / 2 -
                (5 + 3 * t1Val + 10 * c1Val - 4 * c1Val * c1Val - 9 * eccPrimeSquared) *
                        dVal * dVal * dVal * dVal / 24 + (61 + 90 * t1Val + 298 * c1Val + 45 *
                t1Val * t1Val - 252 * eccPrimeSquared - 3 * c1Val * c1Val) * dVal * dVal * dVal * dVal * dVal * dVal / 720);
        double latDeg = rad2deg(lat);
        double lon = (dVal - (1 + 2 * t1Val + c1Val) * dVal * dVal * dVal / 6 +
                (5 - 2 * c1Val + 28 * t1Val - 3 * c1Val * c1Val + 8 * eccPrimeSquared + 24 * t1Val * t1Val) *
                        dVal * dVal * dVal * dVal * dVal / 120) / Math.cos(phi1Rad);
        double lonDeg = (double) lonOrigin + rad2deg(lon);
        retVal.Latitude = latDeg;
        retVal.Longitude = lonDeg;
        return retVal;
    }


    public MGRSData utm2MGRS(UTMData coord, int accuracy) {
        MGRSData retVal = GeneralData.emptyMGRSData;
        String tempEasting = "00000" + coord.Easting;
        String tempNorthing = "00000" + coord.Northing;
        retVal.ZoneNumber = coord.ZoneNumber;
        retVal.ZoneLetter = coord.ZoneLetter;
        String hunK = get100kID(coord.Easting, coord.Northing, coord.ZoneNumber);
        retVal.ID100kCol = hunK.substring(0, 1);
        retVal.ID100kRow = hunK.substring(1);
        retVal.Accuracy = accuracy;
        retVal.Easting = Integer.parseInt(tempEasting.substring(tempEasting.length() - 5).substring(0, accuracy));
        retVal.Northing = Integer.parseInt(tempNorthing.substring(tempNorthing.length() - 5).substring(0, accuracy));
        return retVal;
    }

    //endregion

    //region From DMS

    String singledmsdataToString(SingleDMSData coord) {
        return coord.Deg + GeneralData.degChar +
                coord.Min + GeneralData.minChar +
                coord.Sec + GeneralData.secChar +
                coord.Hemisphere;
    }

    SingleDMSData singledmsstringToData(String coord) {
        SingleDMSData retVal = new SingleDMSData(0, 0, 0.0, "");
        String tempStr = coord.replaceAll("\\s+", "");
        System.out.printf("String to recognize: %s\n", tempStr);
        Pattern pattern = GeneralData.patternSingleDMS;
        Matcher matcher = pattern.matcher(tempStr);
        int matchCount = 0;
        while (matcher.find()) {
            matchCount++;
            System.out.printf("Match count: %s, Group Zero Text: '%s'%n", matchCount,
                    matcher.group());
            for (int i = 1; i <= matcher.groupCount(); i++) {
                System.out.printf("Capture Group Number: %s, Captured Text: '%s'%n", i,
                        matcher.group(i));
            }
            double valD1 = Double.parseDouble(matcher.group(1));
            double valD2 = Double.parseDouble(matcher.group(2));
            double valD3 = Double.parseDouble(matcher.group(3));
            retVal.Deg = (int) valD1;
            retVal.Min = (int) valD2;
            retVal.Sec = valD3;
            retVal.Hemisphere = matcher.group(5);
        }
        return retVal;
    }

    String dmsdataToString(DMSData coord) {
        return coord.LatDeg + GeneralData.degChar +
                coord.LatMin + GeneralData.minChar +
                coord.LatSec + GeneralData.secChar +
                coord.LatHemisphere + "," +
                coord.LonDeg + GeneralData.degChar +
                coord.LonMin + GeneralData.minChar +
                coord.LonSec + GeneralData.secChar +
                coord.LonHemisphere;
    }

    DMSData dmsstringToData(String coord) {
        DMSData retVal = GeneralData.emptyDMSData;
        System.out.println("dmsstringToData - coord: " + coord);
        String tempStr = coord.replaceAll("\\s+", "");
        Pattern pattern = GeneralData.patternDMS;
        Matcher matcher = pattern.matcher(tempStr);
        int matchCount = 0;
        while (matcher.find()) {
            matchCount++;
            System.out.printf("dmsPart2DMSCoord Match count: %s, Group Zero Text: '%s'%n", matchCount,
                    matcher.group());
            for (int i = 1; i <= matcher.groupCount(); i++) {
                System.out.printf("dmsPart2DMSCoord Capture Group Number: %s, Captured Text: '%s'%n", i,
                        matcher.group(i));
            }
            retVal.LatDeg = Integer.parseInt(matcher.group(1));
            retVal.LatMin = Integer.parseInt(matcher.group(2));
            retVal.LatSec = Double.parseDouble(matcher.group(3));
            retVal.LatHemisphere = matcher.group(5);
            retVal.LonDeg = Integer.parseInt(matcher.group(6));
            retVal.LonMin = Integer.parseInt(matcher.group(7));
            retVal.LonSec = Double.parseDouble(matcher.group(8));
            retVal.LonHemisphere = matcher.group(10);
        }
        return retVal;
    }

    DEGData dms2DEG(DMSData coord) {
        DEGData retVal = GeneralData.emptyDEGData;
        retVal.Latitude = (double) coord.LatDeg + coord.LatMin / 60.0 + coord.LatSec / 3600.0;
        if (coord.LatHemisphere.equals("S")) {
            retVal.Latitude = -1 * retVal.Latitude;
        }
        retVal.Longitude = (double) coord.LonDeg + coord.LonMin / 60.0 + coord.LonSec / 3600.0;
        if (coord.LonHemisphere.equals("W")) {
            retVal.Longitude = -1 * retVal.Longitude;
        }
        return retVal;
    }

    UTMData dms2UTM(DMSData coord) {
        return deg2UTM(dms2DEG(coord));
    }

    MGRSData dms2MGRS(DMSData coord) {
        return deg2MGRS(dms2DEG(coord), 5);
    }
    //endregion

    //region From SingleDEG
    double singledegstringToData(String coord) {
        double retVal = 0.0;
        String tempStr = coord.replaceAll("\\s+", "");
        System.out.printf("String to recognize: %s\n", tempStr);
        Pattern pattern = GeneralData.patternSingleDEG;
        Matcher matcher = pattern.matcher(tempStr);
        int matchCount = 0;
        while (matcher.find()) {
            matchCount++;
            System.out.printf("Match count: %s, Group Zero Text: '%s'%n", matchCount,
                    matcher.group());
            for (int i = 1; i <= matcher.groupCount(); i++) {
                System.out.printf("Capture Group Number: %s, Captured Text: '%s'%n", i,
                        matcher.group(i));
            }
            double valD1 = Double.parseDouble(matcher.group(1));
            if (valD1 <= 180.0 && valD1 >= -180.0) {
                retVal = valD1;
            }
        }
        return retVal;
    }
    //endregion

    //region From SingleDMS
    //endregion

    //region GEOREF

    String deg2GEOREF(DEGData coord) {
        return georefLon15(coord.Longitude) +
                georefLat15(coord.Latitude) +
                georefLon1515(coord.Longitude) +
                georefLat1515(coord.Latitude) +
                georef151560(coord.Longitude) +
                georef151560(coord.Latitude);
    }

    DEGData georef2DEG(String coord) {
        DEGData retVal = new DEGData(0.0, 0.0);
        String tempStr = coord.replaceAll("\\s+", "");
        System.out.printf("String to recognize: %s\n", tempStr);
        Pattern pattern = GeneralData.patternGEOREF;
        Matcher matcher = pattern.matcher(tempStr);
        int matchCount = 0;
        while (matcher.find()) {
            matchCount++;
            System.out.printf("Match count: %s, Group Zero Text: '%s'%n", matchCount,
                    matcher.group());
            for (int i = 1; i <= matcher.groupCount(); i++) {
                System.out.printf("Capture Group Number: %s, Captured Text: '%s'%n", i,
                        matcher.group(i));
            }
            int mLength = matcher.group(5).length();
            if (mLength % 2 == 0) {
                int lonMulti = 1;
                int latMulti = 1;
                String lon1 = matcher.group(1);
                int lon1Val = georef15IDVal(lon1);
                String lat1 = matcher.group(2);
                int lat1Val = georef15IDVal(lat1);
                String lon2 = matcher.group(3);
                int lon2Val = georef1515IDVal(lon2);
                String lat2 = matcher.group(4);
                int lat2Val = georef1515IDVal(lat2);
                if (lon1Val < 180) {
                    lon1Val = 180 - lon1Val;
                    lonMulti = -1;
                } else {
                    lon1Val -= 180;
                    lonMulti = 1;
                }
                lon1Val += lonMulti * lon2Val;
                if (lat1Val < 90) {
                    lat1Val = 90 - lat1Val;
                    latMulti = -1;
                } else {
                    lat1Val -= 90;
                    latMulti = 1;
                }
                lat1Val += latMulti * lat2Val;
                String nums = matcher.group(5).substring(0, 4);
                String lon3 = nums.substring(0, 2);
                String lat3 = nums.substring(2, 4);
                double lon4 = (double) lon1Val + lonMulti * (Double.parseDouble(lon3) / 60.0);
                double lat4 = (double) lat1Val + latMulti * (Double.parseDouble(lat3) / 60.0);
                lon4 *= lonMulti;
                lat4 *= latMulti;
                retVal.Latitude = lat4;
                retVal.Longitude = lon4;
            }
        }

        return retVal;
    }
    //endregion

    //region GARS
    String deg2GARS(DEGData coord) {
        String retVal = "";
        double lon1 = 180.0 + coord.Longitude;
        String lon2 = ("000" + String.valueOf((int) Math.ceil(lon1 * 2))).substring(3);

        retVal += lon2 + garsLatBand(coord.Latitude) +
                garsQudrant(coord) + String.valueOf(garsNinth(coord));
        return retVal;
    }

    DEGData gars2DEG(String coord) {
        DEGData retVal = new DEGData(0.0, 0.0);
        String tempStr = coord.replaceAll("\\s+", "");
        System.out.printf("String to recognize: %s\n", tempStr);
        Pattern pattern = GeneralData.patternGARS;
        Matcher matcher = pattern.matcher(tempStr);
        int matchCount = 0;
        while (matcher.find()) {
            matchCount++;
            System.out.printf("Match count: %s, Group Zero Text: '%s'%n", matchCount,
                    matcher.group());
            for (int i = 1; i <= matcher.groupCount(); i++) {
                System.out.printf("Capture Group Number: %s, Captured Text: '%s'%n", i,
                        matcher.group(i));
            }
            double lonMatch = Double.parseDouble(matcher.group(1));
            double lon = lonMatch / 2 - 180.0;
            String letters01 = "ABCDEFGHJKLMNPQ";
            String letters02 = "ABCDEFGHJKLMNPQRSTUVWXYZ";
            int where01 = letters01.indexOf(matcher.group(2));
            int where02 = letters02.indexOf(matcher.group(3));
            double lat = (where01 * 24.0 + where02) / 2.0 - 90.0;
            int[][] valArr = {
                    {0, 0},
                    {1, 0},
                    {1, 1},
                    {0, 0},
                    {0, 1}
            };
            int quadIndex = Integer.parseInt(matcher.group(4));
            int[] tempArr = valArr[quadIndex];
            int extLat = tempArr[0];
            int extLon = tempArr[1];
            int[][] valArr2 = {
                    {0, 0},
                    {2, 0},
                    {2, 1},
                    {2, 2},

                    {1, 0},
                    {1, 1},
                    {1, 2},

                    {0, 0},
                    {0, 1},
                    {0, 2}
            };
            int ninthIndex = Integer.parseInt(matcher.group(5));
            int[] tempArr2 = valArr2[ninthIndex];
            int extLat2 = tempArr2[0];
            int extLon2 = tempArr2[1];
            retVal.Latitude = lat + extLat * 0.25 + extLat2 * 0.083333;
            retVal.Longitude = lon + extLon * 0.25 + extLon2 * 0.083333;
        }

        return retVal;
    }
    //endregion

    //region Calculations

    int georef1515IDVal(String coord) {
        int retVal = 0;
        Map<String, Integer> letters = new HashMap<String, Integer>();
        letters.put("A", 0);
        letters.put("B", 1);
        letters.put("C", 2);
        letters.put("D", 3);
        letters.put("E", 4);
        letters.put("F", 5);
        letters.put("G", 6);
        letters.put("H", 7);
        letters.put("J", 8);
        letters.put("K", 9);
        letters.put("L", 10);
        letters.put("M", 11);
        letters.put("N", 12);
        letters.put("P", 13);
        letters.put("Q", 14);
        retVal = letters.get(coord);
        return retVal;
    }

    int georef15IDVal(String coord) {
        int retVal;
        Map<String, Integer> letters = new HashMap<String, Integer>();
        letters.put("A", 0);
        letters.put("B", 15);
        letters.put("C", 30);
        letters.put("D", 45);
        letters.put("E", 60);
        letters.put("F", 75);
        letters.put("G", 90);
        letters.put("H", 105);
        letters.put("J", 120);
        letters.put("K", 135);
        letters.put("L", 150);
        letters.put("M", 165);
        letters.put("N", 180);
        letters.put("P", 195);
        letters.put("Q", 210);
        letters.put("R", 225);
        letters.put("S", 240);
        letters.put("T", 255);
        letters.put("U", 270);
        letters.put("V", 285);
        letters.put("W", 300);
        letters.put("X", 315);
        letters.put("Y", 330);
        letters.put("Z", 345);
        retVal = letters.get(coord);
        return retVal;
    }

    String georefLon15(double longitude) {
        int tempDeg = Math.abs((int) longitude);
        if (longitude >= 0) {
            tempDeg += 180;
        } else {
            tempDeg = 180 - tempDeg;
        }
        return georef15ID(tempDeg);
    }

    String georefLat15(double latitude) {
        int tempDeg = Math.abs((int) latitude);
        if (latitude >= 0) {
            tempDeg += 90;
        } else {
            tempDeg = 90 - tempDeg;
        }
        return georef15ID(tempDeg);
    }

    String georefLon1515(double longitude) {
        int tempDeg = Math.abs((int) longitude);
        int tempVal = tempDeg - ((int) ((double) tempDeg / 15) * 15);
        if (longitude < 0) {
            tempVal = 14 - tempVal;
        }
        String retVal = georef1515ID(tempVal);
        System.out.println("georefLon1515 " + retVal);
        return retVal;
    }

    String georefLat1515(double latitude) {
        int tempDeg = Math.abs((int) latitude);
        int tempVal = tempDeg - ((int) ((double) tempDeg / 15) * 15);
        if (latitude < 0) {
            tempVal = 14 - tempVal;
        }
        return georef1515ID(tempVal);
    }

    String georef151560(double coord) {
        System.out.println("georef151560 " + coord);
        double temp = Math.abs(coord - (int) coord) * 60;
        System.out.println("georef151560 " + temp);
        if (coord < 0) {
            temp = 60.0 - temp;
        }
        System.out.println("georef151560 " + temp);
        String retVal = "00" + String.valueOf((int) temp);
        retVal = retVal.substring(retVal.length() - 2);
        System.out.println("georef151560 RETURN " + retVal);
        return retVal;
//        return ("00" + String.valueOf((int)temp));
    }

    String georef15ID(int deg) {
        String letters01 = "ABCDEFGHJKLMNPQRSTUVWXYZ";
        char char01 = letters01.toCharArray()[deg / 15];
        return String.valueOf(char01);
    }

    String georef1515ID(int index) {
        System.out.println("georef1515ID " + index);
        String letters01 = "ABCDEFGHJKLMNPQ";
        char char01 = letters01.toCharArray()[index];
        String retVal = String.valueOf(char01);
        System.out.println("georef1515ID " + retVal);
        return retVal;
    }


    String garsLatBand(double lat) {
        String retVal;
        double tempLatMin = (90.0 + lat) * 2;
        String letters01 = "ABCDEFGHJKLMNPQ";
        String letters02 = "ABCDEFGHJKLMNPQRSTUVWXYZ";
        int index01 = (int) (tempLatMin / 24.0);
        char char01 = letters01.toCharArray()[index01];
        int index02 = (int) (tempLatMin - (index01 * 24));
        char char02 = letters02.toCharArray()[index02];
        retVal = String.valueOf(char01) + String.valueOf(char02);
        return retVal;
    }

    int garsQudrant(DEGData coord) {
        int retVal;
        double tempLatMin = (90.0 + coord.Latitude) * 2;
        double tempLonMin = (180.0 + coord.Longitude) * 2;
        int quadLat = (int) ((tempLatMin - (int) tempLatMin) * 2);
        int quadLon = (int) ((tempLonMin - (int) tempLonMin) * 2);
        int[][] valArr = {
                {3, 4},
                {1, 2}
        };
        retVal = valArr[quadLat][quadLon];
        return retVal;
    }

    int garsNinth(DEGData coord) {
        int retVal;
        double tempLatMin = (90.0 + coord.Latitude) * 2;
        double tempLonMin = (180.0 + coord.Longitude) * 2;
        int[][] valArr = {
                {7, 8, 9, 7, 8, 9},
                {4, 5, 6, 4, 5, 6},
                {1, 2, 3, 1, 2, 3},
                {7, 8, 9, 7, 8, 9},
                {4, 5, 6, 4, 5, 6},
                {1, 2, 3, 1, 2, 3}
        };
        int quadLat = (int) ((tempLatMin - (int) tempLatMin) * 6);
        int quadLon = (int) ((tempLonMin - (int) tempLonMin) * 6);
        retVal = valArr[quadLat][quadLon];
        return retVal;
    }

    private double rad2deg(double number) {
        return number * 180 / Math.PI;
    }

    private double deg2rad(double number) {
        return number * Math.PI / 180;
    }

    private String utmLetterDesignator(double lat) {
        String retVal = "Z";
        if (72 <= lat && lat <= 84) {
            retVal = "X";
        } else if (64 <= lat && lat < 72) {
            retVal = "W";
        } else if (56 <= lat && lat < 64) {
            retVal = "V";
        } else if (48 <= lat && lat < 56) {
            retVal = "U";
        } else if (40 <= lat && lat < 48) {
            retVal = "T";
        } else if (32 <= lat && lat < 40) {
            retVal = "S";
        } else if (24 <= lat && lat < 32) {
            retVal = "R";
        } else if (16 <= lat && lat < 24) {
            retVal = "Q";
        } else if (8 <= lat && lat < 16) {
            retVal = "P";
        } else if (0 <= lat && lat < 8) {
            retVal = "N";
        } else if (-8 <= lat && lat < 0) {
            retVal = "M";
        } else if (-16 <= lat && lat < -8) {
            retVal = "L";
        } else if (-24 <= lat && lat < -16) {
            retVal = "K";
        } else if (-32 <= lat && lat < -24) {
            retVal = "J";
        } else if (-40 <= lat && lat < -32) {
            retVal = "H";
        } else if (-48 <= lat && lat < -40) {
            retVal = "G";
        } else if (-56 <= lat && lat < -48) {
            retVal = "F";
        } else if (-64 <= lat && lat < -56) {
            retVal = "E";
        } else if (-72 <= lat && lat < -64) {
            retVal = "D";
        } else if (-80 <= lat && lat < -72) {
            retVal = "C";
        }
        return retVal;
    }

    private String get100kID(double easting, double northing, double zone_number) {
        System.out.println("get100kID - easting " + easting);
        System.out.println("get100kID - northing " + northing);
        System.out.println("get100kID - zone_number " + zone_number);
        int setParm = get100kSet4Zone(zone_number);
        System.out.println("get100kID - setParm " + setParm);
        double setColumn = Math.floor(easting / 100000);
        System.out.println("get100kID - setColumn " + setColumn);
        double setRow = Math.floor(northing / 100000) % 20;
        System.out.println("get100kID - setRow " + setRow);
        String retVal = getLetter100kID((int) Math.round(setColumn), (int) Math.round(setRow), setParm);
        System.out.println("get100kID - RETURN " + retVal);
        return retVal;
    }

    private double getEastingFromChar(String mgrsFirstLetter, int mgrsSet) {
        int index = mgrsSet - 1;
        int currentColumn = SET_ORIGIN_COLUMN_LETTERS.toCharArray()[index];
        System.out.println("getEastingFromChar - currentColumn " + currentColumn);
        double eastingValue = 100000.0;
        index = 0;
        while (currentColumn != (int) mgrsFirstLetter.toCharArray()[0]) {
            index += 1;
            currentColumn += 1;
            if (currentColumn == VAL_I) {
                currentColumn += 1;
            }
            if (currentColumn == VAL_O) {
                currentColumn += 1;
            }
            if (currentColumn > VAL_Z) {
                currentColumn = VAL_A;
            }
            eastingValue += 100000.0;
        }
        System.out.println("getEastingFromChar - RETURN " + eastingValue);
        return eastingValue;
    }

    private double getNorthingFromChar(String mgrsSecondLetter, int mgrsSet) {
        System.out.println("getNorthingFromChar - mgrsSecondLetter " + mgrsSecondLetter);
        int index = mgrsSet - 1;
        int currentRow = SET_ORIGIN_ROW_LETTERS.toCharArray()[index];
        System.out.println("getNorthingFromChar - currentRow " + currentRow);
        double northingValue = 0.0;
        while (currentRow != (int) mgrsSecondLetter.toCharArray()[0]) {
            currentRow += 1;
            if (currentRow == VAL_I) {
                currentRow += 1;
            }
            if (currentRow == VAL_O) {
                currentRow += 1;
            }
            if (currentRow > VAL_V) {
                currentRow = VAL_A;
            }
            northingValue += 100000.0;
        }
        System.out.println("getNorthingFromChar - RETURN " + northingValue);
        return northingValue;
    }

    private double getMinNorthing(String zoneLetter) {
        double retVal;
        Map<String, Double> letters = new HashMap<>();
        letters.put("C", 1100000.0);
        letters.put("D", 2000000.0);
        letters.put("E", 2800000.0);
        letters.put("F", 3700000.0);
        letters.put("G", 4600000.0);
        letters.put("H", 5500000.0);
        letters.put("J", 6400000.0);
        letters.put("K", 7300000.0);
        letters.put("L", 8200000.0);
        letters.put("M", 9100000.0);
        letters.put("N", 0.0);
        letters.put("P", 800000.0);
        letters.put("Q", 1700000.0);
        letters.put("R", 2600000.0);
        letters.put("S", 3500000.0);
        letters.put("T", 4400000.0);
        letters.put("U", 5300000.0);
        letters.put("V", 6200000.0);
        letters.put("W", 7000000.0);
        letters.put("X", 7900000.0);
        retVal = letters.get(zoneLetter);

        return retVal;
    }

    private int get100kSet4Zone(double zoneNumber) {
        System.out.println("get100kSet4Zone - zoneNumber " + zoneNumber);
        int NUM_100K_SETS = 6;
        int retVal = (int) Math.round(zoneNumber) % NUM_100K_SETS;
        if (retVal == 0) {
            retVal = NUM_100K_SETS;
        }
        System.out.println("get100kSet4Zone - RETURN " + retVal);
        return retVal;
    }

    private String getLetter100kID(int column, int row, int parm) {
        String retVal;
        int index = parm - 1;
        int colOrigin = SET_ORIGIN_COLUMN_LETTERS.toCharArray()[index];
        int rowOrigin = SET_ORIGIN_ROW_LETTERS.toCharArray()[index];
        int colInt = colOrigin + column - 1;
        int rowInt = rowOrigin + row;
        boolean rollover = false;
        if (colInt > VAL_Z) {
            colInt = colInt - VAL_Z + VAL_A - 1;
            rollover = true;
        }
        if (colInt == VAL_I || (colOrigin < VAL_I && colInt > VAL_I) || ((colInt > VAL_I || colOrigin < VAL_I) && rollover)) {
            colInt += 1;
        }
        if (colInt == VAL_O || (colOrigin < VAL_O && colInt > VAL_O) || ((colInt > VAL_O || colOrigin < VAL_O) && rollover)) {
            colInt += 1;
        }
        if (colInt == VAL_I) {
            colInt += 1;
        }
        if (colInt > VAL_Z) {
            colInt = colInt - VAL_Z + VAL_A - 1;
        }
        if (rowInt > VAL_V) {
            rowInt = rowInt - VAL_V + VAL_A - 1;
            rollover = true;
        } else {
            rollover = false;
        }
        if (((rowInt == VAL_I) || ((rowOrigin < VAL_I) && (rowInt > VAL_I))) || (((rowInt > VAL_I) || (rowOrigin < VAL_I)) && rollover)) {
            rowInt += 1;
        }
        if (((rowInt == VAL_O) || ((rowOrigin < VAL_O) && (rowInt > VAL_O))) || (((rowInt > VAL_O) || (rowOrigin < VAL_O)) && rollover)) {
            rowInt += 1;
        }
        if (rowInt == VAL_I) {
            rowInt += 1;
        }
        if (rowInt > VAL_V) {
            rowInt = rowInt - VAL_V + VAL_A - 1;
        }
        retVal = (char) colInt + Character.toString((char) rowInt);
        return retVal;
    }

    //endregion

}

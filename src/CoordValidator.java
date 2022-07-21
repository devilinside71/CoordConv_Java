/*
 * Copyright (c) 2022. Laszlo TAMAS9
 * National Operations Management Division
 * HM Electronics, Logistics and Property Management Private Co.
 */


import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created on 2022-07-20
 *
 * @author Laszlo TAMAS9
 */
public class CoordValidator {


    boolean validMGRSString(String coord) {
        boolean retVal = false;
        String tempStr = coord.replaceAll("\\s+", "");
        System.out.printf("String to recognize: %s\n", tempStr);
        Pattern pattern = GeneralData.patternMGRS;
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
            if (matcher.group(5).length() % 2 == 0) {
                retVal = true;
            }
        }
        return retVal;
    }

    boolean validUTMString(String coord) {
        boolean retVal = false;
        String tempStr = coord.replaceAll("\\s+", "");
        System.out.printf("String to recognize: %s\n", tempStr);
        Pattern pattern = GeneralData.patternUTM;
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
            retVal = true;
        }
        return retVal;
    }

    boolean validDEGString(String coord) {
        boolean retVal = false;
        String tempStr = coord.replaceAll("\\s+", "");
        System.out.printf("String to recognize: %s\n", tempStr);
        Pattern pattern = GeneralData.patternDEG;
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
            if (valD1 <= 90.0 && valD1 >= -90.0 && valD2 <= 180.0 && valD2 >= -180.0) {
                retVal = true;
            }
        }
        return retVal;
    }

    boolean validSingleDegString(String coord) {
        boolean retVal = false;
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
                retVal = true;
            }
        }
        return retVal;
    }

    boolean validSingleDMSStringLat(String coord) {
        boolean retVal = false;
        String tempStr = coord.replaceAll("\\s+", "");
        System.out.printf("String to recognize: %s\n", tempStr);
        Pattern pattern = GeneralData.patternSingleDMSLatitude;
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
            if (valD1 <= 90.0 && valD1 >= -90.0 && valD2 >= 0.0 && valD2 <= 60.0 && valD3 >= 0.0 && valD3 <= 60.0) {
                retVal = true;
            }
        }
        return retVal;
    }

    boolean validSingleDMSStringLon(String coord) {
        boolean retVal = false;
        String tempStr = coord.replaceAll("\\s+", "");
        System.out.printf("String to recognize: %s\n", tempStr);
        Pattern pattern = GeneralData.patternSingleDMSLongitude;
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
            if (valD1 <= 180.0 && valD1 >= -180.0 && valD2 >= 0.0 && valD2 <= 60.0 && valD3 >= 0.0 && valD3 <= 60.0) {
                retVal = true;
            }
        }
        return retVal;
    }

    boolean validSingleDMSStringNoHemisphere(String coord) {
        boolean retVal = false;
        String tempStr = coord.replaceAll("\\s+", "");
        System.out.printf("String to recognize: %s\n", tempStr);
        Pattern pattern = GeneralData.patternSingleDMSNoHemisphere;
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
            if (valD1 <= 180.0 && valD1 >= -180.0 && valD2 >= 0.0 && valD2 <= 60.0 && valD3 >= 0.0 && valD3 <= 60.0) {
                retVal = true;
            }
        }
        return retVal;
    }

    boolean validDMSString(String coord) {
        boolean retVal = false;
        String tempStr = coord.replaceAll("\\s+", "");
        System.out.printf("String to recognize: %s\n", tempStr);
        Pattern pattern = GeneralData.patternDMS;
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
            double valD6 = Double.parseDouble(matcher.group(6));
            double valD7 = Double.parseDouble(matcher.group(7));
            double valD8 = Double.parseDouble(matcher.group(8));

            if (valD1 <= 90.0 && valD1 >= -90.0 && valD2 >= 0.0 && valD2 <= 60.0 && valD3 >= 0.0
                    && valD3 <= 60.0 && valD6 <= 180.0 && valD6 >= -180.0 && valD7 >= 0.0
                    && valD7 <= 60.0 && valD8 >= 0.0 && valD8 <= 60.0
            ) {
                retVal = true;
            }
        }
        return retVal;
    }

    boolean validGEOREF(String coord) {
        boolean retVal = false;
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
            retVal = true;
        }
        return retVal;
    }

    boolean validGARS(String coord) {
        boolean retVal = false;
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
            retVal = true;
        }
        return retVal;
    }
    CoordType recognizedCoord(String coord) {
        CoordType retVal = CoordType.Unknown;
        if (validMGRSString(coord)) {
            retVal = CoordType.MGRS;
        }
        if (validUTMString(coord)) {
            retVal = CoordType.UTM;
        }
        if (validDEGString(coord)) {
            retVal = CoordType.DEG;
        }
        if (validSingleDegString(coord)) {
            retVal = CoordType.SingleDEG;
        }
        if (validSingleDMSStringLat(coord)) {
            retVal = CoordType.SingleDMSLatitude;
        }
        if (validSingleDMSStringLon(coord)) {
            retVal = CoordType.SingleDMSLongitude;
        }
        if (validSingleDMSStringNoHemisphere(coord)) {
            retVal = CoordType.SingleDMSNoHemisphere;
        }
        if (validDMSString(coord)) {
            retVal = CoordType.DMS;
        }
        if (validGEOREF(coord)) {
            retVal = CoordType.GEOREF;
        }
        if (validGARS(coord)) {
            retVal = CoordType.GARS;
        }

        return retVal;
    }

}



/**
 * Created on ${YEAR}-${MONTH}-${DAY}
 *
 * @author Laszlo TAMAS9
 */
public class Main {
    public static void main(String[] args) {

        CoordConv coordConv = new CoordConv();
        CoordValidator coordValidator = new CoordValidator();
        System.out.println("Hello world!");
        System.out.println("input data: " + args[0]);

        String degStringTest01 = "16.707746,-2.986502";
        DEGData degTest01 = new DEGData(16.707746, -2.986502);
        String mgrsStringTest01 = "30QWD0143947225";
        MGRSData mgrsTest01 = new MGRSData(30, "Q", "W", "D", 1439, 47225, 5);
        String utmStringTest01 = "30,Q,501439,1847225";
        UTMData utmTest01 = new UTMData(30, "Q", 501439, 1847225);
        String dmsStringTest01 = "16°42'27.89\"N,2°59'11.41\"W";
        String dmsStringSingleDMSNoHemisphereTest01 = "16°42'27.89\"";
        String dmsStringSingleDMSLatitudeTest01 = "16°42'27.89\"N";
        String dmsStringSingleDMSLongitudeTest01 = "2°59'11.41\"W";
        DMSData dmsTest01 = new DMSData(16, 42, 27.89, "N", 2, 59, 11.41, "W");
        String singleDegStringTest01 = "16.707746";
        String georefStringTest01 = "MHNB0042";
        String garsStringTest01 = "355JX31";


        String mgrsStr = "";
        String utmStr = "";
        String degStr = "";
        String dmsStr = "";
        String latStr = "";
        String lonStr = "";
        String garsStr = "";
        String georefStr = "";
        String gmapsStr = "";
        String wazeStr = "";


//        System.out.println("UTM: " + coordConv.utmdataToString(coordConv.deg2UTM(coordConv.degstringToData(args[0]))));
//        System.out.println("MGRS: " + coordConv.mgrsdataToString(coordConv.deg2MGRS(coordConv.degstringToData(args[0]),5)));

//        System.out.printf("Recognized coordinate type of %s is %s", mgrsStringTest01, coordValidator.recognizedCoord(mgrsStringTest01));
//        System.out.printf("Recognized coordinate type of %s is %s", utmStringTest01, coordValidator.recognizedCoord(utmStringTest01));
//        System.out.printf("Recognized coordinate type of %s is %s", degStringTest01, coordValidator.recognizedCoord(degStringTest01));
//        System.out.printf("Recognized coordinate type of %s is %s", singleDegStringTest01, coordValidator.recognizedCoord(singleDegStringTest01));
//        System.out.printf("Recognized coordinate type of %s is %s", dmsStringSingleDMSLatitudeTest01, coordValidator.recognizedCoord(dmsStringSingleDMSLatitudeTest01));
//        System.out.printf("Recognized coordinate type of %s is %s", dmsStringSingleDMSLongitudeTest01, coordValidator.recognizedCoord(dmsStringSingleDMSLongitudeTest01));
//        System.out.printf("Recognized coordinate type of %s is %s", dmsStringSingleDMSNoHemisphereTest01, coordValidator.recognizedCoord(dmsStringSingleDMSNoHemisphereTest01));
//        System.out.printf("Recognized coordinate type of %s is %s", dmsStringTest01, coordValidator.recognizedCoord(dmsStringTest01));
//        System.out.printf("Recognized coordinate type of %s is %s", georefStringTest01, coordValidator.recognizedCoord(georefStringTest01));
//        System.out.printf("Recognized coordinate type of %s is %s", garsStringTest01, coordValidator.recognizedCoord(garsStringTest01));


        CoordType recognized = coordValidator.recognizedCoord(args[0]);
        DEGData buffDEG = new DEGData(0.0, 0.0);
        switch (recognized) {
            case MGRS:
                MGRSData coordMGRS = coordConv.mgrsstringToData(args[0]);
                mgrsStr = coordConv.mgrsdataToString(coordMGRS);
                utmStr = coordConv.utmdataToString(coordConv.mgrs2UTM(coordMGRS));
                buffDEG = coordConv.mgrs2DEG(coordMGRS);
                degStr = coordConv.degdataToString(buffDEG);
                dmsStr = coordConv.dmsdataToString(coordConv.deg2DMS(buffDEG));
                latStr = String.valueOf(buffDEG.Latitude);
                lonStr = String.valueOf(buffDEG.Longitude);
                garsStr = coordConv.deg2GARS(buffDEG);
                georefStr = coordConv.deg2GEOREF(buffDEG);
                gmapsStr = coordConv.deg2GMaps(buffDEG);
                wazeStr = coordConv.deg2Waze(buffDEG);
                break;
            case DEG:
                buffDEG = coordConv.degstringToData(args[0]);
                mgrsStr = coordConv.mgrsdataToString(coordConv.deg2MGRS(buffDEG, 5));
                utmStr = coordConv.utmdataToString(coordConv.deg2UTM(buffDEG));
                degStr = coordConv.degdataToString(buffDEG);
                dmsStr = coordConv.dmsdataToString(coordConv.deg2DMS(buffDEG));
                latStr = String.valueOf(buffDEG.Latitude);
                lonStr = String.valueOf(buffDEG.Longitude);
                garsStr = coordConv.deg2GARS(buffDEG);
                georefStr = coordConv.deg2GEOREF(buffDEG);
                gmapsStr = coordConv.deg2GMaps(buffDEG);
                wazeStr = coordConv.deg2Waze(buffDEG);
                break;
            case UTM:
                UTMData coordUTM = coordConv.utmstringToData(args[0]);
                mgrsStr = coordConv.mgrsdataToString(coordConv.utm2MGRS(coordUTM, 5));
                utmStr = coordConv.utmdataToString(coordUTM);
                buffDEG = coordConv.utm2DEG(coordUTM);
                degStr = coordConv.degdataToString(buffDEG);
                dmsStr = coordConv.dmsdataToString(coordConv.deg2DMS(buffDEG));
                latStr = String.valueOf(buffDEG.Latitude);
                lonStr = String.valueOf(buffDEG.Longitude);
                garsStr = coordConv.deg2GARS(buffDEG);
                georefStr = coordConv.deg2GEOREF(buffDEG);
                gmapsStr = coordConv.deg2GMaps(buffDEG);
                wazeStr = coordConv.deg2Waze(buffDEG);
                break;
            case DMS:
                DMSData coordDMS = coordConv.dmsstringToData(args[0]);
                buffDEG = coordConv.dms2DEG(coordDMS);
                mgrsStr = coordConv.mgrsdataToString(coordConv.deg2MGRS(buffDEG, 5));
                utmStr = coordConv.utmdataToString(coordConv.deg2UTM(buffDEG));
                degStr = coordConv.degdataToString(buffDEG);
                dmsStr = coordConv.dmsdataToString(coordDMS);
                latStr = String.valueOf(buffDEG.Latitude);
                lonStr = String.valueOf(buffDEG.Longitude);
                garsStr = coordConv.deg2GARS(buffDEG);
                georefStr = coordConv.deg2GEOREF(buffDEG);
                gmapsStr = coordConv.deg2GMaps(buffDEG);
                wazeStr = coordConv.deg2Waze(buffDEG);
                break;
            case SingleDEG:
                double buffDEGsingle = coordConv.singledegstringToData(args[0]);
                mgrsStr = GeneralData.naStr;
                utmStr = GeneralData.naStr;
                degStr = String.valueOf(buffDEGsingle);
                dmsStr = GeneralData.naStr;
                latStr = GeneralData.naStr;
                lonStr = GeneralData.naStr;
                garsStr = GeneralData.naStr;
                georefStr = GeneralData.naStr;
                gmapsStr = GeneralData.naStr;
                wazeStr = GeneralData.naStr;
                break;
            case SingleDMSLatitude:
                SingleDMSData coordSDMS = coordConv.singledmsstringToData(args[0]);
                DMSData tempDMS = new DMSData(coordSDMS.Deg, coordSDMS.Min, coordSDMS.Sec, coordSDMS.Hemisphere, 0, 0, 0.0, "E");
                buffDEG = coordConv.dms2DEG(tempDMS);
                mgrsStr = GeneralData.naStr;
                utmStr = GeneralData.naStr;
                degStr = GeneralData.naStr;
                dmsStr = GeneralData.naStr;
                latStr = String.valueOf(buffDEG.Latitude);
                lonStr = GeneralData.naStr;
                garsStr = GeneralData.naStr;
                georefStr = GeneralData.naStr;
                gmapsStr = GeneralData.naStr;
                wazeStr = GeneralData.naStr;
                break;
            case SingleDMSLongitude:
                SingleDMSData coordSDMS2 = coordConv.singledmsstringToData(args[0]);
                DMSData tempDMS2 = new DMSData(0, 0, 0.0, "N", coordSDMS2.Deg, coordSDMS2.Min, coordSDMS2.Sec, coordSDMS2.Hemisphere);
                buffDEG = coordConv.dms2DEG(tempDMS2);
                mgrsStr = GeneralData.naStr;
                utmStr = GeneralData.naStr;
                degStr = GeneralData.naStr;
                dmsStr = GeneralData.naStr;
                latStr = GeneralData.naStr;
                lonStr = String.valueOf(buffDEG.Longitude);
                garsStr = GeneralData.naStr;
                georefStr = GeneralData.naStr;
                gmapsStr = GeneralData.naStr;
                wazeStr = GeneralData.naStr;
                break;
            case SingleDMSNoHemisphere:
                SingleDMSData coordSDMS3 = coordConv.singledmsstringToData(args[0]);
                DMSData tempDMS3 = new DMSData(coordSDMS3.Deg, coordSDMS3.Min, coordSDMS3.Sec, "N", 0, 0, 0.0, "E");
                buffDEG = coordConv.dms2DEG(tempDMS3);
                mgrsStr = GeneralData.naStr;
                utmStr = GeneralData.naStr;
                degStr = String.valueOf(buffDEG.Latitude);
                dmsStr = GeneralData.naStr;
                latStr = GeneralData.naStr;
                lonStr = GeneralData.naStr;
                garsStr = GeneralData.naStr;
                georefStr = GeneralData.naStr;
                gmapsStr = GeneralData.naStr;
                wazeStr = GeneralData.naStr;
                break;
            case GEOREF:
                buffDEG = coordConv.georef2DEG(args[0]);
                mgrsStr = coordConv.mgrsdataToString(coordConv.deg2MGRS(buffDEG, 5));
                utmStr = coordConv.utmdataToString(coordConv.deg2UTM(buffDEG));
                degStr = coordConv.degdataToString(buffDEG);
                dmsStr = coordConv.dmsdataToString(coordConv.deg2DMS(buffDEG));
                latStr = String.valueOf(buffDEG.Latitude);
                lonStr = String.valueOf(buffDEG.Longitude);
                garsStr = coordConv.deg2GARS(buffDEG);
                georefStr = coordConv.deg2GEOREF(buffDEG);
                gmapsStr = coordConv.deg2GMaps(buffDEG);
                wazeStr = coordConv.deg2Waze(buffDEG);
                break;
            case GARS:
                buffDEG=coordConv.gars2DEG(args[0]);
                mgrsStr = coordConv.mgrsdataToString(coordConv.deg2MGRS(buffDEG, 5));
                utmStr = coordConv.utmdataToString(coordConv.deg2UTM(buffDEG));
                degStr = coordConv.degdataToString(buffDEG);
                dmsStr = coordConv.dmsdataToString(coordConv.deg2DMS(buffDEG));
                latStr = String.valueOf(buffDEG.Latitude);
                lonStr = String.valueOf(buffDEG.Longitude);
                garsStr = coordConv.deg2GARS(buffDEG);
                georefStr = coordConv.deg2GEOREF(buffDEG);
                gmapsStr = coordConv.deg2GMaps(buffDEG);
                wazeStr = coordConv.deg2Waze(buffDEG);
                break;
            default:
                mgrsStr = GeneralData.naStr;
                utmStr = GeneralData.naStr;
                degStr = GeneralData.naStr;
                dmsStr = GeneralData.naStr;
                latStr = GeneralData.naStr;
                lonStr = GeneralData.naStr;
                garsStr = GeneralData.naStr;
                georefStr = GeneralData.naStr;
                gmapsStr = GeneralData.naStr;
                wazeStr = GeneralData.naStr;
                break;
        }

        System.out.println("\n-------------------------");
        System.out.printf("Recognized coordinate type of %s is %s", args[0], recognized);
        System.out.println("\nMGRS: " + mgrsStr);
        System.out.println("DEG: " + degStr);
        System.out.println("UTM: " + utmStr);
        System.out.println("DMS: " + dmsStr);
        System.out.println("LAT: " + latStr);
        System.out.println("LON: " + lonStr);
        System.out.println("GARS: " + garsStr);
        System.out.println("GEOREF: " + georefStr);
        System.out.println("GMAPS: " + gmapsStr);
        System.out.println("WAZE: " + wazeStr);

    }
}
/*
 * Copyright (c) 2022. Laszlo TAMAS9
 * National Operations Management Division
 * HM Electronics, Logistics and Property Management Private Co.
 */

/**
 * Created on 2022-07-21
 *
 * @author Laszlo TAMAS9
 */
public class SingleDMSData {
    int Deg;
    int Min;
    double Sec;
    String Hemisphere;

    public SingleDMSData(int deg, int min, double sec, String hemisphere) {
        Deg = deg;
        Min = min;
        Sec = sec;
        Hemisphere = hemisphere;
    }
}

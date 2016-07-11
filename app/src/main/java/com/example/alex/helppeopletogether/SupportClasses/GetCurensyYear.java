package com.example.alex.helppeopletogether.SupportClasses;

/**
 * Created by PM on 08.07.2016.
 */
public class GetCurensyYear {


    public static int getCurrentYear() {
        java.util.Calendar calendar = java.util.Calendar.getInstance(java.util.TimeZone.getDefault(), java.util.Locale.getDefault());
        calendar.setTime(new java.util.Date());
        return calendar.get(java.util.Calendar.YEAR);
    }
}

package com.guzung.travelplanner.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtils {
    public static Calendar getDate(String date, String format) throws ParseException {
        Date time = new SimpleDateFormat(format).parse(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        calendar.add(Calendar.DATE, 1);
        return calendar;
    }

    public static Timestamp toTimestamp(String date) {
        try {
            return new Timestamp(getDate(date, "yyyy-MM-dd HH:mm:ss").getTimeInMillis());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static Timestamp dateWithTime(Date date, Integer hourOfDay) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        return new Timestamp(calendar.getTimeInMillis());
    }
}

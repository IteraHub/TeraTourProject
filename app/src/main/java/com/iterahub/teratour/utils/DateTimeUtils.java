package com.iterahub.teratour.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateTimeUtils {

    public static String formatTime(Date date){
        SimpleDateFormat localDateFormat = new SimpleDateFormat("h:mm a", Locale.ROOT);
        return localDateFormat.format(date);
    }

    public static String formatDateTime(Date date){
        SimpleDateFormat localDateFormat = new SimpleDateFormat("dd-MM-yyyy h:mm a", Locale.ROOT);
        return localDateFormat.format(date);
    }

    public static String formatDateTimeWithNow(Date date){
        Calendar newCal= Calendar.getInstance();
        Calendar oldCal = Calendar.getInstance();
        SimpleDateFormat localDateFormat;
        oldCal.setTime(date);
        newCal.setTime(new Date());
        if(oldCal.get(Calendar.YEAR) == newCal.get(Calendar.YEAR)){
            if(oldCal.get(Calendar.MONTH) == newCal.get(Calendar.MONTH)){
                localDateFormat = new SimpleDateFormat("h:mm", Locale.ROOT);
                return localDateFormat.format(date);
            }else{
                localDateFormat = new SimpleDateFormat("dd-MM h:mm", Locale.ROOT);
                return localDateFormat.format(date);
            }
        }else{
            localDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ROOT);
            return localDateFormat.format(date);
        }
    }

}

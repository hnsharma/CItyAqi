package com.cityaqi.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by user4 on 31/5/16.
 */
public class DateFormats {

    public static final String DD_MM_YYYY = "dd-MM-yyyy";
    public static final String HH_MM_DD_MM_YYYY = "hh:mm a dd-MM-yyyy";
    public static final String HH_MM = "hh:mm a";
    public static final String MM_YYYY      = "MMM-yyyy";
    public static final String DD_MMM_YYYY = "dd MMM yyyy";
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYY_MM_DDTHH_MM_SSZ = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    public static Date getDate(String format, String dateString)
    {
        if(dateString == null || dateString.trim().length() == 0)
        {
            return new Date();
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format,Locale.ENGLISH);
        Date    date = null;
        try {
             date = simpleDateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(date==null){
            date = new Date();
        }
        return date;
    }

    public static String getDate(String format, String dateString,String returnformat)
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date    date = null;
        try {
            date = simpleDateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(date==null){
            date = new Date();
        }
        return getDateFormat(returnformat,date);
    }

    public static String getDateFormat(String format, Date date)
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format,Locale.ENGLISH);
        String    dateString = null;
            dateString = simpleDateFormat.format(date);
        return dateString;
    }
    public static String getDateFormat(String toTormat, String date,String fromFormat)
    {
        SimpleDateFormat toSimpleDateFormat = new SimpleDateFormat(toTormat);
        SimpleDateFormat fromSimpleDateFormat = new SimpleDateFormat(fromFormat);
        Date    date1;
        String    dateString = null;
        try {
            date1   =   fromSimpleDateFormat.parse(date);
            dateString = toSimpleDateFormat.format(date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return dateString;
    }
    public static String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                YYYY_MM_DD_HH_MM_SS,Locale.ENGLISH);
        Date date = new Date();
        return dateFormat.format(date);
    }

    private static Pattern pattern = Pattern.compile("(\\d{2}):(\\d{2}):(\\d{2})", Pattern.CASE_INSENSITIVE);

    public static long dateParseRegExp(String period) {
        Matcher matcher = pattern.matcher(period);
        if (matcher.matches()) {
            return Long.parseLong(matcher.group(1)) * 3600000L
                    + Long.parseLong(matcher.group(2)) * 60000
                    + Long.parseLong(matcher.group(3)) * 1000;
        } else {
            return  1000;
        }
    }

    public static int getDiffYears(Date first, Date last) {
        Calendar a = getCalendar(first);
        Calendar b = getCalendar(last);
        int diff = b.get(Calendar.YEAR) - a.get(Calendar.YEAR);
        if (a.get(Calendar.MONTH) > b.get(Calendar.MONTH) ||
                (a.get(Calendar.MONTH) == b.get(Calendar.MONTH) && a.get(Calendar.DATE) > b.get(Calendar.DATE))) {
            diff--;
        }
        return diff;
    }

    public static int getDiffYears(Calendar first, Calendar last) {
        if(first == null || last == null)
        {
            return 0;
        }
        Calendar a = first;
        Calendar b = last;
        int diff = b.get(Calendar.YEAR) - a.get(Calendar.YEAR);
        if (a.get(Calendar.MONTH) > b.get(Calendar.MONTH) ||
                (a.get(Calendar.MONTH) == b.get(Calendar.MONTH) && a.get(Calendar.DATE) > b.get(Calendar.DATE))) {
            diff--;
        }
        return diff;
    }
    public static Calendar getCalendar(Date date) {
        Calendar cal = Calendar.getInstance(Locale.US);
        cal.setTime(date);
        return cal;
    }
}

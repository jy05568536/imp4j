package com.fermii.imp4j.common.utility;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class DateUtility {

    public static String date2String(Date date, String format) {
        if (date == null) {
            return null;
        }
        return new SimpleDateFormat(format).format(date);
    }

    public static Date string2Date(String sDate, String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        Date d = null;
        dateFormat.setLenient(false);
        if ((sDate != null) && (sDate.length() == format.length())) {
            try {
                d = dateFormat.parse(sDate);
            } catch (ParseException ex) {
                return null;
            }
        }
        return d;
    }
}
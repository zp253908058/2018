package com.teeny.wms.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see DateTimeUtils
 * @since 2017/8/21
 */

public class DateTimeUtils {

   public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
   public static final String TIME_FORMAT = "HH:mm:ss";
   public static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final SimpleDateFormat sDateFormat = new SimpleDateFormat(DATE_TIME_FORMAT, Locale.getDefault());

    public static Date stringToDate(String date) {
        return new Date();
    }

    public static String dateToString(Date date) {
        return dateToString(date, DATE_FORMAT);
    }

    public static String dateToString(Date date, String pattern) {
        sDateFormat.applyPattern(DATE_FORMAT);
        return sDateFormat.format(date);
    }
}

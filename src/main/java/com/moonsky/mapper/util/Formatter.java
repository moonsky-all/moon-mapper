package com.moonsky.mapper.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author benshaoye
 */
public enum Formatter {
    ;

    public static final String DATE = "yyyy-MM-dd";
    public static final String TIME = "HH:mm:ss";
    public static final String DATETIME = "yyyy-MM-dd HH:mm:ss";

    public static String format(Date value, String pattern) {
        return new SimpleDateFormat(pattern).format(value);
    }

    public static String format(Calendar value, String pattern) {
        return new SimpleDateFormat(pattern).format(value.getTime());
    }

    public static String format(long value, String pattern) {
        return new DecimalFormat(pattern).format(value);
    }

    public static String format(double value, String pattern) {
        return new DecimalFormat(pattern).format(value);
    }

    public static String format(Number value, String pattern) {
        return new DecimalFormat(pattern).format(value);
    }

    public static Number parseNumber(String value, String pattern) {
        try {
            return new DecimalFormat(pattern).parse(value);
        } catch (ParseException e) {
            throw new IllegalStateException(e);
        }
    }

    public static BigDecimal parseBigDecimal(String value, String pattern) {
        DecimalFormat format = new DecimalFormat(pattern);
        format.setParseBigDecimal(true);
        try {
            return (BigDecimal) format.parse(value);
        } catch (ParseException e) {
            throw new IllegalStateException(e);
        }
    }

    public static Calendar parseCalendar(String value, String pattern) {
        return DateConvert.toUtilCalendar(parseUtilDate(value, pattern));
    }

    public static Date parseUtilDate(String value, String pattern) {
        try {
            return new SimpleDateFormat(pattern).parse(value);
        } catch (ParseException e) {
            throw new IllegalStateException(e);
        }
    }
}

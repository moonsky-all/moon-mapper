package com.moonsky.mapper.util;

import org.joda.time.DateTime;
import org.joda.time.MutableDateTime;

import java.sql.Timestamp;
import java.time.*;
import java.util.Calendar;
import java.util.Date;

import static java.time.ZoneId.systemDefault;

/**
 * @author benshaoye
 */
public abstract class Joda2xConvert extends Joda1xConvert {

    Joda2xConvert() {super();}

    public static LocalDate toJdk8LocalDate(org.joda.time.YearMonth value) {
        return LocalDate.of(value.getYear(), value.getMonthOfYear(), 1);
    }

    public static LocalDateTime toJdk8LocalDateTime(org.joda.time.YearMonth value) {return toJdk8LocalDate(value).atStartOfDay();}

    public static OffsetDateTime toJdk8OffsetDateTime(org.joda.time.YearMonth value) {
        return toJdk8OffsetDateTime(toJdk8LocalDate(value));
    }

    public static ZonedDateTime toJdk8ZonedDateTime(org.joda.time.YearMonth value) {
        return toJdk8ZonedDateTime(toJdk8LocalDate(value));
    }

    public static Instant toJdk8Instant(org.joda.time.YearMonth value) {return toJdk8Instant(toJdk8LocalDate(value));}

    public static Date toUtilDate(org.joda.time.YearMonth value) {return toUtilDate(toJdk8LocalDate(value));}

    public static Calendar toUtilCalendar(org.joda.time.YearMonth value) {return toUtilCalendar(toJdk8LocalDate(value));}

    public static java.sql.Date toSqlDate(org.joda.time.YearMonth value) {
        return java.sql.Date.valueOf(toJdk8LocalDate(value));
    }

    public static Timestamp toSqlTimestamp(org.joda.time.YearMonth value) {return toSqlTimestamp(toJdk8LocalDate(value));}

    public static DateTime toJodaDateTime(org.joda.time.YearMonth value) {return toJodaDateTime(toJdk8LocalDate(value));}

    public static org.joda.time.LocalDate toJodaLocalDate(org.joda.time.YearMonth value) {
        return new org.joda.time.LocalDate(value.getYear(), value.getMonthOfYear(), 1);
    }

    public static org.joda.time.LocalDateTime toJodaLocalDateTime(org.joda.time.YearMonth value) {
        return toJodaLocalDateTime(toJdk8LocalDate(value));
    }

    public static MutableDateTime toJodaMutableDateTime(org.joda.time.YearMonth value) {
        return toJodaMutableDateTime(toJdk8LocalDate(value));
    }

    public static org.joda.time.Instant toJodaInstant(org.joda.time.YearMonth value) {
        return toJodaInstant(toJdk8LocalDate(value));
    }

    public static YearMonth toJdk8YearMonth(org.joda.time.YearMonth value) {
        return YearMonth.of(value.getYear(), value.getMonthOfYear());
    }

    public static Year toJdk8Year(org.joda.time.YearMonth value) {
        return Year.of(value.getYear());
    }

    public static Month toJdk8Month(org.joda.time.YearMonth value) {
        return Month.of(value.getMonthOfYear());
    }

    public static Month toJdk8Month(org.joda.time.MonthDay value) {
        return Month.of(value.getMonthOfYear());
    }

    public static org.joda.time.YearMonth toJodaYearMonth(YearMonth value) {
        return new org.joda.time.YearMonth(value.getYear(), value.getMonthValue());
    }

    public static org.joda.time.YearMonth toJodaYearMonth(LocalDate value) {
        return new org.joda.time.YearMonth(value.getYear(), value.getMonthValue());
    }

    public static org.joda.time.YearMonth toJodaYearMonth(LocalDateTime value) {
        return new org.joda.time.YearMonth(value.getYear(), value.getMonthValue());
    }

    public static org.joda.time.YearMonth toJodaYearMonth(OffsetDateTime value) {
        return new org.joda.time.YearMonth(value.getYear(), value.getMonthValue());
    }

    public static org.joda.time.YearMonth toJodaYearMonth(ZonedDateTime value) {
        return new org.joda.time.YearMonth(value.getYear(), value.getMonthValue());
    }

    public static org.joda.time.YearMonth toJodaYearMonth(Instant value) {
        return toJodaYearMonth(value.atZone(systemDefault()));
    }

    public static org.joda.time.YearMonth toJodaYearMonth(Date value) {
        return org.joda.time.YearMonth.fromDateFields(value);
    }

    public static org.joda.time.YearMonth toJodaYearMonth(Calendar value) {
        return org.joda.time.YearMonth.fromCalendarFields(value);
    }

    public static org.joda.time.YearMonth toJodaYearMonth(java.sql.Date value) {
        return toJodaYearMonth(value.toLocalDate());
    }

    public static org.joda.time.YearMonth toJodaYearMonth(Timestamp value) {
        return toJodaYearMonth(value.toLocalDateTime());
    }

    public static org.joda.time.YearMonth toJodaYearMonth(DateTime value) {
        return new org.joda.time.YearMonth(value.getYear(), value.getMonthOfYear());
    }

    public static org.joda.time.YearMonth toJodaYearMonth(org.joda.time.LocalDate value) {
        return new org.joda.time.YearMonth(value.getYear(), value.getMonthOfYear());
    }

    public static org.joda.time.YearMonth toJodaYearMonth(org.joda.time.LocalDateTime value) {
        return new org.joda.time.YearMonth(value.getYear(), value.getMonthOfYear());
    }

    public static org.joda.time.YearMonth toJodaYearMonth(MutableDateTime value) {
        return new org.joda.time.YearMonth(value.getYear(), value.getMonthOfYear());
    }

    public static org.joda.time.YearMonth toJodaYearMonth(org.joda.time.Instant value) {
        return toJodaYearMonth(toJdk8LocalDate(value));
    }

    public static org.joda.time.YearMonth toJodaYearMonth(Long value) {
        return toJodaYearMonth(new Date(value));
    }

    public static org.joda.time.YearMonth toJodaYearMonth(long value) {
        return toJodaYearMonth(new Date(value));
    }

    public static long toPrimitiveLong(org.joda.time.YearMonth value) {return toPrimitiveLong(toJdk8LocalDate(value));}

    public static Long toLong(org.joda.time.YearMonth value) {return toLong(toJdk8LocalDate(value));}

    public static org.joda.time.MonthDay toJodaMonthDay(LocalDateTime value) {
        return new org.joda.time.MonthDay(value.getMonthValue(), value.getDayOfMonth());
    }

    public static org.joda.time.MonthDay toJodaMonthDay(OffsetDateTime value) {
        return new org.joda.time.MonthDay(value.getMonthValue(), value.getDayOfMonth());
    }

    public static org.joda.time.MonthDay toJodaMonthDay(ZonedDateTime value) {
        return new org.joda.time.MonthDay(value.getMonthValue(), value.getDayOfMonth());
    }

    public static org.joda.time.MonthDay toJodaMonthDay(LocalDate value) {
        return new org.joda.time.MonthDay(value.getMonthValue(), value.getDayOfMonth());
    }

    public static org.joda.time.MonthDay toJodaMonthDay(Instant value) {
        return new org.joda.time.MonthDay(value.toEpochMilli());
    }

    public static org.joda.time.MonthDay toJodaMonthDay(Date value) {
        return org.joda.time.MonthDay.fromDateFields(value);
    }

    public static org.joda.time.MonthDay toJodaMonthDay(java.sql.Date value) {
        return org.joda.time.MonthDay.fromDateFields(value);
    }

    public static org.joda.time.MonthDay toJodaMonthDay(Timestamp value) {
        return org.joda.time.MonthDay.fromDateFields(value);
    }

    public static org.joda.time.MonthDay toJodaMonthDay(Calendar value) {
        return org.joda.time.MonthDay.fromCalendarFields(value);
    }

    public static org.joda.time.MonthDay toJodaMonthDay(Long value) {
        return new org.joda.time.MonthDay(value);
    }

    public static org.joda.time.MonthDay toJodaMonthDay(long value) {
        return new org.joda.time.MonthDay(value);
    }

    public static org.joda.time.MonthDay toJodaMonthDay(DateTime value) {
        return new org.joda.time.MonthDay(value.getMonthOfYear(), value.getDayOfMonth());
    }

    public static org.joda.time.MonthDay toJodaMonthDay(MutableDateTime value) {
        return new org.joda.time.MonthDay(value.getMonthOfYear(), value.getDayOfMonth());
    }

    public static org.joda.time.MonthDay toJodaMonthDay(org.joda.time.LocalDate value) {
        return new org.joda.time.MonthDay(value.getMonthOfYear(), value.getDayOfMonth());
    }

    public static org.joda.time.MonthDay toJodaMonthDay(org.joda.time.LocalDateTime value) {
        return new org.joda.time.MonthDay(value.getMonthOfYear(), value.getDayOfMonth());
    }

    public static org.joda.time.MonthDay toJodaMonthDay(org.joda.time.Instant value) {
        return new org.joda.time.MonthDay(value.getMillis());
    }

    public static org.joda.time.MonthDay toJodaMonthDay(MonthDay value) {
        return new org.joda.time.MonthDay(value.getMonthValue(), value.getDayOfMonth());
    }

    public static MonthDay toJdk8MonthDay(org.joda.time.MonthDay value) {
        return MonthDay.of(value.getMonthOfYear(), value.getDayOfMonth());
    }
}

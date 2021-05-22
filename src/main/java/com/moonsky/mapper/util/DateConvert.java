package com.moonsky.mapper.util;

// import org.joda.time.DateTime;
// import org.joda.time.MutableDateTime;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.*;
import java.util.Calendar;
import java.util.Date;

import static java.time.Instant.ofEpochMilli;
import static java.time.ZoneId.systemDefault;

/**
 * @author benshaoye
 */
public abstract class DateConvert {

    DateConvert() { throw new IllegalStateException(); }

    public static LocalDate toJdk8LocalDate(YearMonth value) {
        return LocalDate.of(value.getYear(), value.getMonthValue(), 1);
    }

    // public static LocalDate toJdk8LocalDate(org.joda.time.YearMonth value) {
    //     return LocalDate.of(value.getYear(), value.getMonthOfYear(), 1);
    // }

    public static LocalDate toJdk8LocalDate(LocalDateTime value) { return value.toLocalDate(); }

    public static LocalDate toJdk8LocalDate(OffsetDateTime value) { return value.toLocalDate(); }

    public static LocalDate toJdk8LocalDate(ZonedDateTime value) { return value.toLocalDate(); }

    public static LocalDate toJdk8LocalDate(Instant value) { return value.atZone(systemDefault()).toLocalDate(); }

    public static LocalDate toJdk8LocalDate(Date value) { return toJdk8LocalDate(value.getTime()); }

    public static LocalDate toJdk8LocalDate(Calendar value) { return toJdk8LocalDate(value.getTimeInMillis()); }

    public static LocalDate toJdk8LocalDate(java.sql.Date value) { return value.toLocalDate(); }

    public static LocalDate toJdk8LocalDate(Timestamp value) { return toJdk8LocalDate(value.getTime()); }

    // public static LocalDate toJdk8LocalDate(DateTime value) { return toJdk8LocalDate(value.getMillis()); }

    // public static LocalDate toJdk8LocalDate(org.joda.time.LocalDate value) {
    //     return LocalDate.of(value.getYear(), value.getMonthOfYear(), value.getDayOfMonth());
    // }

    // public static LocalDate toJdk8LocalDate(org.joda.time.LocalDateTime value) {
    //     return LocalDate.of(value.getYear(), value.getMonthOfYear(), value.getDayOfMonth());
    // }

    // public static LocalDate toJdk8LocalDate(MutableDateTime value) { return toJdk8LocalDate(value.getMillis()); }

    // public static LocalDate toJdk8LocalDate(org.joda.time.Instant value) {
    //     return toJdk8LocalDateTime(value.getMillis()).toLocalDate();
    // }

    public static LocalDate toJdk8LocalDate(Long value) { return toJdk8LocalDateTime(value).toLocalDate(); }

    public static LocalDate toJdk8LocalDate(long value) { return toJdk8LocalDateTime(value).toLocalDate(); }

    public static LocalTime toJdk8LocalTime(LocalDateTime value) { return value.toLocalTime(); }

    public static LocalTime toJdk8LocalTime(OffsetDateTime value) { return value.toLocalTime(); }

    public static LocalTime toJdk8LocalTime(ZonedDateTime value) { return value.toLocalTime(); }

    public static LocalTime toJdk8LocalTime(Instant value) { return toJdk8LocalDateTime(value).toLocalTime(); }

    public static LocalTime toJdk8LocalTime(Date value) { return toJdk8LocalDateTime(value).toLocalTime(); }

    public static LocalTime toJdk8LocalTime(Calendar value) { return toJdk8LocalTime(value.getTimeInMillis()); }

    public static LocalTime toJdk8LocalTime(Time value) { return value.toLocalTime(); }

    public static LocalTime toJdk8LocalTime(Timestamp value) { return value.toLocalDateTime().toLocalTime(); }

    // public static LocalTime toJdk8LocalTime(DateTime value) { return toJdk8LocalTime(value.getMillis()); }

    // public static LocalTime toJdk8LocalTime(org.joda.time.LocalTime value) {
    //     return LocalTime.of(value.getHourOfDay(),
    //         value.getMinuteOfHour(),
    //         value.getSecondOfMinute(),
    //         value.getMillisOfSecond() * 1000);
    // }

    // public static LocalTime toJdk8LocalTime(org.joda.time.LocalDateTime value) {
    //     return LocalTime.of(value.getHourOfDay(),
    //         value.getMinuteOfHour(),
    //         value.getSecondOfMinute(),
    //         value.getMillisOfSecond() * 1000);
    // }

    // public static LocalTime toJdk8LocalTime(MutableDateTime value) { return toJdk8LocalTime(value.getMillis()); }

    // public static LocalTime toJdk8LocalTime(org.joda.time.Instant value) { return toJdk8LocalTime(value.getMillis()); }

    public static LocalTime toJdk8LocalTime(Long value) { return toJdk8LocalDateTime(value).toLocalTime(); }

    public static LocalTime toJdk8LocalTime(long value) { return toJdk8LocalDateTime(value).toLocalTime(); }

    public static LocalDateTime toJdk8LocalDateTime(YearMonth value) { return toJdk8LocalDate(value).atStartOfDay(); }

    // public static LocalDateTime toJdk8LocalDateTime(org.joda.time.YearMonth value) { return toJdk8LocalDate(value).atStartOfDay(); }

    public static LocalDateTime toJdk8LocalDateTime(LocalDate value) { return value.atStartOfDay(); }

    public static LocalDateTime toJdk8LocalDateTime(OffsetDateTime value) { return toJdk8LocalDateTime(value.toInstant()); }

    public static LocalDateTime toJdk8LocalDateTime(ZonedDateTime value) { return toJdk8LocalDateTime(value.toInstant()); }

    public static LocalDateTime toJdk8LocalDateTime(Instant value) {
        return LocalDateTime.ofInstant(value, systemDefault());
    }

    public static LocalDateTime toJdk8LocalDateTime(Date value) { return toJdk8LocalDateTime(value.getTime()); }

    public static LocalDateTime toJdk8LocalDateTime(Calendar value) { return toJdk8LocalDateTime(value.getTimeInMillis()); }

    public static LocalDateTime toJdk8LocalDateTime(java.sql.Date value) { return toJdk8LocalDateTime(value.getTime()); }

    public static LocalDateTime toJdk8LocalDateTime(Timestamp value) { return value.toLocalDateTime(); }

    // public static LocalDateTime toJdk8LocalDateTime(DateTime value) { return toJdk8LocalDateTime(value.toDate()); }

    // public static LocalDateTime toJdk8LocalDateTime(org.joda.time.LocalDate value) { return toJdk8LocalDateTime(value.toDate()); }

    // public static LocalDateTime toJdk8LocalDateTime(org.joda.time.LocalDateTime value) {
    //     return toJdk8LocalDateTime(value.toDate());
    // }

    // public static LocalDateTime toJdk8LocalDateTime(MutableDateTime value) { return toJdk8LocalDateTime(value.getMillis()); }

    // public static LocalDateTime toJdk8LocalDateTime(org.joda.time.Instant value) { return toJdk8LocalDateTime(value.getMillis()); }

    public static LocalDateTime toJdk8LocalDateTime(Long value) { return toJdk8LocalDateTime(ofEpochMilli(value)); }

    public static LocalDateTime toJdk8LocalDateTime(long value) { return toJdk8LocalDateTime(ofEpochMilli(value)); }

    public static OffsetDateTime toJdk8OffsetDateTime(YearMonth value) {
        return toJdk8OffsetDateTime(toJdk8LocalDate(value));
    }

    // public static OffsetDateTime toJdk8OffsetDateTime(org.joda.time.YearMonth value) {
    //     return toJdk8OffsetDateTime(toJdk8LocalDate(value));
    // }

    public static OffsetDateTime toJdk8OffsetDateTime(LocalDate value) { return toJdk8OffsetDateTime(value.atStartOfDay()); }

    public static OffsetDateTime toJdk8OffsetDateTime(LocalDateTime value) {
        return value.atZone(systemDefault()).toOffsetDateTime();
    }

    public static OffsetDateTime toJdk8OffsetDateTime(ZonedDateTime value) { return value.toOffsetDateTime(); }

    public static OffsetDateTime toJdk8OffsetDateTime(Instant value) {
        return toJdk8OffsetDateTime(LocalDateTime.ofInstant(value, systemDefault()));
    }

    public static OffsetDateTime toJdk8OffsetDateTime(Date value) { return toJdk8OffsetDateTime(value.toInstant()); }

    public static OffsetDateTime toJdk8OffsetDateTime(Calendar value) { return toJdk8OffsetDateTime(value.toInstant()); }

    public static OffsetDateTime toJdk8OffsetDateTime(java.sql.Date value) { return toJdk8OffsetDateTime(value.getTime()); }

    public static OffsetDateTime toJdk8OffsetDateTime(Timestamp value) { return toJdk8OffsetDateTime(value.toInstant()); }

    // public static OffsetDateTime toJdk8OffsetDateTime(DateTime value) { return toJdk8OffsetDateTime(value.getMillis()); }

    // public static OffsetDateTime toJdk8OffsetDateTime(org.joda.time.LocalDate value) {
    //     return toJdk8OffsetDateTime(value.toDate());
    // }

    // public static OffsetDateTime toJdk8OffsetDateTime(org.joda.time.LocalDateTime value) {
    //     return toJdk8OffsetDateTime(value.toDate());
    // }

    // public static OffsetDateTime toJdk8OffsetDateTime(MutableDateTime value) { return toJdk8OffsetDateTime(value.getMillis()); }

    // public static OffsetDateTime toJdk8OffsetDateTime(org.joda.time.Instant value) { return toJdk8OffsetDateTime(value.getMillis()); }

    public static OffsetDateTime toJdk8OffsetDateTime(Long value) { return toJdk8OffsetDateTime(ofEpochMilli(value)); }

    public static OffsetDateTime toJdk8OffsetDateTime(long value) { return toJdk8OffsetDateTime(ofEpochMilli(value)); }

    public static ZonedDateTime toJdk8ZonedDateTime(YearMonth value) { return toJdk8ZonedDateTime(toJdk8LocalDate(value)); }

    // public static ZonedDateTime toJdk8ZonedDateTime(org.joda.time.YearMonth value) {
    //     return toJdk8ZonedDateTime(toJdk8LocalDate(value));
    // }

    public static ZonedDateTime toJdk8ZonedDateTime(LocalDate value) { return toJdk8ZonedDateTime(value.atStartOfDay()); }

    public static ZonedDateTime toJdk8ZonedDateTime(LocalDateTime value) { return value.atZone(systemDefault()); }

    public static ZonedDateTime toJdk8ZonedDateTime(OffsetDateTime value) { return ZonedDateTime.from(value); }

    public static ZonedDateTime toJdk8ZonedDateTime(Instant value) { return value.atZone(systemDefault()); }

    public static ZonedDateTime toJdk8ZonedDateTime(Date value) { return value.toInstant().atZone(systemDefault()); }

    public static ZonedDateTime toJdk8ZonedDateTime(Calendar value) {
        return value.toInstant().atZone(systemDefault());
    }

    public static ZonedDateTime toJdk8ZonedDateTime(java.sql.Date value) {
        return toJdk8ZonedDateTime(value.getTime());
    }

    public static ZonedDateTime toJdk8ZonedDateTime(Timestamp value) {
        return value.toInstant().atZone(systemDefault());
    }

    // public static ZonedDateTime toJdk8ZonedDateTime(DateTime value) { return toJdk8ZonedDateTime(value.getMillis()); }
    //
    // public static ZonedDateTime toJdk8ZonedDateTime(org.joda.time.LocalDate value) { return toJdk8ZonedDateTime(value.toDate()); }
    //
    // public static ZonedDateTime toJdk8ZonedDateTime(org.joda.time.LocalDateTime value) {
    //     return toJdk8ZonedDateTime(value.toDate());
    // }

    // public static ZonedDateTime toJdk8ZonedDateTime(MutableDateTime value) { return toJdk8ZonedDateTime(value.toDate()); }

    // public static ZonedDateTime toJdk8ZonedDateTime(org.joda.time.Instant value) { return toJdk8ZonedDateTime(value.toDate()); }

    public static ZonedDateTime toJdk8ZonedDateTime(Long value) { return toJdk8ZonedDateTime(ofEpochMilli(value)); }

    public static ZonedDateTime toJdk8ZonedDateTime(long value) { return toJdk8ZonedDateTime(ofEpochMilli(value)); }

    public static Instant toJdk8Instant(YearMonth value) { return toJdk8Instant(toJdk8LocalDate(value)); }

    // public static Instant toJdk8Instant(org.joda.time.YearMonth value) { return toJdk8Instant(toJdk8LocalDate(value)); }

    public static Instant toJdk8Instant(LocalDate value) { return toJdk8Instant(value.atStartOfDay()); }

    public static Instant toJdk8Instant(LocalDateTime value) { return toJdk8Instant(value.atZone(systemDefault())); }

    public static Instant toJdk8Instant(OffsetDateTime value) { return value.toInstant(); }

    public static Instant toJdk8Instant(ZonedDateTime value) { return value.toInstant(); }

    public static Instant toJdk8Instant(Date value) { return value.toInstant(); }

    public static Instant toJdk8Instant(Calendar value) { return value.toInstant(); }

    public static Instant toJdk8Instant(java.sql.Date value) { return Instant.ofEpochMilli(value.getTime()); }

    public static Instant toJdk8Instant(Timestamp value) { return value.toInstant(); }

    // public static Instant toJdk8Instant(DateTime value) { return toJdk8Instant(value.getMillis()); }
    //
    // public static Instant toJdk8Instant(org.joda.time.LocalDate value) { return value.toDate().toInstant(); }
    //
    // public static Instant toJdk8Instant(org.joda.time.LocalDateTime value) { return toJdk8Instant(value.toDate()); }
    //
    // public static Instant toJdk8Instant(MutableDateTime value) { return toJdk8Instant(value.getMillis()); }
    //
    // public static Instant toJdk8Instant(org.joda.time.Instant value) { return ofEpochMilli(value.getMillis()); }

    public static Instant toJdk8Instant(Long value) { return ofEpochMilli(value); }

    public static Instant toJdk8Instant(long value) { return ofEpochMilli(value); }

    public static Date toUtilDate(YearMonth value) { return toUtilDate(toJdk8LocalDate(value)); }

    // public static Date toUtilDate(org.joda.time.YearMonth value) { return toUtilDate(toJdk8LocalDate(value)); }

    public static Date toUtilDate(LocalDate value) { return toUtilDate(value.atStartOfDay()); }

    public static Date toUtilDate(LocalDateTime value) { return toUtilDate(value.atZone(systemDefault())); }

    public static Date toUtilDate(OffsetDateTime value) { return toUtilDate(value.toInstant()); }

    public static Date toUtilDate(ZonedDateTime value) { return toUtilDate(value.toInstant()); }

    public static Date toUtilDate(Instant value) { return Date.from(value); }

    public static Date toUtilDate(Calendar value) { return new Date(value.getTimeInMillis()); }

    public static Date toUtilDate(java.sql.Date value) { return new Date(value.getTime()); }

    public static Date toUtilDate(Timestamp value) { return new Date(value.getTime()); }

    // public static Date toUtilDate(DateTime value) { return value.toDate(); }

    // public static Date toUtilDate(org.joda.time.LocalDate value) { return value.toDate(); }
    //
    // public static Date toUtilDate(org.joda.time.LocalDateTime value) { return value.toDate(); }
    //
    // public static Date toUtilDate(MutableDateTime value) { return value.toDate(); }
    //
    // public static Date toUtilDate(org.joda.time.Instant value) { return value.toDate(); }

    public static Date toUtilDate(Long value) { return new Date(value); }

    public static Date toUtilDate(long value) { return new Date(value); }

    public static Calendar toUtilCalendar(YearMonth value) { return toUtilCalendar(toJdk8LocalDate(value)); }

    // public static Calendar toUtilCalendar(org.joda.time.YearMonth value) { return toUtilCalendar(toJdk8LocalDate(value)); }

    public static Calendar toUtilCalendar(LocalDate value) { return toUtilCalendar(value.atStartOfDay()); }

    public static Calendar toUtilCalendar(LocalDateTime value) { return toUtilCalendar(value.atZone(systemDefault())); }

    public static Calendar toUtilCalendar(OffsetDateTime value) { return toUtilCalendar(value.toInstant()); }

    public static Calendar toUtilCalendar(ZonedDateTime value) { return toUtilCalendar(value.toInstant()); }

    public static Calendar toUtilCalendar(Instant value) { return toUtilCalendar(value.toEpochMilli()); }

    public static Calendar toUtilCalendar(Date value) { return toUtilCalendar(value.getTime()); }

    public static Calendar toUtilCalendar(java.sql.Date value) { return toUtilCalendar(value.getTime()); }

    public static Calendar toUtilCalendar(Timestamp value) { return toUtilCalendar(value.getTime()); }

    // public static Calendar toUtilCalendar(DateTime value) { return toUtilCalendar(value.toDate()); }
    //
    // public static Calendar toUtilCalendar(org.joda.time.LocalDate value) { return toUtilCalendar(value.toDate()); }
    //
    // public static Calendar toUtilCalendar(org.joda.time.LocalDateTime value) { return toUtilCalendar(value.toDate()); }
    //
    // public static Calendar toUtilCalendar(MutableDateTime value) { return toUtilCalendar(value.toDate()); }
    //
    // public static Calendar toUtilCalendar(org.joda.time.Instant value) { return toUtilCalendar(value.toDate()); }

    public static Calendar toUtilCalendar(Long value) {
        if (value == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(value);
        return calendar;
    }

    public static Calendar toUtilCalendar(long value) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(value);
        return calendar;
    }

    public static java.sql.Date toSqlDate(YearMonth value) { return java.sql.Date.valueOf(toJdk8LocalDate(value)); }

    // public static java.sql.Date toSqlDate(org.joda.time.YearMonth value) {
    //     return java.sql.Date.valueOf(toJdk8LocalDate(value));
    // }

    public static java.sql.Date toSqlDate(LocalDate value) { return java.sql.Date.valueOf(value); }

    public static java.sql.Date toSqlDate(LocalDateTime value) { return toSqlDate(value.toLocalDate()); }

    public static java.sql.Date toSqlDate(OffsetDateTime value) { return toSqlDate(value.toLocalDate()); }

    public static java.sql.Date toSqlDate(ZonedDateTime value) { return toSqlDate(value.toLocalDate()); }

    public static java.sql.Date toSqlDate(Instant value) { return toSqlDate(value.toEpochMilli()); }

    public static java.sql.Date toSqlDate(Date value) { return new java.sql.Date(value.getTime()); }

    public static java.sql.Date toSqlDate(Calendar value) { return new java.sql.Date(value.getTimeInMillis()); }

    public static java.sql.Date toSqlDate(Timestamp value) { return new java.sql.Date(value.getTime()); }

    // public static java.sql.Date toSqlDate(DateTime value) { return toSqlDate(value.toDate()); }
    //
    // public static java.sql.Date toSqlDate(org.joda.time.LocalDate value) { return toSqlDate(value.toDate()); }
    //
    // public static java.sql.Date toSqlDate(org.joda.time.LocalDateTime value) { return toSqlDate(value.toDate()); }
    //
    // public static java.sql.Date toSqlDate(MutableDateTime value) { return toSqlDate(value.getMillis()); }
    //
    // public static java.sql.Date toSqlDate(org.joda.time.Instant value) { return toSqlDate(value.getMillis()); }

    public static java.sql.Date toSqlDate(Long value) { return new java.sql.Date(value); }

    public static java.sql.Date toSqlDate(long value) { return new java.sql.Date(value); }

    public static Time toSqlTime(LocalTime value) { return Time.valueOf(value); }

    public static Time toSqlTime(LocalDateTime value) { return Time.valueOf(value.toLocalTime()); }

    public static Time toSqlTime(OffsetDateTime value) { return Time.valueOf(value.toLocalTime()); }

    public static Time toSqlTime(ZonedDateTime value) { return Time.valueOf(value.toLocalTime()); }

    public static Time toSqlTime(Instant value) { return new Time(value.toEpochMilli()); }

    public static Time toSqlTime(Date value) { return new Time(value.getTime()); }

    public static Time toSqlTime(Calendar value) { return new Time(value.getTimeInMillis()); }

    public static Time toSqlTime(Timestamp value) { return new Time(value.getTime()); }

    // public static Time toSqlTime(DateTime value) { return new Time(value.getMillis()); }

    // public static Time toSqlTime(org.joda.time.LocalTime value) { return toSqlTime(value.toDateTimeToday()); }

    // public static Time toSqlTime(org.joda.time.LocalDateTime value) { return toSqlTime(value.toDate()); }

    // public static Time toSqlTime(MutableDateTime value) { return new Time(value.getMillis()); }

    // public static Time toSqlTime(org.joda.time.Instant value) { return new Time(value.getMillis()); }

    public static Time toSqlTime(Long value) { return new Time(value); }

    public static Time toSqlTime(long value) { return new Time(value); }

    public static Timestamp toSqlTimestamp(YearMonth value) { return toSqlTimestamp(toJdk8LocalDate(value)); }

    // public static Timestamp toSqlTimestamp(org.joda.time.YearMonth value) { return toSqlTimestamp(toJdk8LocalDate(value)); }

    public static Timestamp toSqlTimestamp(LocalDate value) { return toSqlTimestamp(value.atStartOfDay()); }

    public static Timestamp toSqlTimestamp(LocalDateTime value) { return toSqlTimestamp(value.atZone(systemDefault())); }

    public static Timestamp toSqlTimestamp(OffsetDateTime value) { return toSqlTimestamp(value.toInstant()); }

    public static Timestamp toSqlTimestamp(ZonedDateTime value) { return toSqlTimestamp(value.toInstant()); }

    public static Timestamp toSqlTimestamp(Instant value) { return new Timestamp(value.toEpochMilli()); }

    public static Timestamp toSqlTimestamp(Date value) { return new Timestamp(value.getTime()); }

    public static Timestamp toSqlTimestamp(Calendar value) { return new Timestamp(value.getTimeInMillis()); }

    public static Timestamp toSqlTimestamp(java.sql.Date value) { return new Timestamp(value.getTime()); }

    // public static Timestamp toSqlTimestamp(DateTime value) { return new Timestamp(value.getMillis()); }

    // public static Timestamp toSqlTimestamp(org.joda.time.LocalDate value) { return toSqlTimestamp(value.toDate()); }

    // public static Timestamp toSqlTimestamp(org.joda.time.LocalDateTime value) { return toSqlTimestamp(value.toDate()); }

    // public static Timestamp toSqlTimestamp(MutableDateTime value) { return new Timestamp(value.getMillis()); }

    // public static Timestamp toSqlTimestamp(org.joda.time.Instant value) { return new Timestamp(value.getMillis()); }

    // public static Timestamp toSqlTimestamp(Long value) { return new Timestamp(value); }

    public static Timestamp toSqlTimestamp(long value) { return new Timestamp(value); }

    // public static DateTime toJodaDateTime(YearMonth value) { return toJodaDateTime(toJdk8LocalDate(value)); }
    //
    // public static DateTime toJodaDateTime(org.joda.time.YearMonth value) { return toJodaDateTime(toJdk8LocalDate(value)); }
    //
    // public static DateTime toJodaDateTime(LocalDate value) { return toJodaDateTime(value.atStartOfDay()); }
    //
    // public static DateTime toJodaDateTime(LocalDateTime value) { return toJodaDateTime(value.atZone(systemDefault())); }
    //
    // public static DateTime toJodaDateTime(OffsetDateTime value) { return toJodaDateTime(value.toInstant()); }
    //
    // public static DateTime toJodaDateTime(ZonedDateTime value) { return toJodaDateTime(value.toInstant()); }
    //
    // public static DateTime toJodaDateTime(Instant value) { return toJodaDateTime(value.toEpochMilli()); }
    //
    // public static DateTime toJodaDateTime(Date value) { return toJodaDateTime(value.getTime()); }
    //
    // public static DateTime toJodaDateTime(Calendar value) { return toJodaDateTime(value.getTimeInMillis()); }
    //
    // public static DateTime toJodaDateTime(java.sql.Date value) { return toJodaDateTime(value.getTime()); }
    //
    // public static DateTime toJodaDateTime(Timestamp value) { return toJodaDateTime(value.getTime()); }
    //
    // public static DateTime toJodaDateTime(org.joda.time.LocalDate value) { return toJodaDateTime(value.toDate()); }
    //
    // public static DateTime toJodaDateTime(org.joda.time.LocalDateTime value) { return toJodaDateTime(value.toDate()); }
    //
    // public static DateTime toJodaDateTime(MutableDateTime value) { return toJodaDateTime(value.getMillis()); }
    //
    // public static DateTime toJodaDateTime(org.joda.time.Instant value) { return toJodaDateTime(value.getMillis()); }
    //
    // public static DateTime toJodaDateTime(Long value) { return new DateTime(value); }
    //
    // public static DateTime toJodaDateTime(long value) { return new DateTime(value); }
    //
    // public static org.joda.time.LocalDate toJodaLocalDate(YearMonth value) {
    //     return new org.joda.time.LocalDate(value.getYear(), value.getMonthValue(), 1);
    // }
    //
    // public static org.joda.time.LocalDate toJodaLocalDate(org.joda.time.YearMonth value) {
    //     return new org.joda.time.LocalDate(value.getYear(), value.getMonthOfYear(), 1);
    // }
    //
    // public static org.joda.time.LocalDate toJodaLocalDate(LocalDate value) {
    //     return new org.joda.time.LocalDate(value.getYear(), value.getMonthValue(), value.getDayOfMonth());
    // }
    //
    // public static org.joda.time.LocalDate toJodaLocalDate(LocalDateTime value) { return toJodaLocalDate(value.toLocalDate()); }
    //
    // public static org.joda.time.LocalDate toJodaLocalDate(OffsetDateTime value) { return toJodaLocalDate(value.toLocalDate()); }
    //
    // public static org.joda.time.LocalDate toJodaLocalDate(ZonedDateTime value) { return toJodaLocalDate(value.toLocalDate()); }
    //
    // public static org.joda.time.LocalDate toJodaLocalDate(Instant value) { return toJodaLocalDate(value.toEpochMilli()); }
    //
    // public static org.joda.time.LocalDate toJodaLocalDate(Date value) {
    //     return org.joda.time.LocalDate.fromDateFields(value);
    // }
    //
    // public static org.joda.time.LocalDate toJodaLocalDate(Calendar value) {
    //     return org.joda.time.LocalDate.fromCalendarFields(value);
    // }
    //
    // public static org.joda.time.LocalDate toJodaLocalDate(java.sql.Date value) {
    //     return org.joda.time.LocalDate.fromDateFields(value);
    // }
    //
    // public static org.joda.time.LocalDate toJodaLocalDate(Timestamp value) {
    //     return org.joda.time.LocalDate.fromDateFields(value);
    // }
    //
    // public static org.joda.time.LocalDate toJodaLocalDate(DateTime value) {
    //     return new org.joda.time.LocalDate(value);
    // }
    //
    // public static org.joda.time.LocalDate toJodaLocalDate(org.joda.time.LocalDateTime value) {
    //     return new org.joda.time.LocalDate(value);
    // }
    //
    // public static org.joda.time.LocalDate toJodaLocalDate(MutableDateTime value) {
    //     return new org.joda.time.LocalDate(value);
    // }
    //
    // public static org.joda.time.LocalDate toJodaLocalDate(org.joda.time.Instant value) {
    //     return new org.joda.time.LocalDate(value);
    // }
    //
    // public static org.joda.time.LocalDate toJodaLocalDate(Long value) {
    //     return new org.joda.time.LocalDate(value);
    // }
    //
    // public static org.joda.time.LocalDate toJodaLocalDate(long value) {
    //     return new org.joda.time.LocalDate(value);
    // }
    //
    // public static org.joda.time.LocalTime toJodaLocalTime(LocalTime value) {
    //     return toJodaLocalTime(Time.valueOf(value));
    // }
    //
    // public static org.joda.time.LocalTime toJodaLocalTime(LocalDateTime value) {
    //     return toJodaLocalTime(value.atZone(systemDefault()));
    // }
    //
    // public static org.joda.time.LocalTime toJodaLocalTime(OffsetDateTime value) { return toJodaLocalTime(value.toInstant()); }
    //
    // public static org.joda.time.LocalTime toJodaLocalTime(ZonedDateTime value) { return toJodaLocalTime(value.toInstant()); }
    //
    // public static org.joda.time.LocalTime toJodaLocalTime(Instant value) {
    //     return org.joda.time.LocalTime.fromDateFields(Date.from(value));
    // }
    //
    // public static org.joda.time.LocalTime toJodaLocalTime(Date value) {
    //     return org.joda.time.LocalTime.fromDateFields(value);
    // }
    //
    // public static org.joda.time.LocalTime toJodaLocalTime(Calendar value) {
    //     return org.joda.time.LocalTime.fromCalendarFields(value);
    // }
    //
    // public static org.joda.time.LocalTime toJodaLocalTime(Time value) { return new org.joda.time.LocalTime(value); }
    //
    // public static org.joda.time.LocalTime toJodaLocalTime(Timestamp value) {
    //     return org.joda.time.LocalTime.fromDateFields(value);
    // }
    //
    // public static org.joda.time.LocalTime toJodaLocalTime(DateTime value) { return new org.joda.time.LocalTime(value); }
    //
    // public static org.joda.time.LocalTime toJodaLocalTime(org.joda.time.LocalDateTime value) {
    //     return new org.joda.time.LocalTime(value);
    // }
    //
    // public static org.joda.time.LocalTime toJodaLocalTime(MutableDateTime value) {
    //     return new org.joda.time.LocalTime(value);
    // }
    //
    // public static org.joda.time.LocalTime toJodaLocalTime(org.joda.time.Instant value) {
    //     return new org.joda.time.LocalTime(value);
    // }
    //
    // public static org.joda.time.LocalTime toJodaLocalTime(Long value) {
    //     return new org.joda.time.LocalTime(value);
    // }
    //
    // public static org.joda.time.LocalTime toJodaLocalTime(long value) {
    //     return new org.joda.time.LocalTime(value);
    // }
    //
    // public static org.joda.time.LocalDateTime toJodaLocalDateTime(YearMonth value) {
    //     return toJodaLocalDateTime(toJdk8LocalDate(value));
    // }
    //
    // public static org.joda.time.LocalDateTime toJodaLocalDateTime(org.joda.time.YearMonth value) {
    //     return toJodaLocalDateTime(toJdk8LocalDate(value));
    // }
    //
    // public static org.joda.time.LocalDateTime toJodaLocalDateTime(LocalDate value) {
    //     return new org.joda.time.LocalDateTime(java.sql.Date.valueOf(value));
    // }
    //
    // public static org.joda.time.LocalDateTime toJodaLocalDateTime(LocalDateTime value) {
    //     return toJodaLocalDateTime(value.atZone(systemDefault()));
    // }
    //
    // public static org.joda.time.LocalDateTime toJodaLocalDateTime(OffsetDateTime value) {
    //     return toJodaLocalDateTime(value.toInstant());
    // }
    //
    // public static org.joda.time.LocalDateTime toJodaLocalDateTime(ZonedDateTime value) {
    //     return toJodaLocalDateTime(value.toInstant());
    // }
    //
    // public static org.joda.time.LocalDateTime toJodaLocalDateTime(Instant value) {
    //     return toJodaLocalDateTime(value.toEpochMilli());
    // }
    //
    // public static org.joda.time.LocalDateTime toJodaLocalDateTime(Date value) {
    //     return org.joda.time.LocalDateTime.fromDateFields(value);
    // }
    //
    // public static org.joda.time.LocalDateTime toJodaLocalDateTime(Calendar value) {
    //     return org.joda.time.LocalDateTime.fromCalendarFields(value);
    // }
    //
    // public static org.joda.time.LocalDateTime toJodaLocalDateTime(java.sql.Date value) {
    //     return new org.joda.time.LocalDateTime(value.getTime());
    // }
    //
    // public static org.joda.time.LocalDateTime toJodaLocalDateTime(Timestamp value) {
    //     return new org.joda.time.LocalDateTime(value.getTime());
    // }
    //
    // public static org.joda.time.LocalDateTime toJodaLocalDateTime(DateTime value) {
    //     return new org.joda.time.LocalDateTime(value);
    // }
    //
    // public static org.joda.time.LocalDateTime toJodaLocalDateTime(org.joda.time.LocalDate value) {
    //     return new org.joda.time.LocalDateTime(value.getYear(), value.getMonthOfYear(), value.getDayOfMonth(), 0, 0);
    // }
    //
    // public static org.joda.time.LocalDateTime toJodaLocalDateTime(MutableDateTime value) {
    //     return new org.joda.time.LocalDateTime(value.getMillis());
    // }
    //
    // public static org.joda.time.LocalDateTime toJodaLocalDateTime(org.joda.time.Instant value) {
    //     return new org.joda.time.LocalDateTime(value.getMillis());
    // }
    //
    // public static org.joda.time.LocalDateTime toJodaLocalDateTime(Long value) {
    //     return new org.joda.time.LocalDateTime(value);
    // }
    //
    // public static org.joda.time.LocalDateTime toJodaLocalDateTime(long value) {
    //     return new org.joda.time.LocalDateTime(value);
    // }
    //
    // public static MutableDateTime toJodaMutableDateTime(YearMonth value) {
    //     return toJodaMutableDateTime(toJdk8LocalDate(value));
    // }
    //
    // public static MutableDateTime toJodaMutableDateTime(org.joda.time.YearMonth value) {
    //     return toJodaMutableDateTime(toJdk8LocalDate(value));
    // }
    //
    // public static MutableDateTime toJodaMutableDateTime(LocalDate value) { return toJodaMutableDateTime(value.atStartOfDay()); }
    //
    // public static MutableDateTime toJodaMutableDateTime(LocalDateTime value) {
    //     return toJodaMutableDateTime(value.atZone(systemDefault()));
    // }
    //
    // public static MutableDateTime toJodaMutableDateTime(OffsetDateTime value) { return toJodaMutableDateTime(value.toInstant()); }
    //
    // public static MutableDateTime toJodaMutableDateTime(ZonedDateTime value) { return toJodaMutableDateTime(value.toInstant()); }
    //
    // public static MutableDateTime toJodaMutableDateTime(Instant value) { return new MutableDateTime(value.toEpochMilli()); }
    //
    // public static MutableDateTime toJodaMutableDateTime(Date value) { return new MutableDateTime(value.getTime()); }
    //
    // public static MutableDateTime toJodaMutableDateTime(Calendar value) { return new MutableDateTime(value.getTimeInMillis()); }
    //
    // public static MutableDateTime toJodaMutableDateTime(java.sql.Date value) { return new MutableDateTime(value.getTime()); }
    //
    // public static MutableDateTime toJodaMutableDateTime(Timestamp value) { return new MutableDateTime(value.getTime()); }
    //
    // public static MutableDateTime toJodaMutableDateTime(DateTime value) { return new MutableDateTime(value.getMillis()); }
    //
    // public static MutableDateTime toJodaMutableDateTime(org.joda.time.LocalDate value) {
    //     return new MutableDateTime(value.toDate());
    // }
    //
    // public static MutableDateTime toJodaMutableDateTime(org.joda.time.LocalDateTime value) {
    //     return new MutableDateTime(value.toDate());
    // }
    //
    // public static MutableDateTime toJodaMutableDateTime(org.joda.time.Instant value) { return new MutableDateTime(value); }
    //
    // public static MutableDateTime toJodaMutableDateTime(Long value) { return new MutableDateTime(value); }
    //
    // public static MutableDateTime toJodaMutableDateTime(long value) { return new MutableDateTime(value); }
    //
    // public static org.joda.time.Instant toJodaInstant(YearMonth value) { return toJodaInstant(toJdk8LocalDate(value)); }
    //
    // public static org.joda.time.Instant toJodaInstant(org.joda.time.YearMonth value) {
    //     return toJodaInstant(toJdk8LocalDate(value));
    // }
    //
    // public static org.joda.time.Instant toJodaInstant(LocalDate value) { return toJodaInstant(value.atStartOfDay()); }
    //
    // public static org.joda.time.Instant toJodaInstant(LocalDateTime value) {
    //     return toJodaInstant(value.atZone(systemDefault()));
    // }
    //
    // public static org.joda.time.Instant toJodaInstant(OffsetDateTime value) { return toJodaInstant(value.toInstant()); }
    //
    // public static org.joda.time.Instant toJodaInstant(ZonedDateTime value) { return toJodaInstant(value.toInstant()); }
    //
    // public static org.joda.time.Instant toJodaInstant(Instant value) { return new org.joda.time.Instant(value.toEpochMilli()); }
    //
    // public static org.joda.time.Instant toJodaInstant(Date value) { return new org.joda.time.Instant(value.getTime()); }
    //
    // public static org.joda.time.Instant toJodaInstant(Calendar value) { return new org.joda.time.Instant(value.getTimeInMillis()); }
    //
    // public static org.joda.time.Instant toJodaInstant(java.sql.Date value) { return new org.joda.time.Instant(value.getTime()); }
    //
    // public static org.joda.time.Instant toJodaInstant(Timestamp value) { return new org.joda.time.Instant(value.getTime()); }
    //
    // public static org.joda.time.Instant toJodaInstant(DateTime value) { return new org.joda.time.Instant(value.getMillis()); }
    //
    // public static org.joda.time.Instant toJodaInstant(org.joda.time.LocalDate value) { return toJodaInstant(value.toDate()); }
    //
    // public static org.joda.time.Instant toJodaInstant(org.joda.time.LocalDateTime value) { return toJodaInstant(value.toDate()); }
    //
    // public static org.joda.time.Instant toJodaInstant(MutableDateTime value) { return new org.joda.time.Instant(value); }
    //
    // public static org.joda.time.Instant toJodaInstant(Long value) { return new org.joda.time.Instant(value); }
    //
    // public static org.joda.time.Instant toJodaInstant(long value) { return new org.joda.time.Instant(value); }

    // public static YearMonth toJdk8YearMonth(org.joda.time.YearMonth value) {
    //     return YearMonth.of(value.getYear(), value.getMonthOfYear());
    // }

    public static YearMonth toJdk8YearMonth(LocalDate value) {
        return YearMonth.of(value.getYear(), value.getMonth());
    }

    public static YearMonth toJdk8YearMonth(LocalDateTime value) {
        return YearMonth.of(value.getYear(), value.getMonth());
    }

    public static YearMonth toJdk8YearMonth(OffsetDateTime value) {
        return YearMonth.of(value.getYear(), value.getMonth());
    }

    public static YearMonth toJdk8YearMonth(ZonedDateTime value) {
        return YearMonth.of(value.getYear(), value.getMonth());
    }

    public static YearMonth toJdk8YearMonth(Instant value) {
        return YearMonth.from(value.atZone(systemDefault()));
    }

    public static YearMonth toJdk8YearMonth(Date value) {
        return YearMonth.from(value.toInstant().atZone(systemDefault()));
    }

    public static YearMonth toJdk8YearMonth(Calendar value) {
        return YearMonth.from(value.toInstant().atZone(systemDefault()));
    }

    public static YearMonth toJdk8YearMonth(java.sql.Date value) {
        return YearMonth.from(value.toLocalDate());
    }

    public static YearMonth toJdk8YearMonth(Timestamp value) {
        return YearMonth.from(value.toLocalDateTime());
    }

    // public static YearMonth toJdk8YearMonth(DateTime value) {
    //     return YearMonth.of(value.getYear(), value.getMonthOfYear());
    // }

    // public static YearMonth toJdk8YearMonth(org.joda.time.LocalDate value) {
    //     return YearMonth.of(value.getYear(), value.getMonthOfYear());
    // }

    // public static YearMonth toJdk8YearMonth(org.joda.time.LocalDateTime value) {
    //     return YearMonth.of(value.getYear(), value.getMonthOfYear());
    // }

    // public static YearMonth toJdk8YearMonth(MutableDateTime value) {
    //     return YearMonth.of(value.getYear(), value.getMonthOfYear());
    // }

    // public static YearMonth toJdk8YearMonth(org.joda.time.Instant value) {
    //     return toJdk8YearMonth(toJdk8LocalDate(value));
    // }

    public static YearMonth toJdk8YearMonth(Long value) {
        return toJdk8YearMonth(toJdk8LocalDate(value));
    }

    public static YearMonth toJdk8YearMonth(long value) {
        return toJdk8YearMonth(toJdk8LocalDate(value));
    }

    // public static org.joda.time.YearMonth toJodaYearMonth(YearMonth value) {
    //     return new org.joda.time.YearMonth(value.getYear(), value.getMonthValue());
    // }
    //
    // public static org.joda.time.YearMonth toJodaYearMonth(LocalDate value) {
    //     return new org.joda.time.YearMonth(value.getYear(), value.getMonthValue());
    // }
    //
    // public static org.joda.time.YearMonth toJodaYearMonth(LocalDateTime value) {
    //     return new org.joda.time.YearMonth(value.getYear(), value.getMonthValue());
    // }
    //
    // public static org.joda.time.YearMonth toJodaYearMonth(OffsetDateTime value) {
    //     return new org.joda.time.YearMonth(value.getYear(), value.getMonthValue());
    // }
    //
    // public static org.joda.time.YearMonth toJodaYearMonth(ZonedDateTime value) {
    //     return new org.joda.time.YearMonth(value.getYear(), value.getMonthValue());
    // }
    //
    // public static org.joda.time.YearMonth toJodaYearMonth(Instant value) {
    //     return toJodaYearMonth(value.atZone(systemDefault()));
    // }
    //
    // public static org.joda.time.YearMonth toJodaYearMonth(Date value) {
    //     return org.joda.time.YearMonth.fromDateFields(value);
    // }
    //
    // public static org.joda.time.YearMonth toJodaYearMonth(Calendar value) {
    //     return org.joda.time.YearMonth.fromCalendarFields(value);
    // }
    //
    // public static org.joda.time.YearMonth toJodaYearMonth(java.sql.Date value) {
    //     return toJodaYearMonth(value.toLocalDate());
    // }
    //
    // public static org.joda.time.YearMonth toJodaYearMonth(Timestamp value) {
    //     return toJodaYearMonth(value.toLocalDateTime());
    // }

    // public static org.joda.time.YearMonth toJodaYearMonth(DateTime value) {
    //     return new org.joda.time.YearMonth(value.getYear(), value.getMonthOfYear());
    // }

    // public static org.joda.time.YearMonth toJodaYearMonth(org.joda.time.LocalDate value) {
    //     return new org.joda.time.YearMonth(value.getYear(), value.getMonthOfYear());
    // }

    // public static org.joda.time.YearMonth toJodaYearMonth(org.joda.time.LocalDateTime value) {
    //     return new org.joda.time.YearMonth(value.getYear(), value.getMonthOfYear());
    // }

    // public static org.joda.time.YearMonth toJodaYearMonth(MutableDateTime value) {
    //     return new org.joda.time.YearMonth(value.getYear(), value.getMonthOfYear());
    // }

    // public static org.joda.time.YearMonth toJodaYearMonth(org.joda.time.Instant value) {
    //     return toJodaYearMonth(toJdk8LocalDate(value));
    // }

    // public static org.joda.time.YearMonth toJodaYearMonth(Long value) {
    //     return toJodaYearMonth(new Date(value));
    // }

    // public static org.joda.time.YearMonth toJodaYearMonth(long value) {
    //     return toJodaYearMonth(new Date(value));
    // }

    public static long toPrimitiveLong(YearMonth value) { return toPrimitiveLong(toJdk8LocalDate(value)); }

    // public static long toPrimitiveLong(org.joda.time.YearMonth value) { return toPrimitiveLong(toJdk8LocalDate(value)); }

    public static long toPrimitiveLong(LocalDate value) { return toPrimitiveLong(value.atStartOfDay()); }

    public static long toPrimitiveLong(LocalDateTime value) { return toPrimitiveLong(value.atZone(systemDefault())); }

    public static long toPrimitiveLong(OffsetDateTime value) { return toPrimitiveLong(value.toInstant()); }

    public static long toPrimitiveLong(ZonedDateTime value) { return toPrimitiveLong(value.toInstant()); }

    public static long toPrimitiveLong(Instant value) { return value.toEpochMilli(); }

    public static long toPrimitiveLong(Date value) { return value.getTime(); }

    public static long toPrimitiveLong(Calendar value) { return value.getTimeInMillis(); }

    public static long toPrimitiveLong(java.sql.Date value) { return value.getTime(); }

    public static long toPrimitiveLong(Timestamp value) { return value.getTime(); }

    // public static long toPrimitiveLong(DateTime value) { return value.getMillis(); }
    //
    // public static long toPrimitiveLong(org.joda.time.LocalDate value) { return value.toDate().getTime(); }
    //
    // public static long toPrimitiveLong(org.joda.time.LocalDateTime value) { return  value.toDate().getTime(); }
    //
    // public static long toPrimitiveLong(MutableDateTime value) { return value.getMillis(); }
    //
    // public static long toPrimitiveLong(org.joda.time.Instant value) { return value.getMillis(); }

    public static Long toLong(YearMonth value) { return toLong(toJdk8LocalDate(value)); }

    // public static Long toLong(org.joda.time.YearMonth value) { return toLong(toJdk8LocalDate(value)); }

    public static Long toLong(LocalDate value) { return toLong(value.atStartOfDay()); }

    public static Long toLong(LocalDateTime value) { return toLong(value.atZone(systemDefault())); }

    public static Long toLong(OffsetDateTime value) { return toLong(value.toInstant()); }

    public static Long toLong(ZonedDateTime value) { return toLong(value.toInstant()); }

    public static Long toLong(Instant value) { return value.toEpochMilli(); }

    public static Long toLong(Date value) { return value.getTime(); }

    public static Long toLong(Calendar value) { return value.getTimeInMillis(); }

    public static Long toLong(java.sql.Date value) { return value.getTime(); }

    public static Long toLong(Timestamp value) { return value.getTime(); }

    // public static Long toLong(DateTime value) { return value.getMillis(); }
    //
    // public static Long toLong(org.joda.time.LocalDate value) { return value.toDate().getTime(); }
    //
    // public static Long toLong(org.joda.time.LocalDateTime value) { return value.toDate().getTime(); }
    //
    // public static Long toLong(MutableDateTime value) { return value.getMillis(); }
    //
    // public static Long toLong(org.joda.time.Instant value) { return value.getMillis(); }
}

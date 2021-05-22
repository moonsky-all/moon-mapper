package com.moonsky.mapper.util;

import org.joda.time.DateTime;
import org.joda.time.MutableDateTime;

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
public abstract class Joda1xConvert extends DateConvert {

    Joda1xConvert() { super(); }

    public static LocalDate toJdk8LocalDate(DateTime value) { return toJdk8LocalDate(value.getMillis()); }

    public static LocalDate toJdk8LocalDate(org.joda.time.LocalDate value) {
        return LocalDate.of(value.getYear(), value.getMonthOfYear(), value.getDayOfMonth());
    }

    public static LocalDate toJdk8LocalDate(org.joda.time.LocalDateTime value) {
        return LocalDate.of(value.getYear(), value.getMonthOfYear(), value.getDayOfMonth());
    }

    public static LocalDate toJdk8LocalDate(MutableDateTime value) { return toJdk8LocalDate(value.getMillis()); }

    public static LocalDate toJdk8LocalDate(org.joda.time.Instant value) {
        return toJdk8LocalDateTime(value.getMillis()).toLocalDate();
    }

    public static LocalTime toJdk8LocalTime(DateTime value) { return toJdk8LocalTime(value.getMillis()); }

    public static LocalTime toJdk8LocalTime(org.joda.time.LocalTime value) {
        return LocalTime.of(value.getHourOfDay(),
            value.getMinuteOfHour(),
            value.getSecondOfMinute(),
            value.getMillisOfSecond() * 1000);
    }

    public static LocalTime toJdk8LocalTime(org.joda.time.LocalDateTime value) {
        return LocalTime.of(value.getHourOfDay(),
            value.getMinuteOfHour(),
            value.getSecondOfMinute(),
            value.getMillisOfSecond() * 1000);
    }

    public static LocalTime toJdk8LocalTime(MutableDateTime value) { return toJdk8LocalTime(value.getMillis()); }

    public static LocalTime toJdk8LocalTime(org.joda.time.Instant value) { return toJdk8LocalTime(value.getMillis()); }

    public static LocalDateTime toJdk8LocalDateTime(DateTime value) { return toJdk8LocalDateTime(value.toDate()); }

    public static LocalDateTime toJdk8LocalDateTime(org.joda.time.LocalDate value) { return toJdk8LocalDateTime(value.toDate()); }

    public static LocalDateTime toJdk8LocalDateTime(org.joda.time.LocalDateTime value) {
        return toJdk8LocalDateTime(value.toDate());
    }

    public static LocalDateTime toJdk8LocalDateTime(MutableDateTime value) { return toJdk8LocalDateTime(value.getMillis()); }

    public static LocalDateTime toJdk8LocalDateTime(org.joda.time.Instant value) { return toJdk8LocalDateTime(value.getMillis()); }

    public static OffsetDateTime toJdk8OffsetDateTime(DateTime value) { return toJdk8OffsetDateTime(value.getMillis()); }

    public static OffsetDateTime toJdk8OffsetDateTime(org.joda.time.LocalDate value) {
        return toJdk8OffsetDateTime(value.toDate());
    }

    public static OffsetDateTime toJdk8OffsetDateTime(org.joda.time.LocalDateTime value) {
        return toJdk8OffsetDateTime(value.toDate());
    }

    public static OffsetDateTime toJdk8OffsetDateTime(MutableDateTime value) { return toJdk8OffsetDateTime(value.getMillis()); }

    public static OffsetDateTime toJdk8OffsetDateTime(org.joda.time.Instant value) { return toJdk8OffsetDateTime(value.getMillis()); }

    public static ZonedDateTime toJdk8ZonedDateTime(DateTime value) { return toJdk8ZonedDateTime(value.getMillis()); }

    public static ZonedDateTime toJdk8ZonedDateTime(org.joda.time.LocalDate value) { return toJdk8ZonedDateTime(value.toDate()); }

    public static ZonedDateTime toJdk8ZonedDateTime(org.joda.time.LocalDateTime value) {
        return toJdk8ZonedDateTime(value.toDate());
    }

    public static ZonedDateTime toJdk8ZonedDateTime(MutableDateTime value) { return toJdk8ZonedDateTime(value.toDate()); }

    public static ZonedDateTime toJdk8ZonedDateTime(org.joda.time.Instant value) { return toJdk8ZonedDateTime(value.toDate()); }

    public static Instant toJdk8Instant(DateTime value) { return toJdk8Instant(value.getMillis()); }

    public static Instant toJdk8Instant(org.joda.time.LocalDate value) { return value.toDate().toInstant(); }

    public static Instant toJdk8Instant(org.joda.time.LocalDateTime value) { return toJdk8Instant(value.toDate()); }

    public static Instant toJdk8Instant(MutableDateTime value) { return toJdk8Instant(value.getMillis()); }

    public static Instant toJdk8Instant(org.joda.time.Instant value) { return ofEpochMilli(value.getMillis()); }

    public static Date toUtilDate(DateTime value) { return value.toDate(); }

    public static Date toUtilDate(org.joda.time.LocalDate value) { return value.toDate(); }

    public static Date toUtilDate(org.joda.time.LocalDateTime value) { return value.toDate(); }

    public static Date toUtilDate(MutableDateTime value) { return value.toDate(); }

    public static Date toUtilDate(org.joda.time.Instant value) { return value.toDate(); }

    public static Calendar toUtilCalendar(DateTime value) { return toUtilCalendar(value.toDate()); }

    public static Calendar toUtilCalendar(org.joda.time.LocalDate value) { return toUtilCalendar(value.toDate()); }

    public static Calendar toUtilCalendar(org.joda.time.LocalDateTime value) { return toUtilCalendar(value.toDate()); }

    public static Calendar toUtilCalendar(MutableDateTime value) { return toUtilCalendar(value.toDate()); }

    public static Calendar toUtilCalendar(org.joda.time.Instant value) { return toUtilCalendar(value.toDate()); }

    public static java.sql.Date toSqlDate(DateTime value) { return toSqlDate(value.toDate()); }

    public static java.sql.Date toSqlDate(org.joda.time.LocalDate value) { return toSqlDate(value.toDate()); }

    public static java.sql.Date toSqlDate(org.joda.time.LocalDateTime value) { return toSqlDate(value.toDate()); }

    public static java.sql.Date toSqlDate(MutableDateTime value) { return toSqlDate(value.getMillis()); }

    public static java.sql.Date toSqlDate(org.joda.time.Instant value) { return toSqlDate(value.getMillis()); }

    public static Time toSqlTime(DateTime value) { return new Time(value.getMillis()); }

    public static Time toSqlTime(org.joda.time.LocalTime value) { return toSqlTime(value.toDateTimeToday()); }

    public static Time toSqlTime(org.joda.time.LocalDateTime value) { return toSqlTime(value.toDate()); }

    public static Time toSqlTime(MutableDateTime value) { return new Time(value.getMillis()); }

    public static Time toSqlTime(org.joda.time.Instant value) { return new Time(value.getMillis()); }

    public static Timestamp toSqlTimestamp(DateTime value) { return new Timestamp(value.getMillis()); }

    public static Timestamp toSqlTimestamp(org.joda.time.LocalDate value) { return toSqlTimestamp(value.toDate()); }

    public static Timestamp toSqlTimestamp(org.joda.time.LocalDateTime value) { return toSqlTimestamp(value.toDate()); }

    public static Timestamp toSqlTimestamp(MutableDateTime value) { return new Timestamp(value.getMillis()); }

    public static Timestamp toSqlTimestamp(org.joda.time.Instant value) { return new Timestamp(value.getMillis()); }

    public static DateTime toJodaDateTime(YearMonth value) { return toJodaDateTime(toJdk8LocalDate(value)); }

    public static DateTime toJodaDateTime(LocalDate value) { return toJodaDateTime(value.atStartOfDay()); }

    public static DateTime toJodaDateTime(LocalDateTime value) { return toJodaDateTime(value.atZone(systemDefault())); }

    public static DateTime toJodaDateTime(OffsetDateTime value) { return toJodaDateTime(value.toInstant()); }

    public static DateTime toJodaDateTime(ZonedDateTime value) { return toJodaDateTime(value.toInstant()); }

    public static DateTime toJodaDateTime(Instant value) { return toJodaDateTime(value.toEpochMilli()); }

    public static DateTime toJodaDateTime(Date value) { return toJodaDateTime(value.getTime()); }

    public static DateTime toJodaDateTime(Calendar value) { return toJodaDateTime(value.getTimeInMillis()); }

    public static DateTime toJodaDateTime(java.sql.Date value) { return toJodaDateTime(value.getTime()); }

    public static DateTime toJodaDateTime(Timestamp value) { return toJodaDateTime(value.getTime()); }

    public static DateTime toJodaDateTime(org.joda.time.LocalDate value) { return toJodaDateTime(value.toDate()); }

    public static DateTime toJodaDateTime(org.joda.time.LocalDateTime value) { return toJodaDateTime(value.toDate()); }

    public static DateTime toJodaDateTime(MutableDateTime value) { return toJodaDateTime(value.getMillis()); }

    public static DateTime toJodaDateTime(org.joda.time.Instant value) { return toJodaDateTime(value.getMillis()); }

    public static DateTime toJodaDateTime(Long value) { return new DateTime(value); }

    public static DateTime toJodaDateTime(long value) { return new DateTime(value); }

    public static org.joda.time.LocalDate toJodaLocalDate(YearMonth value) {
        return new org.joda.time.LocalDate(value.getYear(), value.getMonthValue(), 1);
    }

    public static org.joda.time.LocalDate toJodaLocalDate(LocalDate value) {
        return new org.joda.time.LocalDate(value.getYear(), value.getMonthValue(), value.getDayOfMonth());
    }

    public static org.joda.time.LocalDate toJodaLocalDate(LocalDateTime value) { return toJodaLocalDate(value.toLocalDate()); }

    public static org.joda.time.LocalDate toJodaLocalDate(OffsetDateTime value) { return toJodaLocalDate(value.toLocalDate()); }

    public static org.joda.time.LocalDate toJodaLocalDate(ZonedDateTime value) { return toJodaLocalDate(value.toLocalDate()); }

    public static org.joda.time.LocalDate toJodaLocalDate(Instant value) { return toJodaLocalDate(value.toEpochMilli()); }

    public static org.joda.time.LocalDate toJodaLocalDate(Date value) {
        return org.joda.time.LocalDate.fromDateFields(value);
    }

    public static org.joda.time.LocalDate toJodaLocalDate(Calendar value) {
        return org.joda.time.LocalDate.fromCalendarFields(value);
    }

    public static org.joda.time.LocalDate toJodaLocalDate(java.sql.Date value) {
        return org.joda.time.LocalDate.fromDateFields(value);
    }

    public static org.joda.time.LocalDate toJodaLocalDate(Timestamp value) {
        return org.joda.time.LocalDate.fromDateFields(value);
    }

    public static org.joda.time.LocalDate toJodaLocalDate(DateTime value) {
        return new org.joda.time.LocalDate(value);
    }

    public static org.joda.time.LocalDate toJodaLocalDate(org.joda.time.LocalDateTime value) {
        return new org.joda.time.LocalDate(value);
    }

    public static org.joda.time.LocalDate toJodaLocalDate(MutableDateTime value) {
        return new org.joda.time.LocalDate(value);
    }

    public static org.joda.time.LocalDate toJodaLocalDate(org.joda.time.Instant value) {
        return new org.joda.time.LocalDate(value);
    }

    public static org.joda.time.LocalDate toJodaLocalDate(Long value) {
        return new org.joda.time.LocalDate(value);
    }

    public static org.joda.time.LocalDate toJodaLocalDate(long value) {
        return new org.joda.time.LocalDate(value);
    }

    public static org.joda.time.LocalTime toJodaLocalTime(LocalTime value) {
        return toJodaLocalTime(Time.valueOf(value));
    }

    public static org.joda.time.LocalTime toJodaLocalTime(LocalDateTime value) {
        return toJodaLocalTime(value.atZone(systemDefault()));
    }

    public static org.joda.time.LocalTime toJodaLocalTime(OffsetDateTime value) { return toJodaLocalTime(value.toInstant()); }

    public static org.joda.time.LocalTime toJodaLocalTime(ZonedDateTime value) { return toJodaLocalTime(value.toInstant()); }

    public static org.joda.time.LocalTime toJodaLocalTime(Instant value) {
        return org.joda.time.LocalTime.fromDateFields(Date.from(value));
    }

    public static org.joda.time.LocalTime toJodaLocalTime(Date value) {
        return org.joda.time.LocalTime.fromDateFields(value);
    }

    public static org.joda.time.LocalTime toJodaLocalTime(Calendar value) {
        return org.joda.time.LocalTime.fromCalendarFields(value);
    }

    public static org.joda.time.LocalTime toJodaLocalTime(Time value) { return new org.joda.time.LocalTime(value); }

    public static org.joda.time.LocalTime toJodaLocalTime(Timestamp value) {
        return org.joda.time.LocalTime.fromDateFields(value);
    }

    public static org.joda.time.LocalTime toJodaLocalTime(DateTime value) { return new org.joda.time.LocalTime(value); }

    public static org.joda.time.LocalTime toJodaLocalTime(org.joda.time.LocalDateTime value) {
        return new org.joda.time.LocalTime(value);
    }

    public static org.joda.time.LocalTime toJodaLocalTime(MutableDateTime value) {
        return new org.joda.time.LocalTime(value);
    }

    public static org.joda.time.LocalTime toJodaLocalTime(org.joda.time.Instant value) {
        return new org.joda.time.LocalTime(value);
    }

    public static org.joda.time.LocalTime toJodaLocalTime(Long value) {
        return new org.joda.time.LocalTime(value);
    }

    public static org.joda.time.LocalTime toJodaLocalTime(long value) {
        return new org.joda.time.LocalTime(value);
    }

    public static org.joda.time.LocalDateTime toJodaLocalDateTime(YearMonth value) {
        return toJodaLocalDateTime(toJdk8LocalDate(value));
    }

    public static org.joda.time.LocalDateTime toJodaLocalDateTime(LocalDate value) {
        return new org.joda.time.LocalDateTime(java.sql.Date.valueOf(value));
    }

    public static org.joda.time.LocalDateTime toJodaLocalDateTime(LocalDateTime value) {
        return toJodaLocalDateTime(value.atZone(systemDefault()));
    }

    public static org.joda.time.LocalDateTime toJodaLocalDateTime(OffsetDateTime value) {
        return toJodaLocalDateTime(value.toInstant());
    }

    public static org.joda.time.LocalDateTime toJodaLocalDateTime(ZonedDateTime value) {
        return toJodaLocalDateTime(value.toInstant());
    }

    public static org.joda.time.LocalDateTime toJodaLocalDateTime(Instant value) {
        return toJodaLocalDateTime(value.toEpochMilli());
    }

    public static org.joda.time.LocalDateTime toJodaLocalDateTime(Date value) {
        return org.joda.time.LocalDateTime.fromDateFields(value);
    }

    public static org.joda.time.LocalDateTime toJodaLocalDateTime(Calendar value) {
        return org.joda.time.LocalDateTime.fromCalendarFields(value);
    }

    public static org.joda.time.LocalDateTime toJodaLocalDateTime(java.sql.Date value) {
        return new org.joda.time.LocalDateTime(value.getTime());
    }

    public static org.joda.time.LocalDateTime toJodaLocalDateTime(Timestamp value) {
        return new org.joda.time.LocalDateTime(value.getTime());
    }

    public static org.joda.time.LocalDateTime toJodaLocalDateTime(DateTime value) {
        return new org.joda.time.LocalDateTime(value);
    }

    public static org.joda.time.LocalDateTime toJodaLocalDateTime(org.joda.time.LocalDate value) {
        return new org.joda.time.LocalDateTime(value.getYear(), value.getMonthOfYear(), value.getDayOfMonth(), 0, 0);
    }

    public static org.joda.time.LocalDateTime toJodaLocalDateTime(MutableDateTime value) {
        return new org.joda.time.LocalDateTime(value.getMillis());
    }

    public static org.joda.time.LocalDateTime toJodaLocalDateTime(org.joda.time.Instant value) {
        return new org.joda.time.LocalDateTime(value.getMillis());
    }

    public static org.joda.time.LocalDateTime toJodaLocalDateTime(Long value) {
        return new org.joda.time.LocalDateTime(value);
    }

    public static org.joda.time.LocalDateTime toJodaLocalDateTime(long value) {
        return new org.joda.time.LocalDateTime(value);
    }

    public static MutableDateTime toJodaMutableDateTime(YearMonth value) {
        return toJodaMutableDateTime(toJdk8LocalDate(value));
    }

    public static MutableDateTime toJodaMutableDateTime(LocalDate value) { return toJodaMutableDateTime(value.atStartOfDay()); }

    public static MutableDateTime toJodaMutableDateTime(LocalDateTime value) {
        return toJodaMutableDateTime(value.atZone(systemDefault()));
    }

    public static MutableDateTime toJodaMutableDateTime(OffsetDateTime value) { return toJodaMutableDateTime(value.toInstant()); }

    public static MutableDateTime toJodaMutableDateTime(ZonedDateTime value) { return toJodaMutableDateTime(value.toInstant()); }

    public static MutableDateTime toJodaMutableDateTime(Instant value) { return new MutableDateTime(value.toEpochMilli()); }

    public static MutableDateTime toJodaMutableDateTime(Date value) { return new MutableDateTime(value.getTime()); }

    public static MutableDateTime toJodaMutableDateTime(Calendar value) { return new MutableDateTime(value.getTimeInMillis()); }

    public static MutableDateTime toJodaMutableDateTime(java.sql.Date value) { return new MutableDateTime(value.getTime()); }

    public static MutableDateTime toJodaMutableDateTime(Timestamp value) { return new MutableDateTime(value.getTime()); }

    public static MutableDateTime toJodaMutableDateTime(DateTime value) { return new MutableDateTime(value.getMillis()); }

    public static MutableDateTime toJodaMutableDateTime(org.joda.time.LocalDate value) {
        return new MutableDateTime(value.toDate());
    }

    public static MutableDateTime toJodaMutableDateTime(org.joda.time.LocalDateTime value) {
        return new MutableDateTime(value.toDate());
    }

    public static MutableDateTime toJodaMutableDateTime(org.joda.time.Instant value) { return new MutableDateTime(value); }

    public static MutableDateTime toJodaMutableDateTime(Long value) { return new MutableDateTime(value); }

    public static MutableDateTime toJodaMutableDateTime(long value) { return new MutableDateTime(value); }

    public static org.joda.time.Instant toJodaInstant(YearMonth value) { return toJodaInstant(toJdk8LocalDate(value)); }

    public static org.joda.time.Instant toJodaInstant(LocalDate value) { return toJodaInstant(value.atStartOfDay()); }

    public static org.joda.time.Instant toJodaInstant(LocalDateTime value) {
        return toJodaInstant(value.atZone(systemDefault()));
    }

    public static org.joda.time.Instant toJodaInstant(OffsetDateTime value) { return toJodaInstant(value.toInstant()); }

    public static org.joda.time.Instant toJodaInstant(ZonedDateTime value) { return toJodaInstant(value.toInstant()); }

    public static org.joda.time.Instant toJodaInstant(Instant value) { return new org.joda.time.Instant(value.toEpochMilli()); }

    public static org.joda.time.Instant toJodaInstant(Date value) { return new org.joda.time.Instant(value.getTime()); }

    public static org.joda.time.Instant toJodaInstant(Calendar value) { return new org.joda.time.Instant(value.getTimeInMillis()); }

    public static org.joda.time.Instant toJodaInstant(java.sql.Date value) { return new org.joda.time.Instant(value.getTime()); }

    public static org.joda.time.Instant toJodaInstant(Timestamp value) { return new org.joda.time.Instant(value.getTime()); }

    public static org.joda.time.Instant toJodaInstant(DateTime value) { return new org.joda.time.Instant(value.getMillis()); }

    public static org.joda.time.Instant toJodaInstant(org.joda.time.LocalDate value) { return toJodaInstant(value.toDate()); }

    public static org.joda.time.Instant toJodaInstant(org.joda.time.LocalDateTime value) { return toJodaInstant(value.toDate()); }

    public static org.joda.time.Instant toJodaInstant(MutableDateTime value) { return new org.joda.time.Instant(value); }

    public static org.joda.time.Instant toJodaInstant(Long value) { return new org.joda.time.Instant(value); }

    public static org.joda.time.Instant toJodaInstant(long value) { return new org.joda.time.Instant(value); }

    public static YearMonth toJdk8YearMonth(DateTime value) {
        return YearMonth.of(value.getYear(), value.getMonthOfYear());
    }

    public static YearMonth toJdk8YearMonth(org.joda.time.LocalDate value) {
        return YearMonth.of(value.getYear(), value.getMonthOfYear());
    }

    public static YearMonth toJdk8YearMonth(org.joda.time.LocalDateTime value) {
        return YearMonth.of(value.getYear(), value.getMonthOfYear());
    }

    public static YearMonth toJdk8YearMonth(MutableDateTime value) {
        return YearMonth.of(value.getYear(), value.getMonthOfYear());
    }

    public static YearMonth toJdk8YearMonth(org.joda.time.Instant value) {
        return toJdk8YearMonth(toJdk8LocalDate(value));
    }

    public static long toPrimitiveLong(DateTime value) { return value.getMillis(); }

    public static long toPrimitiveLong(org.joda.time.LocalDate value) { return value.toDate().getTime(); }

    public static long toPrimitiveLong(org.joda.time.LocalDateTime value) { return value.toDate().getTime(); }

    public static long toPrimitiveLong(MutableDateTime value) { return value.getMillis(); }

    public static long toPrimitiveLong(org.joda.time.Instant value) { return value.getMillis(); }

    public static Long toLong(DateTime value) { return value.getMillis(); }

    public static Long toLong(org.joda.time.LocalDate value) { return value.toDate().getTime(); }

    public static Long toLong(org.joda.time.LocalDateTime value) { return value.toDate().getTime(); }

    public static Long toLong(MutableDateTime value) { return value.getMillis(); }

    public static Long toLong(org.joda.time.Instant value) { return value.getMillis(); }
}

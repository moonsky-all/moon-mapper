package com.moonsky.mapper.util;


import java.sql.Time;
import java.sql.Timestamp;
import java.time.*;
import java.util.Calendar;
import java.util.Date;

import static java.time.Instant.ofEpochMilli;
import static java.time.ZoneId.systemDefault;

/**
 * JDK 自带各种类型日期转换
 *
 * @author benshaoye
 */
public abstract class DateConvert {

    DateConvert() {
        throw new AssertionError("No " + getClass().getCanonicalName() + " instances for you!");
    }

    public static LocalDate toJdk8LocalDate(YearMonth value) {
        return LocalDate.of(value.getYear(), value.getMonthValue(), 1);
    }

    public static LocalDate toJdk8LocalDate(LocalDateTime value) {return value.toLocalDate();}

    public static LocalDate toJdk8LocalDate(OffsetDateTime value) {return value.toLocalDate();}

    public static LocalDate toJdk8LocalDate(ZonedDateTime value) {return value.toLocalDate();}

    public static LocalDate toJdk8LocalDate(Instant value) {return value.atZone(systemDefault()).toLocalDate();}

    public static LocalDate toJdk8LocalDate(Date value) {return toJdk8LocalDate(value.getTime());}

    public static LocalDate toJdk8LocalDate(Calendar value) {
        return LocalDate.of(value.get(Calendar.YEAR), value.get(Calendar.MONTH) + 1, value.get(Calendar.DAY_OF_MONTH));
    }

    public static LocalDate toJdk8LocalDate(java.sql.Date value) {return value.toLocalDate();}

    public static LocalDate toJdk8LocalDate(Timestamp value) {return toJdk8LocalDate(value.getTime());}

    public static LocalDate toJdk8LocalDate(Long value) {return toJdk8LocalDateTime(value).toLocalDate();}

    public static LocalDate toJdk8LocalDate(long value) {return toJdk8LocalDateTime(value).toLocalDate();}

    public static LocalTime toJdk8LocalTime(LocalDateTime value) {return value.toLocalTime();}

    public static LocalTime toJdk8LocalTime(OffsetDateTime value) {return value.toLocalTime();}

    public static LocalTime toJdk8LocalTime(ZonedDateTime value) {return value.toLocalTime();}

    public static LocalTime toJdk8LocalTime(Instant value) {return toJdk8LocalDateTime(value).toLocalTime();}

    public static LocalTime toJdk8LocalTime(Date value) {return toJdk8LocalDateTime(value).toLocalTime();}

    public static LocalTime toJdk8LocalTime(Calendar value) {return toJdk8LocalTime(value.getTimeInMillis());}

    public static LocalTime toJdk8LocalTime(Time value) {return value.toLocalTime();}

    public static LocalTime toJdk8LocalTime(Timestamp value) {return value.toLocalDateTime().toLocalTime();}

    public static LocalTime toJdk8LocalTime(Long value) {return toJdk8LocalDateTime(value).toLocalTime();}

    public static LocalTime toJdk8LocalTime(long value) {return toJdk8LocalDateTime(value).toLocalTime();}

    public static LocalDateTime toJdk8LocalDateTime(YearMonth value) {return toJdk8LocalDate(value).atStartOfDay();}

    public static LocalDateTime toJdk8LocalDateTime(LocalDate value) {return value.atStartOfDay();}

    public static LocalDateTime toJdk8LocalDateTime(OffsetDateTime value) {return toJdk8LocalDateTime(value.toInstant());}

    public static LocalDateTime toJdk8LocalDateTime(ZonedDateTime value) {return toJdk8LocalDateTime(value.toInstant());}

    public static LocalDateTime toJdk8LocalDateTime(Instant value) {
        return LocalDateTime.ofInstant(value, systemDefault());
    }

    public static LocalDateTime toJdk8LocalDateTime(Date value) {return toJdk8LocalDateTime(value.getTime());}

    public static LocalDateTime toJdk8LocalDateTime(Calendar value) {return toJdk8LocalDateTime(value.getTimeInMillis());}

    public static LocalDateTime toJdk8LocalDateTime(java.sql.Date value) {return toJdk8LocalDateTime(value.getTime());}

    public static LocalDateTime toJdk8LocalDateTime(Timestamp value) {return value.toLocalDateTime();}

    public static LocalDateTime toJdk8LocalDateTime(Long value) {return toJdk8LocalDateTime(ofEpochMilli(value));}

    public static LocalDateTime toJdk8LocalDateTime(long value) {return toJdk8LocalDateTime(ofEpochMilli(value));}

    public static OffsetDateTime toJdk8OffsetDateTime(YearMonth value) {
        return toJdk8OffsetDateTime(toJdk8LocalDate(value));
    }

    public static OffsetDateTime toJdk8OffsetDateTime(LocalDate value) {return toJdk8OffsetDateTime(value.atStartOfDay());}

    public static OffsetDateTime toJdk8OffsetDateTime(LocalDateTime value) {
        return value.atZone(systemDefault()).toOffsetDateTime();
    }

    public static OffsetDateTime toJdk8OffsetDateTime(ZonedDateTime value) {return value.toOffsetDateTime();}

    public static OffsetDateTime toJdk8OffsetDateTime(Instant value) {
        return toJdk8OffsetDateTime(LocalDateTime.ofInstant(value, systemDefault()));
    }

    public static OffsetDateTime toJdk8OffsetDateTime(Date value) {return toJdk8OffsetDateTime(value.toInstant());}

    public static OffsetDateTime toJdk8OffsetDateTime(Calendar value) {return toJdk8OffsetDateTime(value.toInstant());}

    public static OffsetDateTime toJdk8OffsetDateTime(java.sql.Date value) {return toJdk8OffsetDateTime(value.getTime());}

    public static OffsetDateTime toJdk8OffsetDateTime(Timestamp value) {return toJdk8OffsetDateTime(value.toInstant());}

    public static OffsetDateTime toJdk8OffsetDateTime(Long value) {return toJdk8OffsetDateTime(ofEpochMilli(value));}

    public static OffsetDateTime toJdk8OffsetDateTime(long value) {return toJdk8OffsetDateTime(ofEpochMilli(value));}

    public static OffsetTime toJdk8OffsetTime(LocalDateTime value) {
        return toJdk8OffsetTime(value.atZone(systemDefault()));
    }

    public static OffsetTime toJdk8OffsetTime(ZonedDateTime value) {return value.toOffsetDateTime().toOffsetTime();}

    public static OffsetTime toJdk8OffsetTime(Instant value) {
        return toJdk8OffsetTime(value.atZone(systemDefault()));
    }

    public static OffsetTime toJdk8OffsetTime(Date value) {return toJdk8OffsetTime(value.toInstant());}

    public static OffsetTime toJdk8OffsetTime(Calendar value) {return toJdk8OffsetTime(value.toInstant());}

    public static OffsetTime toJdk8OffsetTime(Timestamp value) {return toJdk8OffsetTime(value.toInstant());}

    public static OffsetTime toJdk8OffsetTime(Long value) {return toJdk8OffsetTime(ofEpochMilli(value));}

    public static OffsetTime toJdk8OffsetTime(long value) {return toJdk8OffsetTime(ofEpochMilli(value));}

    public static ZonedDateTime toJdk8ZonedDateTime(YearMonth value) {return toJdk8ZonedDateTime(toJdk8LocalDate(value));}

    public static ZonedDateTime toJdk8ZonedDateTime(LocalDate value) {return toJdk8ZonedDateTime(value.atStartOfDay());}

    public static ZonedDateTime toJdk8ZonedDateTime(LocalDateTime value) {return value.atZone(systemDefault());}

    public static ZonedDateTime toJdk8ZonedDateTime(OffsetDateTime value) {return ZonedDateTime.from(value);}

    public static ZonedDateTime toJdk8ZonedDateTime(Instant value) {return value.atZone(systemDefault());}

    public static ZonedDateTime toJdk8ZonedDateTime(Date value) {return value.toInstant().atZone(systemDefault());}

    public static ZonedDateTime toJdk8ZonedDateTime(Calendar value) {
        return value.toInstant().atZone(systemDefault());
    }

    public static ZonedDateTime toJdk8ZonedDateTime(java.sql.Date value) {
        return toJdk8ZonedDateTime(value.getTime());
    }

    public static ZonedDateTime toJdk8ZonedDateTime(Timestamp value) {
        return value.toInstant().atZone(systemDefault());
    }

    public static ZonedDateTime toJdk8ZonedDateTime(Long value) {return toJdk8ZonedDateTime(ofEpochMilli(value));}

    public static ZonedDateTime toJdk8ZonedDateTime(long value) {return toJdk8ZonedDateTime(ofEpochMilli(value));}

    public static Instant toJdk8Instant(YearMonth value) {return toJdk8Instant(toJdk8LocalDate(value));}

    public static Instant toJdk8Instant(LocalDate value) {return toJdk8Instant(value.atStartOfDay());}

    public static Instant toJdk8Instant(LocalDateTime value) {return toJdk8Instant(value.atZone(systemDefault()));}

    public static Instant toJdk8Instant(OffsetDateTime value) {return value.toInstant();}

    public static Instant toJdk8Instant(ZonedDateTime value) {return value.toInstant();}

    public static Instant toJdk8Instant(Date value) {return value.toInstant();}

    public static Instant toJdk8Instant(Calendar value) {return value.toInstant();}

    public static Instant toJdk8Instant(java.sql.Date value) {return Instant.ofEpochMilli(value.getTime());}

    public static Instant toJdk8Instant(Timestamp value) {return value.toInstant();}

    public static Instant toJdk8Instant(Long value) {return ofEpochMilli(value);}

    public static Instant toJdk8Instant(long value) {return ofEpochMilli(value);}

    public static Date toUtilDate(YearMonth value) {return toUtilDate(toJdk8LocalDate(value));}

    public static Date toUtilDate(LocalDate value) {return toUtilDate(value.atStartOfDay());}

    public static Date toUtilDate(LocalDateTime value) {return toUtilDate(value.atZone(systemDefault()));}

    public static Date toUtilDate(OffsetDateTime value) {return toUtilDate(value.toInstant());}

    public static Date toUtilDate(ZonedDateTime value) {return toUtilDate(value.toInstant());}

    public static Date toUtilDate(Instant value) {return Date.from(value);}

    public static Date toUtilDate(Calendar value) {return new Date(value.getTimeInMillis());}

    public static Date toUtilDate(java.sql.Date value) {return new Date(value.getTime());}

    public static Date toUtilDate(Timestamp value) {return new Date(value.getTime());}

    public static Date toUtilDate(Long value) {return new Date(value);}

    public static Date toUtilDate(long value) {return new Date(value);}

    public static Calendar toUtilCalendar(YearMonth value) {return toUtilCalendar(toJdk8LocalDate(value));}

    public static Calendar toUtilCalendar(LocalDate value) {return toUtilCalendar(value.atStartOfDay());}

    public static Calendar toUtilCalendar(LocalDateTime value) {return toUtilCalendar(value.atZone(systemDefault()));}

    public static Calendar toUtilCalendar(OffsetDateTime value) {return toUtilCalendar(value.toInstant());}

    public static Calendar toUtilCalendar(ZonedDateTime value) {return toUtilCalendar(value.toInstant());}

    public static Calendar toUtilCalendar(Instant value) {return toUtilCalendar(value.toEpochMilli());}

    public static Calendar toUtilCalendar(Date value) {return toUtilCalendar(value.getTime());}

    public static Calendar toUtilCalendar(java.sql.Date value) {return toUtilCalendar(value.getTime());}

    public static Calendar toUtilCalendar(Timestamp value) {return toUtilCalendar(value.getTime());}

    public static Calendar toUtilCalendar(Long value) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(value);
        return calendar;
    }

    public static Calendar toUtilCalendar(long value) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(value);
        return calendar;
    }

    public static java.sql.Date toSqlDate(YearMonth value) {return java.sql.Date.valueOf(toJdk8LocalDate(value));}

    public static java.sql.Date toSqlDate(LocalDate value) {return java.sql.Date.valueOf(value);}

    public static java.sql.Date toSqlDate(LocalDateTime value) {return toSqlDate(value.toLocalDate());}

    public static java.sql.Date toSqlDate(OffsetDateTime value) {return toSqlDate(value.toLocalDate());}

    public static java.sql.Date toSqlDate(ZonedDateTime value) {return toSqlDate(value.toLocalDate());}

    public static java.sql.Date toSqlDate(Instant value) {return toSqlDate(value.toEpochMilli());}

    public static java.sql.Date toSqlDate(Date value) {return new java.sql.Date(value.getTime());}

    public static java.sql.Date toSqlDate(Calendar value) {return new java.sql.Date(value.getTimeInMillis());}

    public static java.sql.Date toSqlDate(Timestamp value) {return new java.sql.Date(value.getTime());}

    public static java.sql.Date toSqlDate(Long value) {return new java.sql.Date(value);}

    public static java.sql.Date toSqlDate(long value) {return new java.sql.Date(value);}

    public static Time toSqlTime(LocalTime value) {return Time.valueOf(value);}

    public static Time toSqlTime(LocalDateTime value) {return Time.valueOf(value.toLocalTime());}

    public static Time toSqlTime(OffsetDateTime value) {return Time.valueOf(value.toLocalTime());}

    public static Time toSqlTime(ZonedDateTime value) {return Time.valueOf(value.toLocalTime());}

    public static Time toSqlTime(Instant value) {return new Time(value.toEpochMilli());}

    public static Time toSqlTime(Date value) {return new Time(value.getTime());}

    public static Time toSqlTime(Calendar value) {return new Time(value.getTimeInMillis());}

    public static Time toSqlTime(Timestamp value) {return new Time(value.getTime());}

    public static Time toSqlTime(Long value) {return new Time(value);}

    public static Time toSqlTime(long value) {return new Time(value);}

    public static Timestamp toSqlTimestamp(YearMonth value) {return toSqlTimestamp(toJdk8LocalDate(value));}

    public static Timestamp toSqlTimestamp(LocalDate value) {return toSqlTimestamp(value.atStartOfDay());}

    public static Timestamp toSqlTimestamp(LocalDateTime value) {return Timestamp.valueOf(value);}

    public static Timestamp toSqlTimestamp(OffsetDateTime value) {return toSqlTimestamp(value.toLocalDateTime());}

    public static Timestamp toSqlTimestamp(ZonedDateTime value) {return toSqlTimestamp(value.toLocalDateTime());}

    public static Timestamp toSqlTimestamp(Instant value) {return new Timestamp(value.toEpochMilli());}

    public static Timestamp toSqlTimestamp(Date value) {return new Timestamp(value.getTime());}

    public static Timestamp toSqlTimestamp(Calendar value) {return new Timestamp(value.getTimeInMillis());}

    public static Timestamp toSqlTimestamp(java.sql.Date value) {return new Timestamp(value.getTime());}

    public static Timestamp toSqlTimestamp(Long value) {return new Timestamp(value);}

    public static Timestamp toSqlTimestamp(long value) {return new Timestamp(value);}

    public static MonthDay toJdk8MonthDay(LocalDate value) {
        return MonthDay.of(value.getMonthValue(), value.getDayOfMonth());
    }

    public static MonthDay toJdk8MonthDay(LocalDateTime value) {
        return MonthDay.of(value.getMonthValue(), value.getDayOfMonth());
    }

    public static MonthDay toJdk8MonthDay(OffsetDateTime value) {
        return MonthDay.of(value.getMonthValue(), value.getDayOfMonth());
    }

    public static MonthDay toJdk8MonthDay(ZonedDateTime value) {
        return MonthDay.of(value.getMonthValue(), value.getDayOfMonth());
    }

    public static MonthDay toJdk8MonthDay(Instant value) {
        return toJdk8MonthDay(value.atZone(systemDefault()));
    }

    public static MonthDay toJdk8MonthDay(Date value) {
        return toJdk8MonthDay(value.toInstant());
    }

    public static MonthDay toJdk8MonthDay(Calendar value) {
        return toJdk8MonthDay(value.toInstant());
    }

    public static MonthDay toJdk8MonthDay(java.sql.Date value) {
        return toJdk8MonthDay(value.toLocalDate());
    }

    public static MonthDay toJdk8MonthDay(Timestamp value) {
        return toJdk8MonthDay(value.toLocalDateTime());
    }

    public static MonthDay toJdk8MonthDay(Long value) {
        return toJdk8MonthDay(toJdk8LocalDateTime(value));
    }

    public static MonthDay toJdk8MonthDay(long value) {
        return toJdk8MonthDay(toJdk8LocalDateTime(value));
    }

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

    public static YearMonth toJdk8YearMonth(Long value) {
        return toJdk8YearMonth(toJdk8LocalDate(value));
    }

    public static YearMonth toJdk8YearMonth(long value) {
        return toJdk8YearMonth(toJdk8LocalDate(value));
    }

    public static long toPrimitiveLong(YearMonth value) {return toPrimitiveLong(toJdk8LocalDate(value));}

    public static long toPrimitiveLong(LocalDate value) {return toPrimitiveLong(value.atStartOfDay());}

    public static long toPrimitiveLong(LocalDateTime value) {return toPrimitiveLong(value.atZone(systemDefault()));}

    public static long toPrimitiveLong(OffsetDateTime value) {return toPrimitiveLong(value.toInstant());}

    public static long toPrimitiveLong(ZonedDateTime value) {return toPrimitiveLong(value.toInstant());}

    public static long toPrimitiveLong(Instant value) {return value.toEpochMilli();}

    public static long toPrimitiveLong(Date value) {return value.getTime();}

    public static long toPrimitiveLong(Calendar value) {return value.getTimeInMillis();}

    public static long toPrimitiveLong(java.sql.Date value) {return value.getTime();}

    public static long toPrimitiveLong(Timestamp value) {return value.getTime();}

    public static Long toLong(YearMonth value) {return toLong(toJdk8LocalDate(value));}

    public static Long toLong(LocalDate value) {return toLong(value.atStartOfDay());}

    public static Long toLong(LocalDateTime value) {return toLong(value.atZone(systemDefault()));}

    public static Long toLong(OffsetDateTime value) {return toLong(value.toInstant());}

    public static Long toLong(ZonedDateTime value) {return toLong(value.toInstant());}

    public static Long toLong(Instant value) {return value.toEpochMilli();}

    public static Long toLong(Date value) {return value.getTime();}

    public static Long toLong(Calendar value) {return value.getTimeInMillis();}

    public static Long toLong(java.sql.Date value) {return value.getTime();}

    public static Long toLong(Timestamp value) {return value.getTime();}

    public static Year toJdk8Year(LocalDate value) {return Year.of(value.getYear());}

    public static Year toJdk8Year(LocalDateTime value) {return Year.of(value.getYear());}

    public static Year toJdk8Year(OffsetDateTime value) {return Year.of(value.getYear());}

    public static Year toJdk8Year(ZonedDateTime value) {return Year.of(value.getYear());}

    public static Year toJdk8Year(Instant value) {return Year.from(value.atZone(systemDefault()));}

    public static Year toJdk8Year(Date value) {return toJdk8Year(toUtilCalendar(value));}

    public static Year toJdk8Year(Calendar value) {return Year.of(value.get(Calendar.YEAR));}

    public static Year toJdk8Year(java.sql.Date value) {return Year.from(value.toLocalDate());}

    public static Year toJdk8Year(Timestamp value) {return Year.from(value.toLocalDateTime());}

    public static Year toJdk8Year(YearMonth value) {return Year.of(value.getYear());}

    public static Year toJdk8Year(Long value) {return toJdk8Year(toUtilCalendar(value));}

    public static Year toJdk8Year(long value) {return toJdk8Year(toUtilCalendar(value));}

    public static Year toJdk8Year(Integer value) {return Year.of(value);}

    public static Year toJdk8Year(int value) {return Year.of(value);}

    public static Integer toYearNumber(Year value) {return value.getValue();}

    public static int toYearValue(Year value) {return value.getValue();}

    public static Month toJdk8Month(LocalDate value) {return value.getMonth();}

    public static Month toJdk8Month(LocalDateTime value) {return value.getMonth();}

    public static Month toJdk8Month(OffsetDateTime value) {return value.getMonth();}

    public static Month toJdk8Month(ZonedDateTime value) {return value.getMonth();}

    public static Month toJdk8Month(Instant value) {return Month.from(value.atZone(systemDefault()));}

    public static Month toJdk8Month(Date value) {return toJdk8Month(toUtilCalendar(value));}

    public static Month toJdk8Month(Calendar value) {return Month.of(value.get(Calendar.MONTH) + 1);}

    public static Month toJdk8Month(java.sql.Date value) {return value.toLocalDate().getMonth();}

    public static Month toJdk8Month(Timestamp value) {return value.toLocalDateTime().getMonth();}

    public static Month toJdk8Month(YearMonth value) {return value.getMonth();}

    public static Month toJdk8Month(MonthDay value) {return value.getMonth();}

    public static Month toJdk8Month(Long value) {return toJdk8Month(toUtilCalendar(value));}

    public static Month toJdk8Month(long value) {return toJdk8Month(toUtilCalendar(value));}

    public static Month toJdk8Month(Integer value) {return Month.of(value);}

    public static Month toJdk8Month(int value) {return Month.of(value);}

    public static Integer toMonthNumber(Month value) {return value.getValue();}

    public static int toMonthValue(Month value) {return value.getValue();}
}

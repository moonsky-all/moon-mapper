package com.moonsky.mapper.util;

import com.moonsky.mapper.TestSuperclass;
import org.junit.jupiter.api.Test;

import java.time.*;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author benshaoye
 */
class Joda1xConvertTestTest extends TestSuperclass {

    @Test
    void testPrintlnAll() {
        printlnConvertTestAll(Joda1xConvert.class);
    }

    @Test
    void testToJdk8LocalDate() {
        {
            startingOf("// LocalDate localDate = Joda1xConvert.toJdk8LocalDate(org.joda.time.LocalDate);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// LocalDate localDate = Joda1xConvert.toJdk8LocalDate(org.joda.time.LocalDateTime);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// LocalDate localDate = Joda1xConvert.toJdk8LocalDate(org.joda.time.DateTime);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// LocalDate localDate = Joda1xConvert.toJdk8LocalDate(org.joda.time.MutableDateTime);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// LocalDate localDate = Joda1xConvert.toJdk8LocalDate(org.joda.time.Instant);");
            final long var = System.currentTimeMillis();
        }
    }

    @Test
    void testToJodaMutableDateTime() {
        {
            startingOf("// MutableDateTime mutableDateTime = Joda1xConvert.toJodaMutableDateTime(long);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// MutableDateTime mutableDateTime = Joda1xConvert.toJodaMutableDateTime(java.lang.Long);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// MutableDateTime mutableDateTime = Joda1xConvert.toJodaMutableDateTime(org.joda.time.Instant);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// MutableDateTime mutableDateTime = Joda1xConvert.toJodaMutableDateTime(org.joda.time.LocalDateTime);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// MutableDateTime mutableDateTime = Joda1xConvert.toJodaMutableDateTime(java.time.YearMonth);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// MutableDateTime mutableDateTime = Joda1xConvert.toJodaMutableDateTime(java.time.LocalDate);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// MutableDateTime mutableDateTime = Joda1xConvert.toJodaMutableDateTime(java.time.LocalDateTime);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// MutableDateTime mutableDateTime = Joda1xConvert.toJodaMutableDateTime(java.time.OffsetDateTime);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// MutableDateTime mutableDateTime = Joda1xConvert.toJodaMutableDateTime(java.util.Calendar);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// MutableDateTime mutableDateTime = Joda1xConvert.toJodaMutableDateTime(java.util.Date);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// MutableDateTime mutableDateTime = Joda1xConvert.toJodaMutableDateTime(java.time.Instant);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// MutableDateTime mutableDateTime = Joda1xConvert.toJodaMutableDateTime(java.time.ZonedDateTime);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// MutableDateTime mutableDateTime = Joda1xConvert.toJodaMutableDateTime(org.joda.time.LocalDate);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// MutableDateTime mutableDateTime = Joda1xConvert.toJodaMutableDateTime(org.joda.time.DateTime);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// MutableDateTime mutableDateTime = Joda1xConvert.toJodaMutableDateTime(java.sql.Timestamp);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// MutableDateTime mutableDateTime = Joda1xConvert.toJodaMutableDateTime(java.sql.Date);");
            final long var = System.currentTimeMillis();
        }
    }

    @Test
    void testToJdk8MonthDay() {
        {
            startingOf("// MonthDay monthDay = Joda1xConvert.toJdk8MonthDay(org.joda.time.MutableDateTime);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// MonthDay monthDay = Joda1xConvert.toJdk8MonthDay(org.joda.time.DateTime);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// MonthDay monthDay = Joda1xConvert.toJdk8MonthDay(org.joda.time.Instant);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// MonthDay monthDay = Joda1xConvert.toJdk8MonthDay(org.joda.time.LocalDate);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// MonthDay monthDay = Joda1xConvert.toJdk8MonthDay(org.joda.time.LocalDateTime);");
            final long var = System.currentTimeMillis();
        }
    }

    @Test
    void testToUtilDate() {
        {
            startingOf("// Date date = Joda1xConvert.toUtilDate(org.joda.time.LocalDate);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Date date = Joda1xConvert.toUtilDate(org.joda.time.LocalDateTime);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Date date = Joda1xConvert.toUtilDate(org.joda.time.DateTime);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Date date = Joda1xConvert.toUtilDate(org.joda.time.MutableDateTime);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Date date = Joda1xConvert.toUtilDate(org.joda.time.Instant);");
            final long var = System.currentTimeMillis();
        }
    }

    @Test
    void testToLong() {
        {
            startingOf("// Long long = Joda1xConvert.toLong(org.joda.time.MutableDateTime);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Long long = Joda1xConvert.toLong(org.joda.time.LocalDateTime);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Long long = Joda1xConvert.toLong(org.joda.time.Instant);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Long long = Joda1xConvert.toLong(org.joda.time.LocalDate);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Long long = Joda1xConvert.toLong(org.joda.time.DateTime);");
            final long var = System.currentTimeMillis();
        }
    }

    @Test
    void testToPrimitiveLong() {
        {
            startingOf("// long long = Joda1xConvert.toPrimitiveLong(org.joda.time.Instant);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// long long = Joda1xConvert.toPrimitiveLong(org.joda.time.LocalDateTime);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// long long = Joda1xConvert.toPrimitiveLong(org.joda.time.LocalDate);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// long long = Joda1xConvert.toPrimitiveLong(org.joda.time.DateTime);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// long long = Joda1xConvert.toPrimitiveLong(org.joda.time.MutableDateTime);");
            final long var = System.currentTimeMillis();
        }
    }

    @Test
    void testToJodaLocalDate() {
        {
            startingOf("// LocalDate localDate = Joda1xConvert.toJodaLocalDate(org.joda.time.DateTime);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// LocalDate localDate = Joda1xConvert.toJodaLocalDate(java.sql.Timestamp);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// LocalDate localDate = Joda1xConvert.toJodaLocalDate(java.sql.Date);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// LocalDate localDate = Joda1xConvert.toJodaLocalDate(java.util.Calendar);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// LocalDate localDate = Joda1xConvert.toJodaLocalDate(java.util.Date);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// LocalDate localDate = Joda1xConvert.toJodaLocalDate(long);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// LocalDate localDate = Joda1xConvert.toJodaLocalDate(java.lang.Long);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// LocalDate localDate = Joda1xConvert.toJodaLocalDate(org.joda.time.MutableDateTime);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// LocalDate localDate = Joda1xConvert.toJodaLocalDate(org.joda.time.LocalDateTime);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// LocalDate localDate = Joda1xConvert.toJodaLocalDate(java.time.LocalDateTime);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// LocalDate localDate = Joda1xConvert.toJodaLocalDate(java.time.LocalDate);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// LocalDate localDate = Joda1xConvert.toJodaLocalDate(java.time.YearMonth);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// LocalDate localDate = Joda1xConvert.toJodaLocalDate(org.joda.time.Instant);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// LocalDate localDate = Joda1xConvert.toJodaLocalDate(java.time.Instant);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// LocalDate localDate = Joda1xConvert.toJodaLocalDate(java.time.ZonedDateTime);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// LocalDate localDate = Joda1xConvert.toJodaLocalDate(java.time.OffsetDateTime);");
            final long var = System.currentTimeMillis();
        }
    }

    @Test
    void testToJdk8LocalTime() {
        {
            startingOf("// LocalTime localTime = Joda1xConvert.toJdk8LocalTime(org.joda.time.DateTime);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// LocalTime localTime = Joda1xConvert.toJdk8LocalTime(org.joda.time.Instant);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// LocalTime localTime = Joda1xConvert.toJdk8LocalTime(org.joda.time.MutableDateTime);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// LocalTime localTime = Joda1xConvert.toJdk8LocalTime(org.joda.time.LocalDateTime);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// LocalTime localTime = Joda1xConvert.toJdk8LocalTime(org.joda.time.LocalTime);");
            final long var = System.currentTimeMillis();
        }
    }

    @Test
    void testToJodaInstant() {
        {
            startingOf("// Instant instant = Joda1xConvert.toJodaInstant(org.joda.time.LocalDate);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Instant instant = Joda1xConvert.toJodaInstant(org.joda.time.DateTime);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Instant instant = Joda1xConvert.toJodaInstant(java.sql.Timestamp);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Instant instant = Joda1xConvert.toJodaInstant(java.sql.Date);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Instant instant = Joda1xConvert.toJodaInstant(long);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Instant instant = Joda1xConvert.toJodaInstant(java.lang.Long);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Instant instant = Joda1xConvert.toJodaInstant(org.joda.time.MutableDateTime);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Instant instant = Joda1xConvert.toJodaInstant(org.joda.time.LocalDateTime);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Instant instant = Joda1xConvert.toJodaInstant(java.time.OffsetDateTime);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Instant instant = Joda1xConvert.toJodaInstant(java.time.LocalDateTime);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Instant instant = Joda1xConvert.toJodaInstant(java.time.LocalDate);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Instant instant = Joda1xConvert.toJodaInstant(java.time.YearMonth);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Instant instant = Joda1xConvert.toJodaInstant(java.util.Calendar);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Instant instant = Joda1xConvert.toJodaInstant(java.util.Date);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Instant instant = Joda1xConvert.toJodaInstant(java.time.Instant);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Instant instant = Joda1xConvert.toJodaInstant(java.time.ZonedDateTime);");
            final long var = System.currentTimeMillis();
        }
    }

    @Test
    void testToJdk8Instant() {
        {
            startingOf("// Instant instant = Joda1xConvert.toJdk8Instant(org.joda.time.DateTime);");
            org.joda.time.DateTime datetime = org.joda.time.DateTime.now();
            Instant instant = Joda1xConvert.toJdk8Instant(datetime);
            assertEquals(instant.toEpochMilli(), datetime.getMillis());
        }
        {
            startingOf("// Instant instant = Joda1xConvert.toJdk8Instant(org.joda.time.LocalDate);");
            org.joda.time.LocalDate datetime = org.joda.time.LocalDate.now();
            Instant instant = Joda1xConvert.toJdk8Instant(datetime);
            assertEquals(instant.toEpochMilli(), datetime.toDate().getTime());
        }
        {
            startingOf("// Instant instant = Joda1xConvert.toJdk8Instant(org.joda.time.MutableDateTime);");
            org.joda.time.MutableDateTime datetime = org.joda.time.MutableDateTime.now();
            Instant instant = Joda1xConvert.toJdk8Instant(datetime);
            assertEquals(instant.toEpochMilli(), datetime.getMillis());
        }
        {
            startingOf("// Instant instant = Joda1xConvert.toJdk8Instant(org.joda.time.Instant);");
            org.joda.time.Instant datetime = org.joda.time.Instant.now();
            Instant instant = Joda1xConvert.toJdk8Instant(datetime);
            assertEquals(instant.toEpochMilli(), datetime.getMillis());
        }
        {
            startingOf("// Instant instant = Joda1xConvert.toJdk8Instant(org.joda.time.LocalDateTime);");
            org.joda.time.LocalDateTime datetime = org.joda.time.LocalDateTime.now();
            Instant instant = Joda1xConvert.toJdk8Instant(datetime);
            assertEquals(instant.toEpochMilli(), datetime.toDate().getTime());
        }
    }

    @Test
    void testToJdk8Month() {
        {
            startingOf("// Month month = Joda1xConvert.toJdk8Month(org.joda.time.LocalDateTime);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Month month = Joda1xConvert.toJdk8Month(org.joda.time.LocalDate);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Month month = Joda1xConvert.toJdk8Month(org.joda.time.DateTime);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Month month = Joda1xConvert.toJdk8Month(org.joda.time.Instant);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Month month = Joda1xConvert.toJdk8Month(org.joda.time.MutableDateTime);");
            final long var = System.currentTimeMillis();
        }
    }

    @Test
    void testToJdk8LocalDateTime() {
        {
            startingOf("// LocalDateTime localDateTime = Joda1xConvert.toJdk8LocalDateTime(org.joda.time.Instant);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// LocalDateTime localDateTime = Joda1xConvert.toJdk8LocalDateTime(org.joda.time.MutableDateTime);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// LocalDateTime localDateTime = Joda1xConvert.toJdk8LocalDateTime(org.joda.time.LocalDateTime);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// LocalDateTime localDateTime = Joda1xConvert.toJdk8LocalDateTime(org.joda.time.LocalDate);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// LocalDateTime localDateTime = Joda1xConvert.toJdk8LocalDateTime(org.joda.time.DateTime);");
            final long var = System.currentTimeMillis();
        }
    }

    @Test
    void testToSqlDate() {
        {
            startingOf("// Date date = Joda1xConvert.toSqlDate(org.joda.time.MutableDateTime);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Date date = Joda1xConvert.toSqlDate(org.joda.time.LocalDateTime);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Date date = Joda1xConvert.toSqlDate(org.joda.time.Instant);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Date date = Joda1xConvert.toSqlDate(org.joda.time.LocalDate);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Date date = Joda1xConvert.toSqlDate(org.joda.time.DateTime);");
            final long var = System.currentTimeMillis();
        }
    }

    @Test
    void testToJdk8Year() {
        {
            startingOf("// Year year = Joda1xConvert.toJdk8Year(org.joda.time.DateTime);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Year year = Joda1xConvert.toJdk8Year(org.joda.time.LocalDate);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Year year = Joda1xConvert.toJdk8Year(org.joda.time.LocalDateTime);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Year year = Joda1xConvert.toJdk8Year(org.joda.time.MutableDateTime);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Year year = Joda1xConvert.toJdk8Year(org.joda.time.Instant);");
            final long var = System.currentTimeMillis();
        }
    }

    @Test
    void testToSqlTimestamp() {
        {
            startingOf("// Timestamp timestamp = Joda1xConvert.toSqlTimestamp(org.joda.time.LocalDate);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Timestamp timestamp = Joda1xConvert.toSqlTimestamp(org.joda.time.LocalDateTime);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Timestamp timestamp = Joda1xConvert.toSqlTimestamp(org.joda.time.MutableDateTime);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Timestamp timestamp = Joda1xConvert.toSqlTimestamp(org.joda.time.Instant);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Timestamp timestamp = Joda1xConvert.toSqlTimestamp(org.joda.time.DateTime);");
            final long var = System.currentTimeMillis();
        }
    }

    @Test
    void testToSqlTime() {
        {
            startingOf("// Time time = Joda1xConvert.toSqlTime(org.joda.time.Instant);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Time time = Joda1xConvert.toSqlTime(org.joda.time.LocalTime);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Time time = Joda1xConvert.toSqlTime(org.joda.time.DateTime);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Time time = Joda1xConvert.toSqlTime(org.joda.time.MutableDateTime);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Time time = Joda1xConvert.toSqlTime(org.joda.time.LocalDateTime);");
            final long var = System.currentTimeMillis();
        }
    }

    @Test
    void testToUtilCalendar() {
        {
            startingOf("// Calendar calendar = Joda1xConvert.toUtilCalendar(org.joda.time.LocalDateTime);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Calendar calendar = Joda1xConvert.toUtilCalendar(org.joda.time.MutableDateTime);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Calendar calendar = Joda1xConvert.toUtilCalendar(org.joda.time.Instant);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Calendar calendar = Joda1xConvert.toUtilCalendar(org.joda.time.LocalDate);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Calendar calendar = Joda1xConvert.toUtilCalendar(org.joda.time.DateTime);");
            final long var = System.currentTimeMillis();
        }
    }

    @Test
    void testToJdk8YearMonth() {
        {
            startingOf("// YearMonth yearMonth = Joda1xConvert.toJdk8YearMonth(org.joda.time.LocalDateTime);");
            org.joda.time.LocalDateTime datetime = org.joda.time.LocalDateTime.now();
            YearMonth yearMonth = Joda1xConvert.toJdk8YearMonth(datetime);
            assertEquals(yearMonth.getYear(), datetime.getYear());
            assertEquals(yearMonth.getMonthValue(), datetime.getMonthOfYear());
        }
        {
            startingOf("// YearMonth yearMonth = Joda1xConvert.toJdk8YearMonth(org.joda.time.LocalDate);");
            org.joda.time.LocalDate localDate = org.joda.time.LocalDate.now();
            YearMonth yearMonth = Joda1xConvert.toJdk8YearMonth(localDate);
            assertEquals(yearMonth.getYear(), localDate.getYear());
            assertEquals(yearMonth.getMonthValue(), localDate.getMonthOfYear());
        }
        {
            startingOf("// YearMonth yearMonth = Joda1xConvert.toJdk8YearMonth(org.joda.time.MutableDateTime);");
            org.joda.time.MutableDateTime datetime = org.joda.time.MutableDateTime.now();
            YearMonth yearMonth = Joda1xConvert.toJdk8YearMonth(datetime);
            assertEquals(yearMonth.getYear(), datetime.getYear());
            assertEquals(yearMonth.getMonthValue(), datetime.getMonthOfYear());
        }
        {
            startingOf("// YearMonth yearMonth = Joda1xConvert.toJdk8YearMonth(org.joda.time.DateTime);");
            org.joda.time.DateTime datetime = org.joda.time.DateTime.now();
            YearMonth yearMonth = Joda1xConvert.toJdk8YearMonth(datetime);
            assertEquals(yearMonth.getYear(), datetime.getYear());
            assertEquals(yearMonth.getMonthValue(), datetime.getMonthOfYear());
        }
        {
            startingOf("// YearMonth yearMonth = Joda1xConvert.toJdk8YearMonth(org.joda.time.Instant);");
            org.joda.time.Instant instant = org.joda.time.Instant.now();
            org.joda.time.DateTime datetime = instant.toDateTime();
            YearMonth yearMonth = Joda1xConvert.toJdk8YearMonth(instant);
            assertEquals(yearMonth.getYear(), datetime.getYear());
            assertEquals(yearMonth.getMonthValue(), datetime.getMonthOfYear());
        }
    }

    @Test
    void testToJodaLocalTime() {
        {
            startingOf("// LocalTime localTime = Joda1xConvert.toJodaLocalTime(java.util.Calendar);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// LocalTime localTime = Joda1xConvert.toJodaLocalTime(java.sql.Timestamp);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// LocalTime localTime = Joda1xConvert.toJodaLocalTime(java.sql.Time);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// LocalTime localTime = Joda1xConvert.toJodaLocalTime(java.lang.Long);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// LocalTime localTime = Joda1xConvert.toJodaLocalTime(java.util.Date);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// LocalTime localTime = Joda1xConvert.toJodaLocalTime(org.joda.time.DateTime);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// LocalTime localTime = Joda1xConvert.toJodaLocalTime(org.joda.time.LocalDateTime);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// LocalTime localTime = Joda1xConvert.toJodaLocalTime(org.joda.time.MutableDateTime);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// LocalTime localTime = Joda1xConvert.toJodaLocalTime(java.time.LocalDateTime);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// LocalTime localTime = Joda1xConvert.toJodaLocalTime(java.time.LocalTime);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// LocalTime localTime = Joda1xConvert.toJodaLocalTime(long);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// LocalTime localTime = Joda1xConvert.toJodaLocalTime(org.joda.time.Instant);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// LocalTime localTime = Joda1xConvert.toJodaLocalTime(java.time.OffsetDateTime);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// LocalTime localTime = Joda1xConvert.toJodaLocalTime(java.time.ZonedDateTime);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// LocalTime localTime = Joda1xConvert.toJodaLocalTime(java.time.Instant);");
            final long var = System.currentTimeMillis();
        }
    }

    @Test
    void testToJodaLocalDateTime() {
        {
            startingOf("// LocalDateTime localDateTime = Joda1xConvert.toJodaLocalDateTime(org.joda.time.DateTime);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// LocalDateTime localDateTime = Joda1xConvert.toJodaLocalDateTime(org.joda.time.LocalDate);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// LocalDateTime localDateTime = Joda1xConvert.toJodaLocalDateTime(java.time.YearMonth);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// LocalDateTime localDateTime = Joda1xConvert.toJodaLocalDateTime(java.time.LocalDate);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// LocalDateTime localDateTime = Joda1xConvert.toJodaLocalDateTime(long);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// LocalDateTime localDateTime = Joda1xConvert.toJodaLocalDateTime(java.lang.Long);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// LocalDateTime localDateTime = Joda1xConvert.toJodaLocalDateTime(org.joda.time.Instant);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// LocalDateTime localDateTime = Joda1xConvert.toJodaLocalDateTime(org.joda.time.MutableDateTime);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// LocalDateTime localDateTime = Joda1xConvert.toJodaLocalDateTime(java.sql.Timestamp);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// LocalDateTime localDateTime = Joda1xConvert.toJodaLocalDateTime(java.time.Instant);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// LocalDateTime localDateTime = Joda1xConvert.toJodaLocalDateTime(java.util.Date);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// LocalDateTime localDateTime = Joda1xConvert.toJodaLocalDateTime(java.util.Calendar);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// LocalDateTime localDateTime = Joda1xConvert.toJodaLocalDateTime(java.sql.Date);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// LocalDateTime localDateTime = Joda1xConvert.toJodaLocalDateTime(java.time.OffsetDateTime);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// LocalDateTime localDateTime = Joda1xConvert.toJodaLocalDateTime(java.time.LocalDateTime);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// LocalDateTime localDateTime = Joda1xConvert.toJodaLocalDateTime(java.time.ZonedDateTime);");
            final long var = System.currentTimeMillis();
        }
    }

    @Test
    void testToJdk8ZonedDateTime() {
        {
            startingOf("// ZonedDateTime zonedDateTime = Joda1xConvert.toJdk8ZonedDateTime(org.joda.time.LocalDateTime);");
            org.joda.time.LocalDateTime datetime = org.joda.time.LocalDateTime.now();
            ZonedDateTime zonedDateTime = Joda1xConvert.toJdk8ZonedDateTime(datetime);
            long timeNow = datetime.toDate().getTime();
            assertEquals(zonedDateTime.toInstant().toEpochMilli(), timeNow);
        }
        {
            startingOf("// ZonedDateTime zonedDateTime = Joda1xConvert.toJdk8ZonedDateTime(org.joda.time.LocalDate);");
            org.joda.time.LocalDate datetime = org.joda.time.LocalDate.now();
            ZonedDateTime zonedDateTime = Joda1xConvert.toJdk8ZonedDateTime(datetime);
            long timeNow = datetime.toDate().getTime();
            assertEquals(zonedDateTime.toInstant().toEpochMilli(), timeNow);
            assertEquals(zonedDateTime.getHour(), 0);
            assertEquals(zonedDateTime.getMinute(), 0);
            assertEquals(zonedDateTime.getSecond(), 0);
            assertEquals(zonedDateTime.getNano(), 0);
        }
        {
            startingOf("// ZonedDateTime zonedDateTime = Joda1xConvert.toJdk8ZonedDateTime(org.joda.time.DateTime);");
            org.joda.time.DateTime datetime = org.joda.time.DateTime.now();
            ZonedDateTime zonedDateTime = Joda1xConvert.toJdk8ZonedDateTime(datetime);
            assertEquals(zonedDateTime.toInstant().toEpochMilli(), datetime.getMillis());
        }
        {
            startingOf("// ZonedDateTime zonedDateTime = Joda1xConvert.toJdk8ZonedDateTime(org.joda.time.MutableDateTime);");
            org.joda.time.MutableDateTime datetime = org.joda.time.MutableDateTime.now();
            ZonedDateTime zonedDateTime = Joda1xConvert.toJdk8ZonedDateTime(datetime);
            assertEquals(zonedDateTime.toInstant().toEpochMilli(), datetime.getMillis());
        }
        {
            startingOf("// ZonedDateTime zonedDateTime = Joda1xConvert.toJdk8ZonedDateTime(org.joda.time.Instant);");
            org.joda.time.Instant instant = org.joda.time.Instant.now();
            ZonedDateTime zonedDateTime = Joda1xConvert.toJdk8ZonedDateTime(instant);
            Date utilDate = instant.toDate();
            assertEquals(Instant.ofEpochMilli(utilDate.getTime()), zonedDateTime.toInstant());
        }
    }

    @Test
    void testToJodaDateTime() {
        {
            startingOf("// DateTime dateTime = Joda1xConvert.toJodaDateTime(java.lang.Long);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// DateTime dateTime = Joda1xConvert.toJodaDateTime(org.joda.time.Instant);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// DateTime dateTime = Joda1xConvert.toJodaDateTime(org.joda.time.MutableDateTime);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// DateTime dateTime = Joda1xConvert.toJodaDateTime(long);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// DateTime dateTime = Joda1xConvert.toJodaDateTime(java.util.Date);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// DateTime dateTime = Joda1xConvert.toJodaDateTime(java.time.Instant);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// DateTime dateTime = Joda1xConvert.toJodaDateTime(java.time.ZonedDateTime);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// DateTime dateTime = Joda1xConvert.toJodaDateTime(java.time.OffsetDateTime);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// DateTime dateTime = Joda1xConvert.toJodaDateTime(java.time.LocalDateTime);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// DateTime dateTime = Joda1xConvert.toJodaDateTime(java.sql.Date);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// DateTime dateTime = Joda1xConvert.toJodaDateTime(java.util.Calendar);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// DateTime dateTime = Joda1xConvert.toJodaDateTime(org.joda.time.LocalDate);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// DateTime dateTime = Joda1xConvert.toJodaDateTime(org.joda.time.LocalDateTime);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// DateTime dateTime = Joda1xConvert.toJodaDateTime(java.sql.Timestamp);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// DateTime dateTime = Joda1xConvert.toJodaDateTime(java.time.YearMonth);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// DateTime dateTime = Joda1xConvert.toJodaDateTime(java.time.LocalDate);");
            final long var = System.currentTimeMillis();
        }
    }

    @Test
    void testToJdk8OffsetDateTime() {
        {
            startingOf("// OffsetDateTime offsetDateTime = Joda1xConvert.toJdk8OffsetDateTime(org.joda.time.LocalDate);");
            org.joda.time.LocalDate localDate = org.joda.time.LocalDate.now();
            OffsetDateTime datetime = Joda1xConvert.toJdk8OffsetDateTime(localDate);
            LocalDate jdkLocalDate = localDate.toDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            assertEquals(datetime.toLocalDate(), jdkLocalDate);
            assertEquals(datetime.getHour(), 0);
            assertEquals(datetime.getMinute(), 0);
            assertEquals(datetime.getSecond(), 0);
        }
        {
            startingOf("// OffsetDateTime offsetDateTime = Joda1xConvert.toJdk8OffsetDateTime(org.joda.time.LocalDateTime);");
            org.joda.time.LocalDateTime dt = org.joda.time.LocalDateTime.now();
            OffsetDateTime datetime = Joda1xConvert.toJdk8OffsetDateTime(dt);
            assertEquals(datetime, dt.toDate().toInstant().atZone(ZoneId.systemDefault()).toOffsetDateTime());
        }
        {
            startingOf("// OffsetDateTime offsetDateTime = Joda1xConvert.toJdk8OffsetDateTime(org.joda.time.Instant);");
            org.joda.time.Instant instant = org.joda.time.Instant.now();
            OffsetDateTime datetime = Joda1xConvert.toJdk8OffsetDateTime(instant);
            assertEquals(instant.getMillis(), datetime.toInstant().toEpochMilli());
        }
        {
            startingOf("// OffsetDateTime offsetDateTime = Joda1xConvert.toJdk8OffsetDateTime(org.joda.time.DateTime);");
            org.joda.time.DateTime datetime = org.joda.time.DateTime.now();
            OffsetDateTime offsetDateTime = Joda1xConvert.toJdk8OffsetDateTime(datetime);
            assertEquals(datetime.getMillis(), offsetDateTime.toInstant().toEpochMilli());
        }
        {
            startingOf("// OffsetDateTime offsetDateTime = Joda1xConvert.toJdk8OffsetDateTime(org.joda.time.MutableDateTime);");
            org.joda.time.MutableDateTime datetime = org.joda.time.MutableDateTime.now();
            OffsetDateTime offsetDateTime = Joda1xConvert.toJdk8OffsetDateTime(datetime);
            assertEquals(datetime.getMillis(), offsetDateTime.toInstant().toEpochMilli());
        }
    }
}
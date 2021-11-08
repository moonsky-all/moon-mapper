package com.moonsky.mapper.util;

import com.moonsky.mapper.TestSuperclass;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author benshaoye
 */
class Joda2xConvertTestTest extends TestSuperclass {

    @Test
    void testPrintlnAll() {
        printlnConvertTestAll(Joda2xConvert.class);
    }

    @Test
    void testToJdk8LocalDate() {
        startingOf("// LocalDate localDate = Joda2xConvert.toJdk8LocalDate(org.joda.time.YearMonth);");
        final long var = System.currentTimeMillis();
    }

    @Test
    void testToJodaMutableDateTime() {
        startingOf("// MutableDateTime mutableDateTime = Joda2xConvert.toJodaMutableDateTime(org.joda.time.YearMonth);");
        final long var = System.currentTimeMillis();
    }

    @Test
    void testToJdk8MonthDay() {
        startingOf("// MonthDay monthDay = Joda2xConvert.toJdk8MonthDay(org.joda.time.MonthDay);");
        final long var = System.currentTimeMillis();
    }

    @Test
    void testToUtilDate() {
        startingOf("// Date date = Joda2xConvert.toUtilDate(org.joda.time.YearMonth);");
        final long var = System.currentTimeMillis();
    }

    @Test
    void testToLong() {
        startingOf("// Long long = Joda2xConvert.toLong(org.joda.time.YearMonth);");
        final long var = System.currentTimeMillis();
    }

    @Test
    void testToPrimitiveLong() {
        startingOf("// long long = Joda2xConvert.toPrimitiveLong(org.joda.time.YearMonth);");
        final long var = System.currentTimeMillis();
    }

    @Test
    void testToJodaLocalDate() {
        startingOf("// LocalDate localDate = Joda2xConvert.toJodaLocalDate(org.joda.time.YearMonth);");
        final long var = System.currentTimeMillis();
    }

    @Test
    void testToJodaInstant() {
        startingOf("// Instant instant = Joda2xConvert.toJodaInstant(org.joda.time.YearMonth);");
        final long var = System.currentTimeMillis();
    }

    @Test
    void testToJdk8Instant() {
        startingOf("// Instant instant = Joda2xConvert.toJdk8Instant(org.joda.time.YearMonth);");
        final long var = System.currentTimeMillis();
    }

    @Test
    void testToJdk8Month() {
        {
            startingOf("// Month month = Joda2xConvert.toJdk8Month(org.joda.time.YearMonth);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Month month = Joda2xConvert.toJdk8Month(org.joda.time.MonthDay);");
            final long var = System.currentTimeMillis();
        }
    }

    @Test
    void testToJdk8LocalDateTime() {
        startingOf("// LocalDateTime localDateTime = Joda2xConvert.toJdk8LocalDateTime(org.joda.time.YearMonth);");
        final long var = System.currentTimeMillis();
    }

    @Test
    void testToSqlDate() {
        startingOf("// Date date = Joda2xConvert.toSqlDate(org.joda.time.YearMonth);");
        final long var = System.currentTimeMillis();
    }

    @Test
    void testToJdk8Year() {
        startingOf("// Year year = Joda2xConvert.toJdk8Year(org.joda.time.YearMonth);");
        final long var = System.currentTimeMillis();
    }

    @Test
    void testToSqlTimestamp() {
        startingOf("// Timestamp timestamp = Joda2xConvert.toSqlTimestamp(org.joda.time.YearMonth);");
        final long var = System.currentTimeMillis();
    }

    @Test
    void testToUtilCalendar() {
        startingOf("// Calendar calendar = Joda2xConvert.toUtilCalendar(org.joda.time.YearMonth);");
        final long var = System.currentTimeMillis();
    }

    @Test
    void testToJdk8YearMonth() {
        startingOf("// YearMonth yearMonth = Joda2xConvert.toJdk8YearMonth(org.joda.time.YearMonth);");
        final long var = System.currentTimeMillis();
    }

    @Test
    void testToJodaYearMonth() {
        {
            startingOf("// YearMonth yearMonth = Joda2xConvert.toJodaYearMonth(java.time.ZonedDateTime);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// YearMonth yearMonth = Joda2xConvert.toJodaYearMonth(java.time.OffsetDateTime);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// YearMonth yearMonth = Joda2xConvert.toJodaYearMonth(java.time.LocalDateTime);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// YearMonth yearMonth = Joda2xConvert.toJodaYearMonth(java.time.LocalDate);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// YearMonth yearMonth = Joda2xConvert.toJodaYearMonth(java.time.YearMonth);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// YearMonth yearMonth = Joda2xConvert.toJodaYearMonth(java.sql.Timestamp);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// YearMonth yearMonth = Joda2xConvert.toJodaYearMonth(org.joda.time.LocalDateTime);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// YearMonth yearMonth = Joda2xConvert.toJodaYearMonth(org.joda.time.LocalDate);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// YearMonth yearMonth = Joda2xConvert.toJodaYearMonth(org.joda.time.DateTime);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// YearMonth yearMonth = Joda2xConvert.toJodaYearMonth(java.util.Date);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// YearMonth yearMonth = Joda2xConvert.toJodaYearMonth(java.sql.Date);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// YearMonth yearMonth = Joda2xConvert.toJodaYearMonth(java.util.Calendar);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// YearMonth yearMonth = Joda2xConvert.toJodaYearMonth(org.joda.time.MutableDateTime);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// YearMonth yearMonth = Joda2xConvert.toJodaYearMonth(org.joda.time.Instant);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// YearMonth yearMonth = Joda2xConvert.toJodaYearMonth(java.time.Instant);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// YearMonth yearMonth = Joda2xConvert.toJodaYearMonth(long);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// YearMonth yearMonth = Joda2xConvert.toJodaYearMonth(java.lang.Long);");
            final long var = System.currentTimeMillis();
        }
    }

    @Test
    void testToJodaLocalDateTime() {
        startingOf("// LocalDateTime localDateTime = Joda2xConvert.toJodaLocalDateTime(org.joda.time.YearMonth);");
        final long var = System.currentTimeMillis();
    }

    @Test
    void testToJdk8ZonedDateTime() {
        startingOf("// ZonedDateTime zonedDateTime = Joda2xConvert.toJdk8ZonedDateTime(org.joda.time.YearMonth);");
        final long var = System.currentTimeMillis();
    }

    @Test
    void testToJodaDateTime() {
        startingOf("// DateTime dateTime = Joda2xConvert.toJodaDateTime(org.joda.time.YearMonth);");
        final long var = System.currentTimeMillis();
    }

    @Test
    void testToJdk8OffsetDateTime() {
        startingOf("// OffsetDateTime offsetDateTime = Joda2xConvert.toJdk8OffsetDateTime(org.joda.time.YearMonth);");
        final long var = System.currentTimeMillis();
    }

    @Test
    void testToJodaMonthDay() {
        {
            startingOf("// MonthDay monthDay = Joda2xConvert.toJodaMonthDay(java.time.Instant);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// MonthDay monthDay = Joda2xConvert.toJodaMonthDay(java.util.Date);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// MonthDay monthDay = Joda2xConvert.toJodaMonthDay(java.sql.Timestamp);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// MonthDay monthDay = Joda2xConvert.toJodaMonthDay(java.sql.Date);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// MonthDay monthDay = Joda2xConvert.toJodaMonthDay(java.time.MonthDay);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// MonthDay monthDay = Joda2xConvert.toJodaMonthDay(java.time.LocalDateTime);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// MonthDay monthDay = Joda2xConvert.toJodaMonthDay(java.time.OffsetDateTime);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// MonthDay monthDay = Joda2xConvert.toJodaMonthDay(java.time.ZonedDateTime);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// MonthDay monthDay = Joda2xConvert.toJodaMonthDay(java.time.LocalDate);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// MonthDay monthDay = Joda2xConvert.toJodaMonthDay(org.joda.time.MutableDateTime);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// MonthDay monthDay = Joda2xConvert.toJodaMonthDay(org.joda.time.LocalDate);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// MonthDay monthDay = Joda2xConvert.toJodaMonthDay(org.joda.time.LocalDateTime);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// MonthDay monthDay = Joda2xConvert.toJodaMonthDay(org.joda.time.Instant);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// MonthDay monthDay = Joda2xConvert.toJodaMonthDay(java.util.Calendar);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// MonthDay monthDay = Joda2xConvert.toJodaMonthDay(java.lang.Long);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// MonthDay monthDay = Joda2xConvert.toJodaMonthDay(long);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// MonthDay monthDay = Joda2xConvert.toJodaMonthDay(org.joda.time.DateTime);");
            final long var = System.currentTimeMillis();
        }
    }
}
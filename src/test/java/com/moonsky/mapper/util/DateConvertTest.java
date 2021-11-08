package com.moonsky.mapper.util;

import com.moonsky.mapper.TestSuperclass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DateConvertTest extends TestSuperclass {

    @BeforeEach
    void setUp() {
    }

    @Test
    void testPrintlnAll() {
        printlnConvertTestAll(DateConvert.class);
    }

    static final String SOURCE_yyyy_MM_dd_HHmmss_SSS = "yyyy-MM-dd HH:mm:ss SSS";
    static final String SOURCE_yyyy_MM_dd = "yyyy-MM-dd";
    static final String SOURCE_HHmmss = "HH:mm:ss";
    static final String SOURCE_yyyy_MM = "yyyy-MM";
    static final String SOURCE_MM_dd = "MM-dd";
    static final DateTimeFormatter yyyy_MM_dd_HHmmss_SSS = DateTimeFormatter.ofPattern(SOURCE_yyyy_MM_dd_HHmmss_SSS);
    static final DateTimeFormatter yyyy_MM_dd = DateTimeFormatter.ofPattern(SOURCE_yyyy_MM_dd);
    static final DateTimeFormatter yyyy_MM = DateTimeFormatter.ofPattern(SOURCE_yyyy_MM);
    static final DateTimeFormatter MM_dd = DateTimeFormatter.ofPattern(SOURCE_MM_dd);
    static final DateTimeFormatter HHmmss = DateTimeFormatter.ofPattern(SOURCE_HHmmss);

    @Test
    void testToJdk8LocalDate() throws ParseException {
        {
            startingOf("// LocalDate localDate = DateConvert.toJdk8LocalDate(long);");
            String date = "2021-05-03", time = "12:15:48", milliseconds = "569";
            String datetime = String.format("%s %s %s", date, time, milliseconds);
            Date utilDate = new SimpleDateFormat(SOURCE_yyyy_MM_dd_HHmmss_SSS).parse(datetime);
            long timestamp = utilDate.getTime();
            LocalDate localDate = DateConvert.toJdk8LocalDate(timestamp);
            assertEquals(date, yyyy_MM_dd.format(localDate));
        }
        {
            startingOf("// LocalDate localDate = DateConvert.toJdk8LocalDate(java.time.OffsetDateTime);");
            String date = "2021-05-03", time = "12:15:48", milliseconds = "569";
            String datetime = String.format("%s %s %s", date, time, milliseconds);
            System.out.println(datetime);
            ZonedDateTime zonedDateTime = ZonedDateTime.parse(datetime,
                yyyy_MM_dd_HHmmss_SSS.withLocale(Locale.getDefault()).withZone(ZoneId.systemDefault()));
            OffsetDateTime offsetDateTime = zonedDateTime.toOffsetDateTime();
            LocalDate localDate = DateConvert.toJdk8LocalDate(offsetDateTime);
            assertEquals(date, yyyy_MM_dd.format(localDate));
            System.out.println(offsetDateTime);
            System.out.println(offsetDateTime.format(yyyy_MM_dd_HHmmss_SSS));
        }
        {
            startingOf("// LocalDate localDate = DateConvert.toJdk8LocalDate(java.time.LocalDateTime);");
            String datetimeStringify = new SimpleDateFormat(SOURCE_yyyy_MM_dd_HHmmss_SSS).format(new Date());
            System.out.println(datetimeStringify);
            LocalDateTime localDateTime = LocalDateTime.parse(datetimeStringify, yyyy_MM_dd_HHmmss_SSS);
            LocalDate localDate = DateConvert.toJdk8LocalDate(localDateTime);
            assertEquals(yyyy_MM_dd.format(localDate), yyyy_MM_dd.format(localDateTime));
        }
        {
            startingOf("// LocalDate localDate = DateConvert.toJdk8LocalDate(java.time.ZonedDateTime);");
            String date = "2021-05-03", time = "12:15:48", milliseconds = "569";
            String datetime = String.format("%s %s %s", date, time, milliseconds);
            ZonedDateTime zonedDateTime = ZonedDateTime.parse(datetime,
                yyyy_MM_dd_HHmmss_SSS.withZone(ZoneId.systemDefault()));
            LocalDateTime localDateTime = LocalDateTime.parse(datetime,
                yyyy_MM_dd_HHmmss_SSS.withZone(ZoneId.systemDefault()));
            LocalDate localDate = DateConvert.toJdk8LocalDate(zonedDateTime);
            assertEquals(yyyy_MM_dd.format(localDate), date);
            System.out.println(yyyy_MM_dd.format(localDate));
            System.out.println(zonedDateTime);
            System.out.println(localDateTime);
            // LocalDate localDate = DateConvert.toJdk8LocalDate(java.time.ZonedDateTime);
        }
        {
            startingOf("// LocalDate localDate = DateConvert.toJdk8LocalDate(java.time.Instant);");
            Instant nowInstant = Instant.now();
            String instantStringify = nowInstant.toString();
            String date = instantStringify.substring(0, 10);
            String time = instantStringify.substring(11, 19);
            String milliseconds = instantStringify.substring(20, 23);
            LocalDate localDate = DateConvert.toJdk8LocalDate(nowInstant);
            assertEquals(yyyy_MM_dd.format(localDate), date);
            System.out.println(nowInstant);
            System.out.println(instantStringify);
            System.out.println(date);
            System.out.println(time);
            System.out.println(milliseconds);
            // LocalDate localDate = DateConvert.toJdk8LocalDate(java.time.Instant);
        }
        {
            startingOf("// LocalDate localDate = DateConvert.toJdk8LocalDate(java.util.Date);");
            Date utilDate = new Date();
            String date = new SimpleDateFormat(SOURCE_yyyy_MM_dd).format(utilDate);
            LocalDate localDate = DateConvert.toJdk8LocalDate(utilDate);
            assertEquals(yyyy_MM_dd.format(localDate), date);
            System.out.println(date);
            // LocalDate localDate = DateConvert.toJdk8LocalDate(java.util.Date);
        }
        {
            startingOf("// LocalDate localDate = DateConvert.toJdk8LocalDate(java.util.Calendar);");
            Calendar calendar = Calendar.getInstance();
            Date utilDate = calendar.getTime();
            LocalDate localDate = DateConvert.toJdk8LocalDate(calendar);
            String date = new SimpleDateFormat(SOURCE_yyyy_MM_dd).format(utilDate);
            assertEquals(yyyy_MM_dd.format(localDate), date);
            // LocalDate localDate = DateConvert.toJdk8LocalDate(java.util.Calendar);
        }
        {
            startingOf("// LocalDate localDate = DateConvert.toJdk8LocalDate(java.sql.Date);");
            Date utilDate = new Date();
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            LocalDate localDate = DateConvert.toJdk8LocalDate(sqlDate);
            String date = new SimpleDateFormat(SOURCE_yyyy_MM_dd).format(sqlDate);
            assertEquals(yyyy_MM_dd.format(localDate), date);
            System.out.println(sqlDate);
            // LocalDate localDate = DateConvert.toJdk8LocalDate(java.sql.Date);
        }
        {
            startingOf("// LocalDate localDate = DateConvert.toJdk8LocalDate(java.sql.Timestamp);");
            Date utilDate = new Date();
            java.sql.Timestamp timestamp = new java.sql.Timestamp(utilDate.getTime());
            LocalDate localDate = DateConvert.toJdk8LocalDate(timestamp);
            String date = new SimpleDateFormat(SOURCE_yyyy_MM_dd).format(timestamp);
            assertEquals(yyyy_MM_dd.format(localDate), date);
            System.out.println(timestamp);
            // LocalDate localDate = DateConvert.toJdk8LocalDate(java.sql.Timestamp);
        }
        {
            startingOf("// LocalDate localDate = DateConvert.toJdk8LocalDate(java.lang.Long);");
            Long timestamp = new Date().getTime();
            LocalDate localDate = DateConvert.toJdk8LocalDate(timestamp);
            String date = new SimpleDateFormat(SOURCE_yyyy_MM_dd).format(timestamp);
            assertEquals(yyyy_MM_dd.format(localDate), date);
            System.out.println(timestamp);
            // LocalDate localDate = DateConvert.toJdk8LocalDate(java.lang.Long);
        }
        {
            startingOf("// LocalDate localDate = DateConvert.toJdk8LocalDate(java.time.YearMonth);");
            YearMonth yearMonth = YearMonth.now();
            String yearStringify = padStart(yearMonth.getYear(), 2, '0');
            String monthStringify = padStart(yearMonth.getMonthValue(), 2, '0');
            String datetimeStringify = String.format("%s-%s-01", yearStringify, monthStringify);
            String yearMonthStringify = String.format("%s-%s", yearStringify, monthStringify);
            System.out.println(datetimeStringify);
            System.out.println(yearMonthStringify);
            DateTimeFormatter yyyy_MM = DateTimeFormatter.ofPattern("yyyy-MM").withZone(ZoneId.systemDefault());
            assertEquals(yyyy_MM.format(yearMonth), yearMonthStringify);
            // LocalDate localDate = DateConvert.toJdk8LocalDate(java.time.YearMonth);
        }
    }

    @Test
    void testToJdk8MonthDay() {
        {
            startingOf("// MonthDay monthDay = DateConvert.toJdk8MonthDay(java.util.Date);");
            Date utilDate = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(utilDate);
            String monthStringify = padStart(calendar.get(Calendar.MONTH) + 1, 2, '0');
            String dayStringify = padStart(calendar.get(Calendar.DAY_OF_MONTH), 2, '0');
            String monthDayStringify = String.format("%s-%s", monthStringify, dayStringify);

            MonthDay monthDay = DateConvert.toJdk8MonthDay(utilDate);
            assertEquals(monthDay.format(MM_dd), monthDayStringify);
        }
        {
            startingOf("// MonthDay monthDay = DateConvert.toJdk8MonthDay(long);");
            Calendar calendar = Calendar.getInstance();
            String monthStringify = padStart(calendar.get(Calendar.MONTH) + 1, 2, '0');
            String dayStringify = padStart(calendar.get(Calendar.DAY_OF_MONTH), 2, '0');
            String monthDayStringify = String.format("%s-%s", monthStringify, dayStringify);
            Long timestamp = calendar.getTimeInMillis();
            MonthDay monthDay = DateConvert.toJdk8MonthDay(timestamp);
            assertEquals(monthDay.format(MM_dd), monthDayStringify);
        }
        {
            startingOf("// MonthDay monthDay = DateConvert.toJdk8MonthDay(java.lang.Long);");
            Calendar calendar = Calendar.getInstance();
            String monthStringify = padStart(calendar.get(Calendar.MONTH) + 1, 2, '0');
            String dayStringify = padStart(calendar.get(Calendar.DAY_OF_MONTH), 2, '0');
            String monthDayStringify = String.format("%s-%s", monthStringify, dayStringify);
            Long timestamp = calendar.getTimeInMillis();
            MonthDay monthDay = DateConvert.toJdk8MonthDay(timestamp);
            assertEquals(monthDay.format(MM_dd), monthDayStringify);
        }
        {
            startingOf("// MonthDay monthDay = DateConvert.toJdk8MonthDay(java.util.Calendar);");
            Calendar calendar = Calendar.getInstance();
            String monthStringify = padStart(calendar.get(Calendar.MONTH) + 1, 2, '0');
            String dayStringify = padStart(calendar.get(Calendar.DAY_OF_MONTH), 2, '0');
            String monthDayStringify = String.format("%s-%s", monthStringify, dayStringify);
            MonthDay monthDay = DateConvert.toJdk8MonthDay(calendar);
            assertEquals(monthDay.format(MM_dd), monthDayStringify);
        }
        {
            startingOf("// MonthDay monthDay = DateConvert.toJdk8MonthDay(java.sql.Date);");
            Date utilDate = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(utilDate.getTime());
            String monthStringify = padStart(calendar.get(Calendar.MONTH) + 1, 2, '0');
            String dayStringify = padStart(calendar.get(Calendar.DAY_OF_MONTH), 2, '0');
            String monthDayStringify = String.format("%s-%s", monthStringify, dayStringify);
            MonthDay monthDay = DateConvert.toJdk8MonthDay(utilDate);
            assertEquals(monthDay.format(MM_dd), monthDayStringify);
        }
        {
            startingOf("// MonthDay monthDay = DateConvert.toJdk8MonthDay(java.sql.Timestamp);");
            LocalDateTime localDateTime = LocalDateTime.now();
            String monthStringify = padStart(localDateTime.getMonthValue(), 2, '0');
            String dayStringify = padStart(localDateTime.getDayOfMonth(), 2, '0');
            String monthDayStringify = String.format("%s-%s", monthStringify, dayStringify);
            Timestamp timestamp = Timestamp.valueOf(localDateTime);
            MonthDay monthDay = DateConvert.toJdk8MonthDay(timestamp);
            System.out.println(monthDay.format(MM_dd));
            System.out.println(monthDay);
            System.out.println(monthDayStringify);
            assertEquals(monthDay.format(MM_dd), monthDayStringify);
        }
        {
            startingOf("// MonthDay monthDay = DateConvert.toJdk8MonthDay(java.time.Instant);");
            Instant instant = Instant.now();
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(instant.toEpochMilli());
            String monthStringify = padStart(calendar.get(Calendar.MONTH) + 1, 2, '0');
            String dayStringify = padStart(calendar.get(Calendar.DAY_OF_MONTH), 2, '0');
            String monthDayStringify = String.format("%s-%s", monthStringify, dayStringify);
            MonthDay monthDay = DateConvert.toJdk8MonthDay(instant);
            System.out.println(monthDay.format(MM_dd));
            System.out.println(monthDay);
            System.out.println(monthDayStringify);
            assertEquals(monthDay.format(MM_dd), monthDayStringify);
        }
        {
            startingOf("// MonthDay monthDay = DateConvert.toJdk8MonthDay(java.time.LocalDate);");
            LocalDate localDate = LocalDate.now();
            String monthStringify = padStart(localDate.getMonthValue(), 2, '0');
            String dayStringify = padStart(localDate.getDayOfMonth(), 2, '0');
            String monthDayStringify = String.format("%s-%s", monthStringify, dayStringify);
            MonthDay monthDay = DateConvert.toJdk8MonthDay(localDate);
            System.out.println(monthDay.format(MM_dd));
            System.out.println(monthDay);
            assertEquals(monthDay.format(MM_dd), monthDayStringify);
        }
        {
            startingOf("// MonthDay monthDay = DateConvert.toJdk8MonthDay(java.time.LocalDateTime);");
            LocalDateTime localDateTime = LocalDateTime.now();
            String monthStringify = padStart(localDateTime.getMonthValue(), 2, '0');
            String dayStringify = padStart(localDateTime.getDayOfMonth(), 2, '0');
            String monthDayStringify = String.format("%s-%s", monthStringify, dayStringify);
            MonthDay monthDay = DateConvert.toJdk8MonthDay(localDateTime);
            System.out.println(monthDay.format(MM_dd));
            System.out.println(monthDay);
            assertEquals(monthDay.format(MM_dd), monthDayStringify);
        }
        {
            startingOf("// MonthDay monthDay = DateConvert.toJdk8MonthDay(java.time.OffsetDateTime);");
            OffsetDateTime offsetDateTime = OffsetDateTime.now();
            String monthStringify = padStart(offsetDateTime.getMonthValue(), 2, '0');
            String dayStringify = padStart(offsetDateTime.getDayOfMonth(), 2, '0');
            String monthDayStringify = String.format("%s-%s", monthStringify, dayStringify);
            MonthDay monthDay = DateConvert.toJdk8MonthDay(offsetDateTime);
            System.out.println(monthDay.format(MM_dd));
            System.out.println(monthDay);
            assertEquals(monthDay.format(MM_dd), monthDayStringify);
        }
        {
            startingOf("// MonthDay monthDay = DateConvert.toJdk8MonthDay(java.time.ZonedDateTime);");
            ZonedDateTime zonedDateTime = ZonedDateTime.now();
            String monthStringify = padStart(zonedDateTime.getMonthValue(), 2, '0');
            String dayStringify = padStart(zonedDateTime.getDayOfMonth(), 2, '0');
            String monthDayStringify = String.format("%s-%s", monthStringify, dayStringify);
            MonthDay monthDay = DateConvert.toJdk8MonthDay(zonedDateTime);
            System.out.println(monthDay.format(MM_dd));
            System.out.println(monthDay);
            assertEquals(monthDay.format(MM_dd), monthDayStringify);
        }
    }

    @Test
    void testToUtilDate() {
        {
            startingOf("// Date date = DateConvert.toUtilDate(java.util.Calendar);");
            Calendar calendar = Calendar.getInstance();
            Date date = DateConvert.toUtilDate(calendar);
            assertEquals(date.getTime(), calendar.getTimeInMillis());
        }
        {
            startingOf("// Date date = DateConvert.toUtilDate(java.time.Instant);");
            Instant instant = Instant.now();
            Date utilDate = DateConvert.toUtilDate(instant);
            String convertedStringify = new SimpleDateFormat(SOURCE_yyyy_MM_dd_HHmmss_SSS).format(utilDate);
            assertEquals(convertedStringify, yyyy_MM_dd_HHmmss_SSS.format(instant.atZone(ZoneId.systemDefault())));
        }
        {
            startingOf("// Date date = DateConvert.toUtilDate(java.time.ZonedDateTime);");
            ZonedDateTime offsetDateTime = ZonedDateTime.now();
            Date utilDate = DateConvert.toUtilDate(offsetDateTime);
            String convertedStringify = new SimpleDateFormat(SOURCE_yyyy_MM_dd_HHmmss_SSS).format(utilDate);
            assertEquals(convertedStringify, yyyy_MM_dd_HHmmss_SSS.format(offsetDateTime));
        }
        {
            startingOf("// Date date = DateConvert.toUtilDate(java.time.OffsetDateTime);");
            OffsetDateTime offsetDateTime = OffsetDateTime.now();
            Date utilDate = DateConvert.toUtilDate(offsetDateTime);
            String convertedStringify = new SimpleDateFormat(SOURCE_yyyy_MM_dd_HHmmss_SSS).format(utilDate);
            assertEquals(convertedStringify, yyyy_MM_dd_HHmmss_SSS.format(offsetDateTime));
        }
        {
            startingOf("// Date date = DateConvert.toUtilDate(java.time.LocalDate);");
            LocalDate localDate = LocalDate.now();
            Date utilDate = DateConvert.toUtilDate(localDate);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(utilDate);

            String yearStringify = padStart(calendar.get(Calendar.YEAR), 4, '0');
            String monthStringify = padStart(calendar.get(Calendar.MONTH) + 1, 2, '0');
            String dayStringify = padStart(calendar.get(Calendar.DAY_OF_MONTH), 2, '0');

            String dateStringify = String.format("%s-%s-%s", yearStringify, monthStringify, dayStringify);
            assertEquals(yyyy_MM_dd.format(localDate), dateStringify);
        }
        {
            startingOf("// Date date = DateConvert.toUtilDate(java.time.YearMonth);");
            YearMonth yearMonth = YearMonth.now();
            Date utilDate = DateConvert.toUtilDate(yearMonth);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(utilDate);

            String yearStringify = padStart(calendar.get(Calendar.YEAR), 4, '0');
            String monthStringify = padStart(calendar.get(Calendar.MONTH) + 1, 2, '0');

            String yearMonthStringify = String.format("%s-%s", yearStringify, monthStringify);
            assertEquals(yyyy_MM.format(yearMonth), yearMonthStringify);
        }
        {
            startingOf("// Date date = DateConvert.toUtilDate(java.time.LocalDateTime);");
            String date = "2021-05-03", time = "12:15:48", milliseconds = "569";
            String datetime = String.format("%s %s %s", date, time, milliseconds);

            LocalDateTime localDateTime = LocalDateTime.parse(datetime, yyyy_MM_dd_HHmmss_SSS);
            Date utilDate = DateConvert.toUtilDate(localDateTime);
            assertEquals(new SimpleDateFormat(SOURCE_yyyy_MM_dd_HHmmss_SSS).format(utilDate), datetime);
        }
        {
            startingOf("// Date date = DateConvert.toUtilDate(long);");
            Long timestampValue = System.currentTimeMillis();
            Date utilDate = DateConvert.toUtilDate(timestampValue);
            ZonedDateTime zonedDateTime = Instant.ofEpochMilli(timestampValue).atZone(ZoneId.systemDefault());
            String formatted = yyyy_MM_dd_HHmmss_SSS.withZone(ZoneId.systemDefault()).format(zonedDateTime);
            assertEquals(formatted, new SimpleDateFormat(SOURCE_yyyy_MM_dd_HHmmss_SSS).format(utilDate));
        }
        {
            startingOf("// Date date = DateConvert.toUtilDate(java.lang.Long);");
            Long timestampValue = System.currentTimeMillis();
            Date utilDate = DateConvert.toUtilDate(timestampValue);
            ZonedDateTime zonedDateTime = Instant.ofEpochMilli(timestampValue).atZone(ZoneId.systemDefault());
            String formatted = yyyy_MM_dd_HHmmss_SSS.withZone(ZoneId.systemDefault()).format(zonedDateTime);
            assertEquals(formatted, new SimpleDateFormat(SOURCE_yyyy_MM_dd_HHmmss_SSS).format(utilDate));
        }
        {
            startingOf("// Date date = DateConvert.toUtilDate(java.sql.Date);");
            long longValue = System.currentTimeMillis();
            java.sql.Date sqlDate = new java.sql.Date(longValue);
            Date utilDate = DateConvert.toUtilDate(sqlDate);
            assertEquals(sqlDate.getTime(), longValue);
            assertEquals(utilDate.getTime(), longValue);
            assertEquals(utilDate.getTime(), sqlDate.getTime());
            DateFormat formatter = new SimpleDateFormat(SOURCE_yyyy_MM_dd);
            assertEquals(formatter.format(utilDate), formatter.format(sqlDate));
        }
        {
            startingOf("// Date date = DateConvert.toUtilDate(java.sql.Timestamp);");
            long longValue = System.currentTimeMillis();
            Timestamp timestamp = new Timestamp(longValue);
            Date utilDate = DateConvert.toUtilDate(timestamp);
            assertEquals(timestamp.getTime(), longValue);
            assertEquals(utilDate.getTime(), longValue);
            assertEquals(utilDate.getTime(), timestamp.getTime());
            DateFormat formatter = new SimpleDateFormat(SOURCE_yyyy_MM_dd_HHmmss_SSS);
            assertEquals(formatter.format(utilDate), formatter.format(timestamp));
        }
    }

    @Test
    void testToLong() {
        {
            startingOf("// Long long = DateConvert.toLong(java.time.LocalDate);");
            final long var = System.currentTimeMillis();
            LocalDate localDate = new java.sql.Date(var).toLocalDate();
            assertEquals(new SimpleDateFormat(SOURCE_yyyy_MM_dd).format(var), yyyy_MM_dd.format(localDate));
        }
        {
            // ?? 过程有问题啊
            startingOf("// Long long = DateConvert.toLong(java.time.YearMonth);");
            final long var = System.currentTimeMillis();
            ZonedDateTime zonedDateTime = Instant.ofEpochMilli(var).atZone(ZoneId.systemDefault());
            YearMonth yearMonth = YearMonth.from(zonedDateTime);
            String yearMonthString = new SimpleDateFormat(SOURCE_yyyy_MM).format(new Date(var));
            assertEquals(yyyy_MM.format(yearMonth), yearMonthString);
        }
        {
            startingOf("// Long long = DateConvert.toLong(java.time.LocalDateTime);");
            final long var = System.currentTimeMillis();
            LocalDateTime localDateTime = Instant.ofEpochMilli(var).atZone(ZoneId.systemDefault()).toLocalDateTime();
            long instant = DateConvert.toLong(localDateTime);
            assertEquals(instant, var);
        }
        {
            startingOf("// Long long = DateConvert.toLong(java.sql.Timestamp);");
            final long var = System.currentTimeMillis();
            long instant = DateConvert.toLong(new java.sql.Timestamp(var));
            assertEquals(instant, var);
        }
        {
            startingOf("// Long long = DateConvert.toLong(java.sql.Date);");
            final long var = System.currentTimeMillis();
            long instant = DateConvert.toLong(new java.sql.Date(var));
            assertEquals(instant, var);
        }
        {
            startingOf("// Long long = DateConvert.toLong(java.util.Calendar);");
            final long var = System.currentTimeMillis();
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(var);
            long instant = DateConvert.toLong(calendar);
            assertEquals(instant, var);
        }
        {
            startingOf("// Long long = DateConvert.toLong(java.util.Date);");
            final long var = System.currentTimeMillis();
            long instant = DateConvert.toLong(new Date(var));
            assertEquals(instant, var);
        }
        {
            startingOf("// Long long = DateConvert.toLong(java.time.Instant);");
            final long var = System.currentTimeMillis();
            long instant = DateConvert.toLong(Instant.ofEpochMilli(var));
            assertEquals(instant, var);
        }
        {
            startingOf("// Long long = DateConvert.toLong(java.time.ZonedDateTime);");
            final long var = System.currentTimeMillis();
            ZonedDateTime zonedDateTime = Instant.ofEpochMilli(var).atZone(ZoneId.systemDefault());
            long instant = DateConvert.toLong(zonedDateTime);
            assertEquals(instant, var);
        }
        {
            startingOf("// Long long = DateConvert.toLong(java.time.OffsetDateTime);");
            final long var = System.currentTimeMillis();
            ZonedDateTime zonedDateTime = Instant.ofEpochMilli(var).atZone(ZoneId.systemDefault());
            OffsetDateTime offsetDateTime = zonedDateTime.toOffsetDateTime();
            long instant = DateConvert.toLong(offsetDateTime);
            assertEquals(instant, var);
        }
    }

    @Test
    void testToPrimitiveLong() {
        {
            startingOf("// long long = DateConvert.toPrimitiveLong(java.time.Instant);");
            final long var = System.currentTimeMillis();
            long instant = DateConvert.toPrimitiveLong(Instant.ofEpochMilli(var));
            assertEquals(instant, var);
        }
        {
            startingOf("// long long = DateConvert.toPrimitiveLong(java.time.ZonedDateTime);");
            final long var = System.currentTimeMillis();
            ZonedDateTime zonedDateTime = Instant.ofEpochMilli(var).atZone(ZoneId.systemDefault());
            long instant = DateConvert.toPrimitiveLong(zonedDateTime);
            assertEquals(instant, var);
        }
        {
            startingOf("// long long = DateConvert.toPrimitiveLong(java.time.OffsetDateTime);");
            final long var = System.currentTimeMillis();
            ZonedDateTime zonedDateTime = Instant.ofEpochMilli(var).atZone(ZoneId.systemDefault());
            OffsetDateTime offsetDateTime = zonedDateTime.toOffsetDateTime();
            long instant = DateConvert.toPrimitiveLong(offsetDateTime);
            assertEquals(instant, var);
        }
        {
            startingOf("// long long = DateConvert.toPrimitiveLong(java.util.Date);");
            final long var = System.currentTimeMillis();
            long instant = DateConvert.toPrimitiveLong(new Date(var));
            assertEquals(instant, var);
        }
        {
            startingOf("// long long = DateConvert.toPrimitiveLong(java.util.Calendar);");
            final long var = System.currentTimeMillis();
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(var);
            long instant = DateConvert.toPrimitiveLong(calendar);
            assertEquals(instant, var);
        }
        {
            startingOf("// long long = DateConvert.toPrimitiveLong(java.sql.Timestamp);");
            final long var = System.currentTimeMillis();
            long instant = DateConvert.toPrimitiveLong(new java.sql.Timestamp(var));
            assertEquals(instant, var);
        }
        {
            startingOf("// long long = DateConvert.toPrimitiveLong(java.sql.Date);");
            final long var = System.currentTimeMillis();
            long instant = DateConvert.toPrimitiveLong(new java.sql.Date(var));
            assertEquals(instant, var);
        }
        {
            // ?? 过程有问题啊
            startingOf("// long long = DateConvert.toPrimitiveLong(java.time.YearMonth);");
            final long var = System.currentTimeMillis();
            ZonedDateTime zonedDateTime = Instant.ofEpochMilli(var).atZone(ZoneId.systemDefault());
            YearMonth yearMonth = YearMonth.from(zonedDateTime);
            String yearMonthString = new SimpleDateFormat(SOURCE_yyyy_MM).format(new Date(var));
            assertEquals(yyyy_MM.format(yearMonth), yearMonthString);
        }
        {
            startingOf("// long long = DateConvert.toPrimitiveLong(java.time.LocalDate);");
            final long var = System.currentTimeMillis();
            LocalDate localDate = new java.sql.Date(var).toLocalDate();
            String yearMonthDay = new SimpleDateFormat(SOURCE_yyyy_MM_dd).format(var);
            assertEquals(yearMonthDay, yyyy_MM_dd.format(localDate));
        }
        {
            startingOf("// long long = DateConvert.toPrimitiveLong(java.time.LocalDateTime);");
            final long var = System.currentTimeMillis();
            LocalDateTime localDateTime = Instant.ofEpochMilli(var).atZone(ZoneId.systemDefault()).toLocalDateTime();
            long instant = DateConvert.toPrimitiveLong(localDateTime);
            assertEquals(instant, var);
        }
    }

    @Test
    void testToYearNumber() {
        startingOf("// Integer integer = DateConvert.toYearNumber(java.time.Year);");
        final long var = System.currentTimeMillis();
        Integer year = DateConvert.toYearNumber(Year.of(Instant.ofEpochMilli(var)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
            .getYear()));
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(var);
        assertEquals(year, calendar.get(Calendar.YEAR));
    }

    @Test
    void testToJdk8LocalTime() {
        {
            startingOf("// LocalTime localTime = DateConvert.toJdk8LocalTime(java.time.LocalDateTime);");
            final long var = System.currentTimeMillis();
            ZonedDateTime zonedDateTime = Instant.ofEpochMilli(var).atZone(ZoneId.systemDefault());
            LocalDateTime localDateTime = zonedDateTime.toLocalDateTime();
            LocalTime localTime = DateConvert.toJdk8LocalTime(localDateTime);
            String time1 = new SimpleDateFormat(SOURCE_HHmmss).format(new java.sql.Time(var));
            String time2 = HHmmss.format(localTime);
            assertEquals(time1, time2);
        }
        {
            startingOf("// LocalTime localTime = DateConvert.toJdk8LocalTime(java.sql.Time);");
            final long var = System.currentTimeMillis();
            java.sql.Time sqlTime = new java.sql.Time(var);
            LocalTime localTime = DateConvert.toJdk8LocalTime(sqlTime);
            String time1 = new SimpleDateFormat(SOURCE_HHmmss).format(sqlTime);
            String time2 = HHmmss.format(localTime);
            assertEquals(time1, time2);
        }
        {
            startingOf("// LocalTime localTime = DateConvert.toJdk8LocalTime(java.sql.Timestamp);");
            final long var = System.currentTimeMillis();
            java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(var);
            LocalTime localTime = DateConvert.toJdk8LocalTime(sqlTimestamp);
            String time1 = new SimpleDateFormat(SOURCE_HHmmss).format(sqlTimestamp);
            String time2 = HHmmss.format(localTime);
            assertEquals(time1, time2);
        }
        {
            startingOf("// LocalTime localTime = DateConvert.toJdk8LocalTime(java.lang.Long);");
            final long var = System.currentTimeMillis();
            LocalTime localTime = DateConvert.toJdk8LocalTime(var);
            String time1 = new SimpleDateFormat(SOURCE_HHmmss).format(new Date(var));
            String time2 = HHmmss.format(localTime);
            assertEquals(time1, time2);
        }
        {
            startingOf("// LocalTime localTime = DateConvert.toJdk8LocalTime(long);");
            final long var = System.currentTimeMillis();
            LocalTime localTime = DateConvert.toJdk8LocalTime(var);
            String time1 = new SimpleDateFormat(SOURCE_HHmmss).format(new Date(var));
            String time2 = HHmmss.format(localTime);
            assertEquals(time1, time2);
        }
        {
            startingOf("// LocalTime localTime = DateConvert.toJdk8LocalTime(java.time.OffsetDateTime);");
            final long var = System.currentTimeMillis();
            ZonedDateTime zonedDateTime = Instant.ofEpochMilli(var).atZone(ZoneId.systemDefault());
            OffsetDateTime offsetDateTime = zonedDateTime.toOffsetDateTime();
            LocalTime localTime = DateConvert.toJdk8LocalTime(offsetDateTime);
            assertEquals(HHmmss.format(offsetDateTime), HHmmss.format(localTime));
        }
        {
            startingOf("// LocalTime localTime = DateConvert.toJdk8LocalTime(java.time.Instant);");
            final long var = System.currentTimeMillis();
            Instant instant = Instant.ofEpochMilli(var);
            LocalTime localTime = DateConvert.toJdk8LocalTime(instant);
            assertEquals(HHmmss.format(instant.atZone(ZoneId.systemDefault())), HHmmss.format(localTime));
        }
        {
            startingOf("// LocalTime localTime = DateConvert.toJdk8LocalTime(java.time.ZonedDateTime);");

            final long var = System.currentTimeMillis();
            ZonedDateTime zonedDateTime = Instant.ofEpochMilli(var).atZone(ZoneId.systemDefault());
            LocalTime localTime = DateConvert.toJdk8LocalTime(zonedDateTime);
            assertEquals(HHmmss.format(zonedDateTime), HHmmss.format(localTime));
        }
        {
            startingOf("// LocalTime localTime = DateConvert.toJdk8LocalTime(java.util.Date);");
            final long var = System.currentTimeMillis();
            Date utilDate = new Date(var);
            String time1 = new SimpleDateFormat(SOURCE_HHmmss).format(utilDate);
            LocalTime localTime = DateConvert.toJdk8LocalTime(utilDate);
            assertEquals(time1, HHmmss.format(localTime));
        }
        {
            startingOf("// LocalTime localTime = DateConvert.toJdk8LocalTime(java.util.Calendar);");
            final long var = System.currentTimeMillis();
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(var);
            LocalTime localTime = DateConvert.toJdk8LocalTime(calendar);
            String time1 = new SimpleDateFormat(SOURCE_HHmmss).format(calendar.getTime());
            assertEquals(time1, HHmmss.format(localTime));
        }
    }

    @Test
    void testToJdk8Instant() {
        {
            startingOf("// Instant instant = DateConvert.toJdk8Instant(java.util.Calendar);");
            final long var = System.currentTimeMillis();
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(var);
            Instant instant = DateConvert.toJdk8Instant(calendar);
            assertEquals(instant.toEpochMilli(), var);
        }
        {
            startingOf("// Instant instant = DateConvert.toJdk8Instant(java.sql.Date);");
            final long var = System.currentTimeMillis();
            Instant instant = DateConvert.toJdk8Instant(new java.sql.Date(var));
            assertEquals(instant.toEpochMilli(), var);
        }
        {
            startingOf("// Instant instant = DateConvert.toJdk8Instant(java.util.Date);");
            final long var = System.currentTimeMillis();
            Instant instant = DateConvert.toJdk8Instant(new Date(var));
            assertEquals(instant.toEpochMilli(), var);
        }
        {
            startingOf("// Instant instant = DateConvert.toJdk8Instant(java.sql.Timestamp);");
            final long var = System.currentTimeMillis();
            Instant instant = DateConvert.toJdk8Instant(new java.sql.Timestamp(var));
            assertEquals(instant.toEpochMilli(), var);
        }
        {
            startingOf("// Instant instant = DateConvert.toJdk8Instant(java.lang.Long);");
            final long var = System.currentTimeMillis();
            Instant instant = DateConvert.toJdk8Instant(var);
            assertEquals(instant.toEpochMilli(), var);
        }
        {
            startingOf("// Instant instant = DateConvert.toJdk8Instant(long);");
            final long var = System.currentTimeMillis();
            Instant instant = DateConvert.toJdk8Instant(var);
            assertEquals(instant.toEpochMilli(), var);
        }
        {
            startingOf("// Instant instant = DateConvert.toJdk8Instant(java.time.YearMonth);");
            final long var = System.currentTimeMillis();
            java.sql.Date sqlDate = new java.sql.Date(var);
            LocalDate localDate = sqlDate.toLocalDate();
            YearMonth yearMonth = YearMonth.from(localDate);
            Instant instant = DateConvert.toJdk8Instant(yearMonth);
            ZonedDateTime zonedDateTime = instant.atZone(ZoneId.systemDefault());
            String stringify = yyyy_MM_dd_HHmmss_SSS.format(zonedDateTime);
            String yyyyMM = new SimpleDateFormat(SOURCE_yyyy_MM).format(sqlDate);
            String dateSimpleString = yyyyMM + "-01 00:00:00 000";
            assertEquals(stringify, dateSimpleString);
        }
        {
            startingOf("// Instant instant = DateConvert.toJdk8Instant(java.time.LocalDate);");
            final long var = System.currentTimeMillis();
            java.sql.Date sqlDate = new java.sql.Date(var);
            LocalDate localDate = sqlDate.toLocalDate();
            Instant instant = DateConvert.toJdk8Instant(localDate);
            String stringify = yyyy_MM_dd_HHmmss_SSS.format(instant.atZone(ZoneId.systemDefault()));
            String yyyyMM = new SimpleDateFormat(SOURCE_yyyy_MM_dd).format(sqlDate);
            String dateSimpleString = yyyyMM + " 00:00:00 000";
            assertEquals(stringify, dateSimpleString);
        }
        {
            startingOf("// Instant instant = DateConvert.toJdk8Instant(java.time.LocalDateTime);");
            LocalDateTime localDateTime = LocalDateTime.now();
            Instant instant = DateConvert.toJdk8Instant(localDateTime);
            ZonedDateTime zonedDateTime = instant.atZone(ZoneId.systemDefault());
            assertEquals(yyyy_MM_dd_HHmmss_SSS.format(zonedDateTime), yyyy_MM_dd_HHmmss_SSS.format(localDateTime));
        }
        {
            startingOf("// Instant instant = DateConvert.toJdk8Instant(java.time.OffsetDateTime);");
            final long var = System.currentTimeMillis();
            OffsetDateTime offsetDateTime = Instant.ofEpochMilli(var).atZone(ZoneId.systemDefault()).toOffsetDateTime();
            Instant instant = DateConvert.toJdk8Instant(offsetDateTime);
            assertEquals(instant.toEpochMilli(), var);
        }
        {
            startingOf("// Instant instant = DateConvert.toJdk8Instant(java.time.ZonedDateTime);");
            final long var = System.currentTimeMillis();
            ZonedDateTime zonedDateTime = Instant.ofEpochMilli(var).atZone(ZoneId.systemDefault());
            Instant instant = DateConvert.toJdk8Instant(zonedDateTime);
            assertEquals(instant.toEpochMilli(), var);
        }
    }

    @Test
    void testToJdk8Month() {
        {
            startingOf("// Month month = DateConvert.toJdk8Month(java.sql.Date);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Month month = DateConvert.toJdk8Month(java.lang.Long);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Month month = DateConvert.toJdk8Month(java.time.MonthDay);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Month month = DateConvert.toJdk8Month(java.time.YearMonth);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Month month = DateConvert.toJdk8Month(java.sql.Timestamp);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Month month = DateConvert.toJdk8Month(java.time.LocalDate);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Month month = DateConvert.toJdk8Month(int);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Month month = DateConvert.toJdk8Month(java.lang.Integer);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Month month = DateConvert.toJdk8Month(long);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Month month = DateConvert.toJdk8Month(java.time.LocalDateTime);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Month month = DateConvert.toJdk8Month(java.time.ZonedDateTime);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Month month = DateConvert.toJdk8Month(java.time.Instant);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Month month = DateConvert.toJdk8Month(java.util.Date);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Month month = DateConvert.toJdk8Month(java.util.Calendar);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Month month = DateConvert.toJdk8Month(java.time.OffsetDateTime);");
            final long var = System.currentTimeMillis();
        }
    }

    @Test
    void testToJdk8LocalDateTime() {
        {
            startingOf("// LocalDateTime localDateTime = DateConvert.toJdk8LocalDateTime(java.sql.Date);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// LocalDateTime localDateTime = DateConvert.toJdk8LocalDateTime(java.time.YearMonth);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// LocalDateTime localDateTime = DateConvert.toJdk8LocalDateTime(java.util.Date);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// LocalDateTime localDateTime = DateConvert.toJdk8LocalDateTime(java.time.Instant);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// LocalDateTime localDateTime = DateConvert.toJdk8LocalDateTime(java.time.ZonedDateTime);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// LocalDateTime localDateTime = DateConvert.toJdk8LocalDateTime(java.time.OffsetDateTime);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// LocalDateTime localDateTime = DateConvert.toJdk8LocalDateTime(java.time.LocalDate);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// LocalDateTime localDateTime = DateConvert.toJdk8LocalDateTime(long);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// LocalDateTime localDateTime = DateConvert.toJdk8LocalDateTime(java.lang.Long);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// LocalDateTime localDateTime = DateConvert.toJdk8LocalDateTime(java.sql.Timestamp);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// LocalDateTime localDateTime = DateConvert.toJdk8LocalDateTime(java.util.Calendar);");
            final long var = System.currentTimeMillis();
        }
    }

    @Test
    void testToMonthValue() {
        startingOf("// int int = DateConvert.toMonthValue(java.time.Month);");
        final long var = System.currentTimeMillis();
    }

    @Test
    void testToSqlDate() {
        {
            startingOf("// Date date = DateConvert.toSqlDate(java.util.Date);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Date date = DateConvert.toSqlDate(java.util.Calendar);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Date date = DateConvert.toSqlDate(java.time.Instant);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Date date = DateConvert.toSqlDate(java.time.ZonedDateTime);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Date date = DateConvert.toSqlDate(java.sql.Timestamp);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Date date = DateConvert.toSqlDate(java.lang.Long);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Date date = DateConvert.toSqlDate(long);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Date date = DateConvert.toSqlDate(java.time.YearMonth);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Date date = DateConvert.toSqlDate(java.time.LocalDate);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Date date = DateConvert.toSqlDate(java.time.LocalDateTime);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Date date = DateConvert.toSqlDate(java.time.OffsetDateTime);");
            final long var = System.currentTimeMillis();
        }
    }

    @Test
    void testToJdk8Year() {
        {
            startingOf("// Year year = DateConvert.toJdk8Year(java.sql.Date);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Year year = DateConvert.toJdk8Year(java.util.Calendar);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Year year = DateConvert.toJdk8Year(java.util.Date);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Year year = DateConvert.toJdk8Year(java.time.Instant);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Year year = DateConvert.toJdk8Year(java.time.ZonedDateTime);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Year year = DateConvert.toJdk8Year(java.time.OffsetDateTime);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Year year = DateConvert.toJdk8Year(int);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Year year = DateConvert.toJdk8Year(java.lang.Integer);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Year year = DateConvert.toJdk8Year(long);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Year year = DateConvert.toJdk8Year(java.lang.Long);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Year year = DateConvert.toJdk8Year(java.time.YearMonth);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Year year = DateConvert.toJdk8Year(java.sql.Timestamp);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Year year = DateConvert.toJdk8Year(java.time.LocalDateTime);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Year year = DateConvert.toJdk8Year(java.time.LocalDate);");
            final long var = System.currentTimeMillis();
        }
    }

    @Test
    void testToYearValue() {
        startingOf("// int int = DateConvert.toYearValue(java.time.Year);");
        final long var = System.currentTimeMillis();
        Integer year = DateConvert.toYearNumber(Year.of(Instant.ofEpochMilli(var)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
            .getYear()));
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(var);
        assertEquals(year, calendar.get(Calendar.YEAR));
    }

    @Test
    void testToSqlTimestamp() {
        {
            startingOf("// Timestamp timestamp = DateConvert.toSqlTimestamp(java.time.OffsetDateTime);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Timestamp timestamp = DateConvert.toSqlTimestamp(java.time.LocalDateTime);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Timestamp timestamp = DateConvert.toSqlTimestamp(java.time.LocalDate);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Timestamp timestamp = DateConvert.toSqlTimestamp(java.time.YearMonth);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Timestamp timestamp = DateConvert.toSqlTimestamp(java.util.Calendar);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Timestamp timestamp = DateConvert.toSqlTimestamp(java.sql.Date);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Timestamp timestamp = DateConvert.toSqlTimestamp(java.lang.Long);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Timestamp timestamp = DateConvert.toSqlTimestamp(long);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Timestamp timestamp = DateConvert.toSqlTimestamp(java.util.Date);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Timestamp timestamp = DateConvert.toSqlTimestamp(java.time.Instant);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Timestamp timestamp = DateConvert.toSqlTimestamp(java.time.ZonedDateTime);");
            final long var = System.currentTimeMillis();
        }
    }

    @Test
    void testToMonthNumber() {
        startingOf("// Integer integer = DateConvert.toMonthNumber(java.time.Month);");
        final long var = System.currentTimeMillis();
    }

    @Test
    void testToSqlTime() {
        {
            startingOf("// Time time = DateConvert.toSqlTime(java.time.ZonedDateTime);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Time time = DateConvert.toSqlTime(java.time.OffsetDateTime);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Time time = DateConvert.toSqlTime(java.time.Instant);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Time time = DateConvert.toSqlTime(java.time.LocalDateTime);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Time time = DateConvert.toSqlTime(java.time.LocalTime);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Time time = DateConvert.toSqlTime(long);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Time time = DateConvert.toSqlTime(java.lang.Long);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Time time = DateConvert.toSqlTime(java.sql.Timestamp);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Time time = DateConvert.toSqlTime(java.util.Calendar);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Time time = DateConvert.toSqlTime(java.util.Date);");
            final long var = System.currentTimeMillis();
        }
    }

    @Test
    void testToUtilCalendar() {
        {
            startingOf("// Calendar calendar = DateConvert.toUtilCalendar(java.sql.Timestamp);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Calendar calendar = DateConvert.toUtilCalendar(java.sql.Date);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Calendar calendar = DateConvert.toUtilCalendar(java.util.Date);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Calendar calendar = DateConvert.toUtilCalendar(java.lang.Long);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Calendar calendar = DateConvert.toUtilCalendar(long);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Calendar calendar = DateConvert.toUtilCalendar(java.time.YearMonth);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Calendar calendar = DateConvert.toUtilCalendar(java.time.LocalDate);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Calendar calendar = DateConvert.toUtilCalendar(java.time.LocalDateTime);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Calendar calendar = DateConvert.toUtilCalendar(java.time.OffsetDateTime);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Calendar calendar = DateConvert.toUtilCalendar(java.time.ZonedDateTime);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// Calendar calendar = DateConvert.toUtilCalendar(java.time.Instant);");
            final long var = System.currentTimeMillis();
        }
    }

    @Test
    void testToJdk8YearMonth() {
        {
            startingOf("// YearMonth yearMonth = DateConvert.toJdk8YearMonth(java.time.LocalDateTime);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// YearMonth yearMonth = DateConvert.toJdk8YearMonth(java.time.OffsetDateTime);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// YearMonth yearMonth = DateConvert.toJdk8YearMonth(java.time.ZonedDateTime);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// YearMonth yearMonth = DateConvert.toJdk8YearMonth(java.time.Instant);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// YearMonth yearMonth = DateConvert.toJdk8YearMonth(java.util.Date);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// YearMonth yearMonth = DateConvert.toJdk8YearMonth(java.util.Calendar);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// YearMonth yearMonth = DateConvert.toJdk8YearMonth(java.sql.Date);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// YearMonth yearMonth = DateConvert.toJdk8YearMonth(long);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// YearMonth yearMonth = DateConvert.toJdk8YearMonth(java.lang.Long);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// YearMonth yearMonth = DateConvert.toJdk8YearMonth(java.sql.Timestamp);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// YearMonth yearMonth = DateConvert.toJdk8YearMonth(java.time.LocalDate);");
            final long var = System.currentTimeMillis();
        }
    }

    @Test
    void testToJdk8ZonedDateTime() {
        {
            startingOf("// ZonedDateTime zonedDateTime = DateConvert.toJdk8ZonedDateTime(java.sql.Timestamp);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// ZonedDateTime zonedDateTime = DateConvert.toJdk8ZonedDateTime(java.sql.Date);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// ZonedDateTime zonedDateTime = DateConvert.toJdk8ZonedDateTime(java.util.Calendar);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// ZonedDateTime zonedDateTime = DateConvert.toJdk8ZonedDateTime(java.lang.Long);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// ZonedDateTime zonedDateTime = DateConvert.toJdk8ZonedDateTime(long);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// ZonedDateTime zonedDateTime = DateConvert.toJdk8ZonedDateTime(java.time.LocalDateTime);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// ZonedDateTime zonedDateTime = DateConvert.toJdk8ZonedDateTime(java.time.YearMonth);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// ZonedDateTime zonedDateTime = DateConvert.toJdk8ZonedDateTime(java.time.LocalDate);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// ZonedDateTime zonedDateTime = DateConvert.toJdk8ZonedDateTime(java.time.OffsetDateTime);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// ZonedDateTime zonedDateTime = DateConvert.toJdk8ZonedDateTime(java.time.Instant);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// ZonedDateTime zonedDateTime = DateConvert.toJdk8ZonedDateTime(java.util.Date);");
            final long var = System.currentTimeMillis();
        }
    }

    @Test
    void testToJdk8OffsetDateTime() {
        {
            startingOf("// OffsetDateTime offsetDateTime = DateConvert.toJdk8OffsetDateTime(java.time.ZonedDateTime);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// OffsetDateTime offsetDateTime = DateConvert.toJdk8OffsetDateTime(java.time.Instant);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// OffsetDateTime offsetDateTime = DateConvert.toJdk8OffsetDateTime(java.util.Date);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// OffsetDateTime offsetDateTime = DateConvert.toJdk8OffsetDateTime(java.util.Calendar);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// OffsetDateTime offsetDateTime = DateConvert.toJdk8OffsetDateTime(java.time.YearMonth);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// OffsetDateTime offsetDateTime = DateConvert.toJdk8OffsetDateTime(java.time.LocalDate);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// OffsetDateTime offsetDateTime = DateConvert.toJdk8OffsetDateTime(java.time.LocalDateTime);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// OffsetDateTime offsetDateTime = DateConvert.toJdk8OffsetDateTime(long);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// OffsetDateTime offsetDateTime = DateConvert.toJdk8OffsetDateTime(java.sql.Timestamp);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// OffsetDateTime offsetDateTime = DateConvert.toJdk8OffsetDateTime(java.sql.Date);");
            final long var = System.currentTimeMillis();
        }
        {
            startingOf("// OffsetDateTime offsetDateTime = DateConvert.toJdk8OffsetDateTime(java.lang.Long);");
            final long var = System.currentTimeMillis();
        }
    }
}

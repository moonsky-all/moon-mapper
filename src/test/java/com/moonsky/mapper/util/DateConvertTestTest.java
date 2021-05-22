package com.moonsky.mapper.util;

import org.joda.time.DateTime;
import org.joda.time.MutableDateTime;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.*;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author benshaoye
 */
class DateConvertTestTest {

    @Test
    void testToJdk8LocalDate() {
        assertNotNull(Joda1xConvert.toJdk8LocalDate(Instant.now()));
        assertNotNull(Joda1xConvert.toJdk8LocalDate(OffsetDateTime.now()));
        assertNotNull(Joda1xConvert.toJdk8LocalDate(ZonedDateTime.now()));
        assertNotNull(Joda1xConvert.toJdk8LocalDate(LocalDateTime.now()));
        assertNotNull(Joda1xConvert.toJdk8LocalDate(new Date()));
        assertNotNull(Joda1xConvert.toJdk8LocalDate(new java.sql.Date(System.currentTimeMillis())));
        assertNotNull(Joda1xConvert.toJdk8LocalDate(new Timestamp(System.currentTimeMillis())));
        assertNotNull(Joda1xConvert.toJdk8LocalDate(Calendar.getInstance()));
        assertNotNull(Joda1xConvert.toJdk8LocalDate(new DateTime()));
        assertNotNull(Joda1xConvert.toJdk8LocalDate(new org.joda.time.Instant()));
        assertNotNull(Joda1xConvert.toJdk8LocalDate(new org.joda.time.LocalDate()));
        assertNotNull(Joda1xConvert.toJdk8LocalDate(new org.joda.time.LocalDateTime()));
        assertNotNull(Joda1xConvert.toJdk8LocalDate(new MutableDateTime()));
        assertNotNull(Joda1xConvert.toJdk8LocalDate(System.currentTimeMillis()));
        assertNotNull(Joda1xConvert.toJdk8LocalDate(System.currentTimeMillis()));
    }

    @Test
    void testToJdk8LocalTime() {
        assertNotNull(Joda1xConvert.toJdk8LocalTime(Instant.now()));
        assertNotNull(Joda1xConvert.toJdk8LocalTime(OffsetDateTime.now()));
        assertNotNull(Joda1xConvert.toJdk8LocalTime(ZonedDateTime.now()));
        assertNotNull(Joda1xConvert.toJdk8LocalTime(LocalDateTime.now()));
        assertNotNull(Joda1xConvert.toJdk8LocalTime(new Date()));
        // assertNotNull(JodaConvert.toJdk8LocalTime(new java.sql.Date(System.currentTimeMillis())));
        assertNotNull(Joda1xConvert.toJdk8LocalTime(new java.sql.Time(System.currentTimeMillis())));
        assertNotNull(Joda1xConvert.toJdk8LocalTime(new Timestamp(System.currentTimeMillis())));
        assertNotNull(Joda1xConvert.toJdk8LocalTime(Calendar.getInstance()));
        assertNotNull(Joda1xConvert.toJdk8LocalTime(new DateTime()));
        assertNotNull(Joda1xConvert.toJdk8LocalTime(new org.joda.time.Instant()));
        assertNotNull(Joda1xConvert.toJdk8LocalTime(new org.joda.time.LocalTime()));
        assertNotNull(Joda1xConvert.toJdk8LocalTime(new org.joda.time.LocalDateTime()));
        assertNotNull(Joda1xConvert.toJdk8LocalTime(new MutableDateTime()));
        assertNotNull(Joda1xConvert.toJdk8LocalTime(System.currentTimeMillis()));
        assertNotNull(Joda1xConvert.toJdk8LocalTime(System.currentTimeMillis()));
    }

    @Test
    void testToJdk8LocalDateTime() {
        assertNotNull(Joda1xConvert.toJdk8LocalDateTime(LocalDate.now()));
        assertNotNull(Joda1xConvert.toJdk8LocalDateTime(Instant.now()));
        assertNotNull(Joda1xConvert.toJdk8LocalDateTime(OffsetDateTime.now()));
        assertNotNull(Joda1xConvert.toJdk8LocalDateTime(ZonedDateTime.now()));
        // assertNotNull(JodaConvert.toJdk8LocalDateTime(LocalDateTime.now()));
        assertNotNull(Joda1xConvert.toJdk8LocalDateTime(new Date()));
        assertNotNull(Joda1xConvert.toJdk8LocalDateTime(new java.sql.Date(System.currentTimeMillis())));
        assertNotNull(Joda1xConvert.toJdk8LocalDateTime(new Timestamp(System.currentTimeMillis())));
        assertNotNull(Joda1xConvert.toJdk8LocalDateTime(Calendar.getInstance()));
        assertNotNull(Joda1xConvert.toJdk8LocalDateTime(new DateTime()));
        assertNotNull(Joda1xConvert.toJdk8LocalDateTime(new org.joda.time.Instant()));
        assertNotNull(Joda1xConvert.toJdk8LocalDateTime(new org.joda.time.LocalDate()));
        assertNotNull(Joda1xConvert.toJdk8LocalDateTime(new org.joda.time.LocalDateTime()));
        assertNotNull(Joda1xConvert.toJdk8LocalDateTime(new MutableDateTime()));
        assertNotNull(Joda1xConvert.toJdk8LocalDateTime(System.currentTimeMillis()));
        assertNotNull(Joda1xConvert.toJdk8LocalDateTime(System.currentTimeMillis()));
    }

    @Test
    void testToJdk8OffsetDateTime() {
        assertNotNull(Joda1xConvert.toJdk8OffsetDateTime(LocalDate.now()));
        assertNotNull(Joda1xConvert.toJdk8OffsetDateTime(Instant.now()));
        // assertNotNull(JodaConvert.toJdk8OffsetDateTime(OffsetDateTime.now()));
        assertNotNull(Joda1xConvert.toJdk8OffsetDateTime(ZonedDateTime.now()));
        assertNotNull(Joda1xConvert.toJdk8OffsetDateTime(LocalDateTime.now()));
        assertNotNull(Joda1xConvert.toJdk8OffsetDateTime(new Date()));
        assertNotNull(Joda1xConvert.toJdk8OffsetDateTime(new java.sql.Date(System.currentTimeMillis())));
        assertNotNull(Joda1xConvert.toJdk8OffsetDateTime(new Timestamp(System.currentTimeMillis())));
        assertNotNull(Joda1xConvert.toJdk8OffsetDateTime(Calendar.getInstance()));
        assertNotNull(Joda1xConvert.toJdk8OffsetDateTime(new DateTime()));
        assertNotNull(Joda1xConvert.toJdk8OffsetDateTime(new org.joda.time.Instant()));
        assertNotNull(Joda1xConvert.toJdk8OffsetDateTime(new org.joda.time.LocalDate()));
        assertNotNull(Joda1xConvert.toJdk8OffsetDateTime(new org.joda.time.LocalDateTime()));
        assertNotNull(Joda1xConvert.toJdk8OffsetDateTime(new MutableDateTime()));
        assertNotNull(Joda1xConvert.toJdk8OffsetDateTime(System.currentTimeMillis()));
        assertNotNull(Joda1xConvert.toJdk8OffsetDateTime(System.currentTimeMillis()));
    }

    @Test
    void testToJdk8ZonedDateTime() {
        assertNotNull(Joda1xConvert.toJdk8ZonedDateTime(LocalDate.now()));
        assertNotNull(Joda1xConvert.toJdk8ZonedDateTime(Instant.now()));
        assertNotNull(Joda1xConvert.toJdk8ZonedDateTime(OffsetDateTime.now()));
        // assertNotNull(JodaConvert.toJdk8ZonedDateTime(ZonedDateTime.now()));
        assertNotNull(Joda1xConvert.toJdk8ZonedDateTime(LocalDateTime.now()));
        assertNotNull(Joda1xConvert.toJdk8ZonedDateTime(new Date()));
        assertNotNull(Joda1xConvert.toJdk8ZonedDateTime(new java.sql.Date(System.currentTimeMillis())));
        assertNotNull(Joda1xConvert.toJdk8ZonedDateTime(new Timestamp(System.currentTimeMillis())));
        assertNotNull(Joda1xConvert.toJdk8ZonedDateTime(Calendar.getInstance()));
        assertNotNull(Joda1xConvert.toJdk8ZonedDateTime(new DateTime()));
        assertNotNull(Joda1xConvert.toJdk8ZonedDateTime(new org.joda.time.Instant()));
        assertNotNull(Joda1xConvert.toJdk8ZonedDateTime(new org.joda.time.LocalDate()));
        assertNotNull(Joda1xConvert.toJdk8ZonedDateTime(new org.joda.time.LocalDateTime()));
        assertNotNull(Joda1xConvert.toJdk8ZonedDateTime(new MutableDateTime()));
        assertNotNull(Joda1xConvert.toJdk8ZonedDateTime(System.currentTimeMillis()));
        assertNotNull(Joda1xConvert.toJdk8ZonedDateTime(System.currentTimeMillis()));
    }

    @Test
    void testToJdk8Instant() {
        assertNotNull(Joda1xConvert.toJdk8Instant(LocalDate.now()));
        // assertNotNull(JodaConvert.toJdk8Instant(Instant.now()));
        assertNotNull(Joda1xConvert.toJdk8Instant(OffsetDateTime.now()));
        assertNotNull(Joda1xConvert.toJdk8Instant(ZonedDateTime.now()));
        assertNotNull(Joda1xConvert.toJdk8Instant(LocalDateTime.now()));
        assertNotNull(Joda1xConvert.toJdk8Instant(new Date()));
        assertNotNull(Joda1xConvert.toJdk8Instant(new java.sql.Date(System.currentTimeMillis())));
        assertNotNull(Joda1xConvert.toJdk8Instant(new Timestamp(System.currentTimeMillis())));
        assertNotNull(Joda1xConvert.toJdk8Instant(Calendar.getInstance()));
        assertNotNull(Joda1xConvert.toJdk8Instant(new DateTime()));
        assertNotNull(Joda1xConvert.toJdk8Instant(new org.joda.time.Instant()));
        assertNotNull(Joda1xConvert.toJdk8Instant(new org.joda.time.LocalDate()));
        assertNotNull(Joda1xConvert.toJdk8Instant(new org.joda.time.LocalDateTime()));
        assertNotNull(Joda1xConvert.toJdk8Instant(new MutableDateTime()));
        assertNotNull(Joda1xConvert.toJdk8Instant(System.currentTimeMillis()));
        assertNotNull(Joda1xConvert.toJdk8Instant(System.currentTimeMillis()));
    }

    @Test
    void testToUtilDate() {
        assertNotNull(Joda1xConvert.toUtilDate(LocalDate.now()));
        assertNotNull(Joda1xConvert.toUtilDate(Instant.now()));
        assertNotNull(Joda1xConvert.toUtilDate(OffsetDateTime.now()));
        assertNotNull(Joda1xConvert.toUtilDate(ZonedDateTime.now()));
        assertNotNull(Joda1xConvert.toUtilDate(LocalDateTime.now()));
        // assertNotNull(JodaConvert.toUtilDate(new Date()));
        assertNotNull(Joda1xConvert.toUtilDate(new java.sql.Date(System.currentTimeMillis())));
        assertNotNull(Joda1xConvert.toUtilDate(new Timestamp(System.currentTimeMillis())));
        assertNotNull(Joda1xConvert.toUtilDate(Calendar.getInstance()));
        assertNotNull(Joda1xConvert.toUtilDate(new DateTime()));
        assertNotNull(Joda1xConvert.toUtilDate(new org.joda.time.Instant()));
        assertNotNull(Joda1xConvert.toUtilDate(new org.joda.time.LocalDate()));
        assertNotNull(Joda1xConvert.toUtilDate(new org.joda.time.LocalDateTime()));
        assertNotNull(Joda1xConvert.toUtilDate(new MutableDateTime()));
        assertNotNull(Joda1xConvert.toUtilDate(System.currentTimeMillis()));
        assertNotNull(Joda1xConvert.toUtilDate(System.currentTimeMillis()));
    }

    @Test
    void testToUtilCalendar() {
        assertNotNull(Joda1xConvert.toUtilCalendar(LocalDate.now()));
        assertNotNull(Joda1xConvert.toUtilCalendar(Instant.now()));
        assertNotNull(Joda1xConvert.toUtilCalendar(OffsetDateTime.now()));
        assertNotNull(Joda1xConvert.toUtilCalendar(ZonedDateTime.now()));
        assertNotNull(Joda1xConvert.toUtilCalendar(LocalDateTime.now()));
        assertNotNull(Joda1xConvert.toUtilCalendar(new Date()));
        assertNotNull(Joda1xConvert.toUtilCalendar(new java.sql.Date(System.currentTimeMillis())));
        assertNotNull(Joda1xConvert.toUtilCalendar(new Timestamp(System.currentTimeMillis())));
        // assertNotNull(JodaConvert.toUtilCalendar(Calendar.getInstance()));
        assertNotNull(Joda1xConvert.toUtilCalendar(new DateTime()));
        assertNotNull(Joda1xConvert.toUtilCalendar(new org.joda.time.Instant()));
        assertNotNull(Joda1xConvert.toUtilCalendar(new org.joda.time.LocalDate()));
        assertNotNull(Joda1xConvert.toUtilCalendar(new org.joda.time.LocalDateTime()));
        assertNotNull(Joda1xConvert.toUtilCalendar(new MutableDateTime()));
        assertNotNull(Joda1xConvert.toUtilCalendar(System.currentTimeMillis()));
        assertNotNull(Joda1xConvert.toUtilCalendar(System.currentTimeMillis()));
    }

    @Test
    void testToSqlDate() {
        assertNotNull(Joda1xConvert.toSqlDate(LocalDate.now()));
        assertNotNull(Joda1xConvert.toSqlDate(Instant.now()));
        assertNotNull(Joda1xConvert.toSqlDate(OffsetDateTime.now()));
        assertNotNull(Joda1xConvert.toSqlDate(ZonedDateTime.now()));
        assertNotNull(Joda1xConvert.toSqlDate(LocalDateTime.now()));
        assertNotNull(Joda1xConvert.toSqlDate(new Date()));
        assertNotNull(Joda1xConvert.toSqlDate(new java.sql.Date(System.currentTimeMillis())));
        assertNotNull(Joda1xConvert.toSqlDate(new Timestamp(System.currentTimeMillis())));
        assertNotNull(Joda1xConvert.toSqlDate(Calendar.getInstance()));
        assertNotNull(Joda1xConvert.toSqlDate(new DateTime()));
        assertNotNull(Joda1xConvert.toSqlDate(new org.joda.time.Instant()));
        assertNotNull(Joda1xConvert.toSqlDate(new org.joda.time.LocalDate()));
        assertNotNull(Joda1xConvert.toSqlDate(new org.joda.time.LocalDateTime()));
        assertNotNull(Joda1xConvert.toSqlDate(new MutableDateTime()));
        assertNotNull(Joda1xConvert.toSqlDate(System.currentTimeMillis()));
        assertNotNull(Joda1xConvert.toSqlDate(System.currentTimeMillis()));
    }

    @Test
    void testToSqlTime() {
        // assertNotNull(JodaConvert.toSqlTime(LocalDate.now()));
        assertNotNull(Joda1xConvert.toSqlTime(Instant.now()));
        assertNotNull(Joda1xConvert.toSqlTime(OffsetDateTime.now()));
        assertNotNull(Joda1xConvert.toSqlTime(ZonedDateTime.now()));
        assertNotNull(Joda1xConvert.toSqlTime(LocalDateTime.now()));
        assertNotNull(Joda1xConvert.toSqlTime(new Date()));
        assertNotNull(Joda1xConvert.toSqlTime(new java.sql.Date(System.currentTimeMillis())));
        assertNotNull(Joda1xConvert.toSqlTime(new Timestamp(System.currentTimeMillis())));
        assertNotNull(Joda1xConvert.toSqlTime(Calendar.getInstance()));
        assertNotNull(Joda1xConvert.toSqlTime(new DateTime()));
        assertNotNull(Joda1xConvert.toSqlTime(new org.joda.time.Instant()));
        // assertNotNull(JodaConvert.toSqlTime(new org.joda.time.LocalDate()));
        assertNotNull(Joda1xConvert.toSqlTime(new org.joda.time.LocalDateTime()));
        assertNotNull(Joda1xConvert.toSqlTime(new MutableDateTime()));
        assertNotNull(Joda1xConvert.toSqlTime(System.currentTimeMillis()));
        assertNotNull(Joda1xConvert.toSqlTime(System.currentTimeMillis()));
    }

    @Test
    void testToSqlTimestamp() {
        assertNotNull(Joda1xConvert.toSqlTimestamp(LocalDate.now()));
        assertNotNull(Joda1xConvert.toSqlTimestamp(Instant.now()));
        assertNotNull(Joda1xConvert.toSqlTimestamp(OffsetDateTime.now()));
        assertNotNull(Joda1xConvert.toSqlTimestamp(ZonedDateTime.now()));
        assertNotNull(Joda1xConvert.toSqlTimestamp(LocalDateTime.now()));
        assertNotNull(Joda1xConvert.toSqlTimestamp(new Date()));
        assertNotNull(Joda1xConvert.toSqlTimestamp(new java.sql.Date(System.currentTimeMillis())));
        assertNotNull(Joda1xConvert.toSqlTimestamp(new Timestamp(System.currentTimeMillis())));
        assertNotNull(Joda1xConvert.toSqlTimestamp(Calendar.getInstance()));
        assertNotNull(Joda1xConvert.toSqlTimestamp(new DateTime()));
        assertNotNull(Joda1xConvert.toSqlTimestamp(new org.joda.time.Instant()));
        assertNotNull(Joda1xConvert.toSqlTimestamp(new org.joda.time.LocalDate()));
        assertNotNull(Joda1xConvert.toSqlTimestamp(new org.joda.time.LocalDateTime()));
        assertNotNull(Joda1xConvert.toSqlTimestamp(new MutableDateTime()));
        assertNotNull(Joda1xConvert.toSqlTimestamp(System.currentTimeMillis()));
        assertNotNull(Joda1xConvert.toSqlTimestamp(System.currentTimeMillis()));
    }

    @Test
    void testToJodaDateTime() {
        assertNotNull(Joda1xConvert.toJodaDateTime(LocalDate.now()));
        assertNotNull(Joda1xConvert.toJodaDateTime(Instant.now()));
        assertNotNull(Joda1xConvert.toJodaDateTime(OffsetDateTime.now()));
        assertNotNull(Joda1xConvert.toJodaDateTime(ZonedDateTime.now()));
        assertNotNull(Joda1xConvert.toJodaDateTime(LocalDateTime.now()));
        assertNotNull(Joda1xConvert.toJodaDateTime(new Date()));
        assertNotNull(Joda1xConvert.toJodaDateTime(new java.sql.Date(System.currentTimeMillis())));
        assertNotNull(Joda1xConvert.toJodaDateTime(new Timestamp(System.currentTimeMillis())));
        assertNotNull(Joda1xConvert.toJodaDateTime(Calendar.getInstance()));
        // assertNotNull(JodaConvert.toJodaDateTime(new DateTime()));
        assertNotNull(Joda1xConvert.toJodaDateTime(new org.joda.time.Instant()));
        assertNotNull(Joda1xConvert.toJodaDateTime(new org.joda.time.LocalDate()));
        assertNotNull(Joda1xConvert.toJodaDateTime(new org.joda.time.LocalDateTime()));
        assertNotNull(Joda1xConvert.toJodaDateTime(new MutableDateTime()));
        assertNotNull(Joda1xConvert.toJodaDateTime(System.currentTimeMillis()));
        assertNotNull(Joda1xConvert.toJodaDateTime(System.currentTimeMillis()));
    }

    @Test
    void testToJodaLocalDate() {
        assertNotNull(Joda1xConvert.toJodaLocalDate(LocalDate.now()));
        assertNotNull(Joda1xConvert.toJodaLocalDate(Instant.now()));
        assertNotNull(Joda1xConvert.toJodaLocalDate(OffsetDateTime.now()));
        assertNotNull(Joda1xConvert.toJodaLocalDate(ZonedDateTime.now()));
        assertNotNull(Joda1xConvert.toJodaLocalDate(LocalDateTime.now()));
        assertNotNull(Joda1xConvert.toJodaLocalDate(new Date()));
        assertNotNull(Joda1xConvert.toJodaLocalDate(new java.sql.Date(System.currentTimeMillis())));
        assertNotNull(Joda1xConvert.toJodaLocalDate(new Timestamp(System.currentTimeMillis())));
        assertNotNull(Joda1xConvert.toJodaLocalDate(Calendar.getInstance()));
        assertNotNull(Joda1xConvert.toJodaLocalDate(new DateTime()));
        assertNotNull(Joda1xConvert.toJodaLocalDate(new org.joda.time.Instant()));
        // assertNotNull(JodaConvert.toJodaLocalDate(new org.joda.time.LocalDate()));
        assertNotNull(Joda1xConvert.toJodaLocalDate(new org.joda.time.LocalDateTime()));
        assertNotNull(Joda1xConvert.toJodaLocalDate(new MutableDateTime()));
        assertNotNull(Joda1xConvert.toJodaLocalDate(System.currentTimeMillis()));
        assertNotNull(Joda1xConvert.toJodaLocalDate(System.currentTimeMillis()));
    }

    @Test
    void testToJodaLocalTime() {
        // assertNotNull(JodaConvert.toJodaLocalTime(LocalDate.now()));
        assertNotNull(Joda1xConvert.toJodaLocalTime(LocalTime.now()));
        assertNotNull(Joda1xConvert.toJodaLocalTime(Instant.now()));
        assertNotNull(Joda1xConvert.toJodaLocalTime(OffsetDateTime.now()));
        assertNotNull(Joda1xConvert.toJodaLocalTime(ZonedDateTime.now()));
        assertNotNull(Joda1xConvert.toJodaLocalTime(LocalDateTime.now()));
        assertNotNull(Joda1xConvert.toJodaLocalTime(new Date()));
        // assertNotNull(JodaConvert.toJodaLocalTime(new java.sql.Date(System.currentTimeMillis())));
        assertNotNull(Joda1xConvert.toJodaLocalTime(new java.sql.Time(System.currentTimeMillis())));
        assertNotNull(Joda1xConvert.toJodaLocalTime(new Timestamp(System.currentTimeMillis())));
        assertNotNull(Joda1xConvert.toJodaLocalTime(Calendar.getInstance()));
        assertNotNull(Joda1xConvert.toJodaLocalTime(new DateTime()));
        assertNotNull(Joda1xConvert.toJodaLocalTime(new org.joda.time.Instant()));
        // assertNotNull(JodaConvert.toJodaLocalTime(new org.joda.time.LocalDate()));
        // assertNotNull(JodaConvert.toJodaLocalTime(new org.joda.time.LocalTime()));
        assertNotNull(Joda1xConvert.toJodaLocalTime(new org.joda.time.LocalDateTime()));
        assertNotNull(Joda1xConvert.toJodaLocalTime(new MutableDateTime()));
        assertNotNull(Joda1xConvert.toJodaLocalTime(System.currentTimeMillis()));
        assertNotNull(Joda1xConvert.toJodaLocalTime(System.currentTimeMillis()));
    }

    @Test
    void testToJodaLocalDateTime() {
        assertNotNull(Joda1xConvert.toJodaLocalDateTime(LocalDate.now()));
        assertNotNull(Joda1xConvert.toJodaLocalDateTime(Instant.now()));
        assertNotNull(Joda1xConvert.toJodaLocalDateTime(OffsetDateTime.now()));
        assertNotNull(Joda1xConvert.toJodaLocalDateTime(ZonedDateTime.now()));
        assertNotNull(Joda1xConvert.toJodaLocalDateTime(LocalDateTime.now()));
        assertNotNull(Joda1xConvert.toJodaLocalDateTime(new Date()));
        assertNotNull(Joda1xConvert.toJodaLocalDateTime(new java.sql.Date(System.currentTimeMillis())));
        // assertNotNull(JodaConvert.toJodaLocalDateTime(new java.sql.Time(System.currentTimeMillis())));
        assertNotNull(Joda1xConvert.toJodaLocalDateTime(new Timestamp(System.currentTimeMillis())));
        assertNotNull(Joda1xConvert.toJodaLocalDateTime(Calendar.getInstance()));
        assertNotNull(Joda1xConvert.toJodaLocalDateTime(new DateTime()));
        assertNotNull(Joda1xConvert.toJodaLocalDateTime(new org.joda.time.Instant()));
        assertNotNull(Joda1xConvert.toJodaLocalDateTime(new org.joda.time.LocalDate()));
        // assertNotNull(JodaConvert.toJodaLocalDateTime(new org.joda.time.LocalDateTime()));
        assertNotNull(Joda1xConvert.toJodaLocalDateTime(new MutableDateTime()));
        assertNotNull(Joda1xConvert.toJodaLocalDateTime(System.currentTimeMillis()));
        assertNotNull(Joda1xConvert.toJodaLocalDateTime(System.currentTimeMillis()));
    }

    @Test
    void testToJodaMutableDateTime() {
        assertNotNull(Joda1xConvert.toJodaMutableDateTime(LocalDate.now()));
        assertNotNull(Joda1xConvert.toJodaMutableDateTime(Instant.now()));
        assertNotNull(Joda1xConvert.toJodaMutableDateTime(OffsetDateTime.now()));
        assertNotNull(Joda1xConvert.toJodaMutableDateTime(ZonedDateTime.now()));
        assertNotNull(Joda1xConvert.toJodaMutableDateTime(LocalDateTime.now()));
        assertNotNull(Joda1xConvert.toJodaMutableDateTime(new Date()));
        assertNotNull(Joda1xConvert.toJodaMutableDateTime(new java.sql.Date(System.currentTimeMillis())));
        // assertNotNull(JodaConvert.toJodaMutableDateTime(new java.sql.Time(System.currentTimeMillis())));
        assertNotNull(Joda1xConvert.toJodaMutableDateTime(new Timestamp(System.currentTimeMillis())));
        assertNotNull(Joda1xConvert.toJodaMutableDateTime(Calendar.getInstance()));
        assertNotNull(Joda1xConvert.toJodaMutableDateTime(new DateTime()));
        assertNotNull(Joda1xConvert.toJodaMutableDateTime(new org.joda.time.Instant()));
        assertNotNull(Joda1xConvert.toJodaMutableDateTime(new org.joda.time.LocalDate()));
        assertNotNull(Joda1xConvert.toJodaMutableDateTime(new org.joda.time.LocalDateTime()));
        // assertNotNull(JodaConvert.toJodaMutableDateTime(new MutableDateTime()));
        assertNotNull(Joda1xConvert.toJodaMutableDateTime(System.currentTimeMillis()));
        assertNotNull(Joda1xConvert.toJodaMutableDateTime(System.currentTimeMillis()));
    }

    @Test
    void testToJodaInstant() {
        assertNotNull(Joda1xConvert.toJodaInstant(LocalDate.now()));
        assertNotNull(Joda1xConvert.toJodaInstant(Instant.now()));
        assertNotNull(Joda1xConvert.toJodaInstant(OffsetDateTime.now()));
        assertNotNull(Joda1xConvert.toJodaInstant(ZonedDateTime.now()));
        assertNotNull(Joda1xConvert.toJodaInstant(LocalDateTime.now()));
        assertNotNull(Joda1xConvert.toJodaInstant(new Date()));
        assertNotNull(Joda1xConvert.toJodaInstant(new java.sql.Date(System.currentTimeMillis())));
        // assertNotNull(JodaConvert.toJodaInstant(new java.sql.Time(System.currentTimeMillis())));
        assertNotNull(Joda1xConvert.toJodaInstant(new Timestamp(System.currentTimeMillis())));
        assertNotNull(Joda1xConvert.toJodaInstant(Calendar.getInstance()));
        assertNotNull(Joda1xConvert.toJodaInstant(new DateTime()));
        // assertNotNull(JodaConvert.toJodaInstant(new org.joda.time.Instant()));
        assertNotNull(Joda1xConvert.toJodaInstant(new org.joda.time.LocalDate()));
        assertNotNull(Joda1xConvert.toJodaInstant(new org.joda.time.LocalDateTime()));
        assertNotNull(Joda1xConvert.toJodaInstant(new MutableDateTime()));
        assertNotNull(Joda1xConvert.toJodaInstant(System.currentTimeMillis()));
        assertNotNull(Joda1xConvert.toJodaInstant(System.currentTimeMillis()));
    }
}
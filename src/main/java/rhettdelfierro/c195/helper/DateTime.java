package rhettdelfierro.c195.helper;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DateTime {
    private final static int openingHour = 8;
    private final static int closingHour = 22;
    private final static String businessZoneId = "America/New_York";

    /**
     * Converts the given time from UTC time to local time.
     * @param time the UTC time to convert. Format: yyyy-MM-dd HH:mm:ss
     * @return a String representing the converted local time.
     */
    public static String convertFromUTCToLocal(String time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(time, formatter);
        ZonedDateTime utcZonedDateTime = localDateTime.atZone(ZoneId.of("UTC"));
        ZonedDateTime localZonedDateTime = utcZonedDateTime.withZoneSameInstant(ZoneId.systemDefault());
        return localZonedDateTime.format(formatter);
    }

    /**
     * Converts the given time from local time to UTC time.
     * @param time the time to convert. Format: yyyy-MM-dd HH:mm:ss
     * @return a String representing the converted UTC time.
     */
    public static String convertFromLocalToUTC(String time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(time, formatter);
        ZonedDateTime localZonedDateTime = localDateTime.atZone(ZoneId.systemDefault());
        ZonedDateTime utcZonedDateTime = localZonedDateTime.withZoneSameInstant(ZoneId.of("UTC"));
        return utcZonedDateTime.format(formatter);
    }

    /**
     * Checks if the given time before the current local time.
     * @param time the time to check. Format: yyyy-MM-dd HH:mm:ss
     * @return true if the given time is earlier than the current local time, false otherwise
     */
    public static boolean isBeforeCurrentTime(String time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(time, formatter);
        ZonedDateTime localZonedDateTime = localDateTime.atZone(ZoneId.systemDefault());
        return localZonedDateTime.isBefore(ZonedDateTime.now());
    }

    /**
     * Checks if the given time is after the current local time.
     * @param time the time to check. Format: yyyy-MM-dd HH:mm:ss
     * @return true if the given time is after the current time, false otherwise
     */
    public static boolean isAfterCurrentTime(String time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(time, formatter);
        ZonedDateTime localZonedDateTime = localDateTime.atZone(ZoneId.systemDefault());
        return localZonedDateTime.isAfter(ZonedDateTime.now());
    }

    /**
     * Checks if the first given time is earlier than the 2nd time given.
     * @param startTime the start time to check. Format: yyyy-MM-dd HH:mm:ss
     * @param endTime the end time to check. Format: yyyy-MM-dd HH:mm:ss
     * @return true if the first given time is earlier than the 2nd time given, false otherwise
     */
    public static boolean isEndTimeBeforeStartTime(String startTime, String endTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localStartDateTime = LocalDateTime.parse(startTime, formatter);
        LocalDateTime localEndDateTime = LocalDateTime.parse(endTime, formatter);
        ZonedDateTime localStartZonedDateTime = localStartDateTime.atZone(ZoneId.systemDefault());
        ZonedDateTime localEndZonedDateTime = localEndDateTime.atZone(ZoneId.systemDefault());
        return localEndZonedDateTime.isBefore(localStartZonedDateTime);
    }

    /**
     * Check if there is overlap between the given times.
     * @param start1 start time of first interval. Format: yyyy-MM-dd HH:mm:ss
     * @param end1 end time of first interval. Format: yyyy-MM-dd HH:mm:ss
     * @param start2 start time of second interval. Format: yyyy-MM-dd HH:mm:ss
     * @param end2 end time of second interval. Format: yyyy-MM-dd HH:mm:ss
     * @return true if there is overlap between the given times, false otherwise
     */
    public static boolean areIntervalsOverlapping(String start1, String end1, String start2, String end2) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime start1DateTime = LocalDateTime.parse(start1, formatter);
        LocalDateTime end1DateTime = LocalDateTime.parse(end1, formatter);
        LocalDateTime start2DateTime = LocalDateTime.parse(start2, formatter);
        LocalDateTime end2DateTime = LocalDateTime.parse(end2, formatter);

        ZonedDateTime start1ZonedDateTime = start1DateTime.atZone(ZoneId.systemDefault());
        ZonedDateTime end1ZonedDateTime = end1DateTime.atZone(ZoneId.systemDefault());
        ZonedDateTime start2ZonedDateTime = start2DateTime.atZone(ZoneId.systemDefault());
        ZonedDateTime end2ZonedDateTime = end2DateTime.atZone(ZoneId.systemDefault());

        return start1ZonedDateTime.isBefore(end2ZonedDateTime) && end1ZonedDateTime.isAfter(start2ZonedDateTime);
    }

    /**
     * Checks if the given time is between the business hours of 8:00 AM and 10:00 PM EST.
     * @param startTime a String representing the start time to check. Format: yyyy-MM-dd HH:mm:ss
     * @param endTime a String representing the end time to check. Format: yyyy-MM-dd HH:mm:ss
     * @return true if the time is between the business hours, false otherwise.
     */
    public static boolean isBetweenBusinessHours(String startTime, String endTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        ZonedDateTime localStartTime = LocalDateTime.parse(startTime, formatter).atZone(ZoneId.systemDefault());
        ZonedDateTime localEndTime = LocalDateTime.parse(endTime, formatter).atZone(ZoneId.systemDefault());

        ZoneId estZoneId = ZoneId.of(businessZoneId);
        ZonedDateTime zonedStartTime = localStartTime.withZoneSameInstant(estZoneId);
        ZonedDateTime zonedEndTime = localEndTime.withZoneSameInstant(estZoneId);

        ZonedDateTime startOfDay = zonedStartTime.toLocalDate().atStartOfDay(estZoneId);
        ZonedDateTime eightAm = startOfDay.plusHours(openingHour);
        ZonedDateTime tenPm = startOfDay.plusHours(closingHour);

        return !zonedStartTime.isBefore(eightAm) && !zonedEndTime.isAfter(tenPm);
    }

    /**
     * Checks if the given time spans more than one day.
     * @param startTime a String representing the start time to check. Format: yyyy-MM-dd HH:mm:ss
     * @param endTime a String representing the end time to check. Format: yyyy-MM-dd HH:mm:ss
     * @return true if the time is between the business hours, false otherwise.
     */
    public static boolean isMoreThanOneDay(String startTime, String endTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        ZonedDateTime localStartTime = LocalDateTime.parse(startTime, formatter).atZone(ZoneId.systemDefault());
        ZonedDateTime localEndTime = LocalDateTime.parse(endTime, formatter).atZone(ZoneId.systemDefault());

        ZoneId estZoneId = ZoneId.of(businessZoneId);
        ZonedDateTime zonedStartTime = localStartTime.withZoneSameInstant(estZoneId);
        ZonedDateTime zonedEndTime = localEndTime.withZoneSameInstant(estZoneId);

        return !zonedStartTime.toLocalDate().equals(zonedEndTime.toLocalDate());
    }

    /**
     * Returns the month from a time string
     *
     * @param time the time string
     * @return String month the month name with the first name capitalized
     */
    public static String getMonthFromTime(String time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(time, formatter);
        return Parsers.capitalizeFirstLetter(dateTime.getMonth().name());
    }

    /**
     * Returns the zone id of the current time zone.
     * @return the zone id of the current time zone
     */
    public static String getCurrentTimezone() {
        return ZoneId.systemDefault().toString();
    }

    /**
     * Checks if the given time is within 15 minutes of the current time.
     * @param time the time to check. Format: yyyy-MM-dd HH:mm:ss
     * @return true if the given time is within 15 minutes of the current time, false otherwise
     */
    public static boolean isWithin15Minutes(String time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(time, formatter);
        ZonedDateTime zonedTime = localDateTime.atZone(ZoneId.systemDefault());
        ZonedDateTime now = ZonedDateTime.now(ZoneId.systemDefault());

        long minutesBetween = ChronoUnit.MINUTES.between(now, zonedTime);

        return minutesBetween >= 0 && minutesBetween <= 15;
    }

    /**
     * Converts a time format from yyyy-MM-dd HH:mm:ss to HH:mm AM or PM.
     * @param time the time to convert
     * @return the converted time
     */
    public static String convertTimeFormatAMPM(String time) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(time, inputFormatter);

        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("hh:mm a");
        return dateTime.format(outputFormatter);
    }

    /**
     * Get current UTC time and date in the format yyyy-MM-dd HH:mm:ss
     * @return current UTC time and date in the format yyyy-MM-dd HH:mm:ss
     */
    public static String getCurrentUTCTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.now(ZoneOffset.UTC).format(formatter);
    }
}

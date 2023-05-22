package rhettdelfierro.c195.helper;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class DateTime {
    private final static int openingHour = 8;
    private final static int closingHour = 22;
    private final static String businessZoneId = "America/New_York";
    public static String convertFromUTCToLocal(String time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(time, formatter);
        ZonedDateTime utcZonedDateTime = localDateTime.atZone(ZoneId.of("UTC"));
        ZonedDateTime localZonedDateTime = utcZonedDateTime.withZoneSameInstant(ZoneId.systemDefault());
        return localZonedDateTime.format(formatter);
    }

    public static String convertFromLocalToUTC(String time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(time, formatter);
        ZonedDateTime localZonedDateTime = localDateTime.atZone(ZoneId.systemDefault());
        ZonedDateTime utcZonedDateTime = localZonedDateTime.withZoneSameInstant(ZoneId.of("UTC"));
        return utcZonedDateTime.format(formatter);
    }

    public static boolean isBeforeCurrentTime(String time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(time, formatter);
        ZonedDateTime localZonedDateTime = localDateTime.atZone(ZoneId.systemDefault());
        return localZonedDateTime.isBefore(ZonedDateTime.now());
    }

    public static boolean isAfterCurrentTime(String time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(time, formatter);
        ZonedDateTime localZonedDateTime = localDateTime.atZone(ZoneId.systemDefault());
        return localZonedDateTime.isAfter(ZonedDateTime.now());
    }

    public static boolean isEndTimeBeforeStartTime(String startTime, String endTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localStartDateTime = LocalDateTime.parse(startTime, formatter);
        LocalDateTime localEndDateTime = LocalDateTime.parse(endTime, formatter);
        ZonedDateTime localStartZonedDateTime = localStartDateTime.atZone(ZoneId.systemDefault());
        ZonedDateTime localEndZonedDateTime = localEndDateTime.atZone(ZoneId.systemDefault());
        return localEndZonedDateTime.isBefore(localStartZonedDateTime);
    }

    public static boolean isTimeBetweenTwoLocalTimes(String time, String startTime, String endTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(time, formatter);
        LocalDateTime localStartDateTime = LocalDateTime.parse(startTime, formatter);
        LocalDateTime localEndDateTime = LocalDateTime.parse(endTime, formatter);
        ZonedDateTime localZonedDateTime = localDateTime.atZone(ZoneId.systemDefault());
        ZonedDateTime localStartZonedDateTime = localStartDateTime.atZone(ZoneId.systemDefault());
        ZonedDateTime localEndZonedDateTime = localEndDateTime.atZone(ZoneId.systemDefault());
        return localZonedDateTime.isEqual(localStartZonedDateTime) || (localZonedDateTime.isAfter(localStartZonedDateTime) && localZonedDateTime.isBefore(localEndZonedDateTime));
    }

    public static boolean isBetweenBusinessHours(String startTime, String endTime) {
        System.out.println("start time: " + startTime);
        System.out.println("end time: " + endTime);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        ZonedDateTime localStartTime = LocalDateTime.parse(startTime, formatter).atZone(ZoneId.systemDefault());
        ZonedDateTime localEndTime = LocalDateTime.parse(endTime, formatter).atZone(ZoneId.systemDefault());

        System.out.println("local start time: " + localStartTime);
        System.out.println("local end time: " + localEndTime);

        ZoneId estZoneId = ZoneId.of(businessZoneId);
        ZonedDateTime zonedStartTime = localStartTime.withZoneSameInstant(estZoneId);
        ZonedDateTime zonedEndTime = localEndTime.withZoneSameInstant(estZoneId);

        System.out.println(estZoneId + " start time: " + zonedStartTime);
        System.out.println(estZoneId + "zoned end time: " + zonedEndTime);
        // The times span multiple days
        if(!zonedStartTime.toLocalDate().equals(zonedEndTime.toLocalDate())) {
            return false;
        }

        ZonedDateTime startOfDay = zonedStartTime.toLocalDate().atStartOfDay(estZoneId);
        ZonedDateTime eightAm = startOfDay.plusHours(openingHour);
        ZonedDateTime tenPm = startOfDay.plusHours(closingHour);

        return !zonedStartTime.isBefore(eightAm) && !zonedEndTime.isAfter(tenPm);
    }

    public static String getMonthFromTime(String time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(time, formatter);
        return Parsers.capitalizeFirstLetter(dateTime.getMonth().name());
    }
}

package rhettdelfierro.c195.helper;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateTime {
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

    public static boolean isTimeBetweenTwoLocalTimes(String time, String startTime, String endTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(time, formatter);
        LocalDateTime localStartDateTime = LocalDateTime.parse(startTime, formatter);
        LocalDateTime localEndDateTime = LocalDateTime.parse(endTime, formatter);
        ZonedDateTime localZonedDateTime = localDateTime.atZone(ZoneId.systemDefault());
        ZonedDateTime localStartZonedDateTime = localStartDateTime.atZone(ZoneId.systemDefault());
        ZonedDateTime localEndZonedDateTime = localEndDateTime.atZone(ZoneId.systemDefault());
        return localZonedDateTime.isAfter(localStartZonedDateTime) && localZonedDateTime.isBefore(localEndZonedDateTime);
    }
}
